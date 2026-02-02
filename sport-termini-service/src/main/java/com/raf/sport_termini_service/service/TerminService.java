package com.raf.sport_termini_service.service;
import com.raf.sport_termini_service.domain.*;
import com.raf.sport_termini_service.dto.SlanjeNotifikacijeDto;
import com.raf.sport_termini_service.dto.TerminCreateDto;
import com.raf.sport_termini_service.helper.MessageHelper;
import com.raf.sport_termini_service.repository.*;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TerminService {
    private final TerminRepository terminRepository;
    private final TerenRepository terenRepository;
    private final TrenerRepository trenerRepository;
    private final SportskaGrupaRepository sportskaGrupaRepository;
    private final RezervacijaRepository rezervacijaRepository;
    private final JmsTemplate jmsTemplate; // Koristimo ga za slanje poruka
    private final MessageHelper messageHelper;


    public TerminService(TerminRepository terminRepository, TerenRepository terenRepository,
                         TrenerRepository trenerRepository, SportskaGrupaRepository sportskaGrupaRepository,
                         RezervacijaRepository rezervacijaRepository, JmsTemplate jmsTemplate,MessageHelper messageHelper) {
        this.terminRepository = terminRepository;
        this.terenRepository = terenRepository;
        this.trenerRepository = trenerRepository;
        this.sportskaGrupaRepository = sportskaGrupaRepository;
        this.rezervacijaRepository = rezervacijaRepository;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;


    }


    public Termin createTermin(TerminCreateDto dto) {
        Teren teren = terenRepository.findById(dto.getTerenId())
                .orElseThrow(() -> new RuntimeException("Teren ne postoji."));
        Trener trener = trenerRepository.findById(dto.getTrenerId())
                .orElseThrow(() -> new RuntimeException("Trener ne postoji."));
        SportskaGrupa grupa = sportskaGrupaRepository.findById(dto.getSportskaGrupaId())
                .orElseThrow(() -> new RuntimeException("Grupa ne postoji."));

        // Validacija preklapanja
        List<Termin> preklapajuci = terminRepository.findOverlappingTermini(
                dto.getDanUNedelji(), dto.getVremePocetka(), dto.getVremeZavrsetka(), dto.getTerenId(), dto.getTrenerId());
        if (!preklapajuci.isEmpty()) {
            throw new RuntimeException("Teren ili trener su zauzeti!");
        }

        Termin novi = new Termin();
        novi.setVremePocetka(dto.getVremePocetka());
        novi.setVremeZavrsetka(dto.getVremeZavrsetka());
        novi.setDanUNedelji(dto.getDanUNedelji());
        novi.setTipTermina(dto.getTipTermina());
        novi.setTeren(teren);
        novi.setTrener(trener);
        novi.setSportskaGrupa(grupa);
        novi.setTrenutnoPrijavljenih(0); // Na početku niko nije prijavljen
        return terminRepository.save(novi);
    }

    //  REZERVACIJA (Klijent)
    public Rezervacija rezervisiTermin(Long terminId, Long userId, String userEmail) {
        // 1. Nađi termin
        Termin termin = terminRepository.findById(terminId)
                .orElseThrow(() -> new RuntimeException("Termin ne postoji"));

        // 2. Proveri kapacitet
        int maxClanova = termin.getSportskaGrupa().getMaksimalniBrojClanova();
        if (termin.getTrenutnoPrijavljenih() >= maxClanova) {
            throw new RuntimeException("Termin je popunjen!");
        }

        // 3. Povećaj broj prijavljenih
        termin.setTrenutnoPrijavljenih(termin.getTrenutnoPrijavljenih() + 1);
        terminRepository.save(termin);

        // 4. Napravi rezervaciju
        Rezervacija rezervacija = new Rezervacija(userId, userEmail, termin);
        Rezervacija sacuvana = rezervacijaRepository.save(rezervacija);

        // 4. Pakovanje parametara (Ovde email stavljamo pod ključ "ime")
        Map<String, String> parametri = new HashMap<>();
        parametri.put("ime", sacuvana.getUserEmail()); // Po tvojoj želji, šaljemo email umesto imena
        parametri.put("vreme", sacuvana.getTermin().getVremePocetka().toString());
        parametri.put("tipTermina", sacuvana.getTermin().getTipTermina().name());
        parametri.put("teren", sacuvana.getTermin().getTeren().getOznaka());

        // 5. ASINHRONO SLANJE
        SlanjeNotifikacijeDto poruka = new SlanjeNotifikacijeDto();
        poruka.setTip("REZERVACIJA_TERMINA");
        poruka.setEmail(sacuvana.getUserEmail());
        poruka.setKorisnikId(sacuvana.getUserId());
        poruka.setParametri(parametri);

        SlanjeNotifikacijeDto porukaZaTrenera = new SlanjeNotifikacijeDto();
        porukaZaTrenera.setTip("REZERVACIJA_TRENERA"); // Napravila ovaj tip u bazi notifikacija
        porukaZaTrenera.setEmail(termin.getTrener().getEmail()); // Uzimamo email trenera iz termina
        porukaZaTrenera.setKorisnikId(termin.getTrener().getId()); // ID trenera

        Map<String, String> parametriTrener = new HashMap<>();
        parametriTrener.put("ime", termin.getTrener().getIme());
        parametriTrener.put("klijent", sacuvana.getUserEmail()); // Da trener zna ko mu je došao
        parametriTrener.put("vreme", termin.getVremePocetka().toString());

        porukaZaTrenera.setParametri(parametriTrener);


        // Šaljemo drugu poruku na isti broker
        jmsTemplate.convertAndSend("notifikacije_queue", messageHelper.createTextMessage(porukaZaTrenera));

        // 6. SLANJE NA ACTIVE MQ
        // Koristiš messageHelper da spakuješ objekat u JSON tekst
        jmsTemplate.convertAndSend("notifikacije_queue", messageHelper.createTextMessage(poruka));

        return sacuvana;
    }

    // --- METODA ZA BRISANJE (Sa slanjem mejlova otkazivanja) ---
    public void otkaziTermin(Long terminId) {
        Termin termin = terminRepository.findById(terminId)
                .orElseThrow(() -> new RuntimeException("Termin ne postoji"));
        // 1. Nađi sve rezervacije za taj termin
        List<Rezervacija> rezervacije = rezervacijaRepository.findByTerminId(terminId);

        // 2. Pošalji poruku SVAKOM klijentu
        for (Rezervacija r : rezervacije) {
            SlanjeNotifikacijeDto poruka = new SlanjeNotifikacijeDto();
            poruka.setTip("OTKAZIVANJE_TERMINA");
            poruka.setEmail(r.getUserEmail());
            poruka.setKorisnikId(r.getUserId());

            Map<String, String> parametri = new HashMap<>();
            parametri.put("ime", r.getUserEmail());
            parametri.put("tipTermina", termin.getTipTermina().name());
            parametri.put("vreme", termin.getVremePocetka().toString());
            poruka.setParametri(parametri);

            jmsTemplate.convertAndSend("notifikacije_queue",
                    messageHelper.createTextMessage(poruka));
        }

        // 3. Obriši termin

        if (!rezervacije.isEmpty()) {
            rezervacijaRepository.deleteAll(rezervacije);
        }

        terminRepository.delete(termin);
    }

    public Termin izmeniTermin(Long id, TerminCreateDto dto) {
        // 1. Pronađi postojeći termin
        Termin termin = terminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Termin ne postoji."));

        // 2. Ažuriraj polja
        termin.setVremePocetka(dto.getVremePocetka());
        termin.setVremeZavrsetka(dto.getVremeZavrsetka());
        termin.setDanUNedelji(dto.getDanUNedelji());
        termin.setTipTermina(dto.getTipTermina());

        // 3. Obavesti sve klijente koji su rezervisali ovaj termin
        List<Rezervacija> rezervacije = rezervacijaRepository.findByTerminId(id);

        for (Rezervacija r : rezervacije) {
            try {
                SlanjeNotifikacijeDto poruka = new SlanjeNotifikacijeDto();
                poruka.setTip("IZMENA_TERMINA");
                poruka.setEmail(r.getUserEmail());
                poruka.setKorisnikId(r.getUserId());

                Map<String, String> parametri = new HashMap<>();
                parametri.put("ime", r.getUserEmail());
                parametri.put("tipTermina", termin.getTipTermina().name());
                parametri.put("vreme", termin.getVremePocetka().toString());
                poruka.setParametri(parametri);

                jmsTemplate.convertAndSend("notifikacije_queue", messageHelper.createTextMessage(poruka));
            } catch (Exception e) {
                System.err.println("Greška pri slanju obaveštenja o izmeni: " + e.getMessage());
            }
        }

        // 4. Sačuvaj izmene
        return terminRepository.save(termin);
    }

    public List<Termin> getAllTermini() {
        return terminRepository.findAll();
    }
}
