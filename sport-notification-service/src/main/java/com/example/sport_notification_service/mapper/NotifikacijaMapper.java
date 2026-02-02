package com.example.sport_notification_service.mapper;

import com.example.sport_notification_service.domain.Notifikacija;
import com.example.sport_notification_service.domain.TipNotifikacije;
import com.example.sport_notification_service.dto.NotifikacijaDto;
import com.example.sport_notification_service.dto.SlanjeNotifikacijeDto;
import com.example.sport_notification_service.dto.TipNotifikacijeDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class NotifikacijaMapper {
    // 1. Prebacuje Domain Notifikacija u DTO (za listanje arhive)
    public NotifikacijaDto toDto(Notifikacija entity) {
        if (entity == null) return null;

        NotifikacijaDto dto = new NotifikacijaDto();
        dto.setId(entity.getId());
        dto.setKorisnikId(entity.getKorisnikId());
        dto.setEmail(entity.getEmail());
        dto.setTip(entity.getTip());
        dto.setTekstPoruke(entity.getTekstPoruke());
        dto.setVremeSlanja(entity.getVremeSlanja());
        return dto;
    }

    // 2. Prebacuje TipNotifikacije (Domain) u DTO (za admin pregled)
    public TipNotifikacijeDto toTipDto(TipNotifikacije entity) {
        if (entity == null) return null;

        TipNotifikacijeDto dto = new TipNotifikacijeDto();
        dto.setId(entity.getId());
        dto.setNaziv(entity.getNaziv());
        dto.setSablon(entity.getSablon());
        return dto;
    }

    // 3. Prebacuje DTO u Domain (kada admin kreira novi tip notifikacije)
    public TipNotifikacije toTipEntity(TipNotifikacijeDto dto) {
        if (dto == null) return null;

        TipNotifikacije entity = new TipNotifikacije();
        entity.setNaziv(dto.getNaziv());
        entity.setSablon(dto.getSablon());
        return entity;
    }

    /*
     * 4. Pretvara zahtev za slanje u entitet koji čuvamo u bazu.
     * Ovde mapper koristi šablon iz TipNotifikacije i menja parametre (npr. %ime)
     * sa pravim vrednostima iz mape.
     */
    public Notifikacija toEntityFromSlanje(SlanjeNotifikacijeDto request, TipNotifikacije tip) {
        if (request == null) return null;

        Notifikacija entity = new Notifikacija();
        entity.setKorisnikId(request.getKorisnikId());
        entity.setEmail(request.getEmail());
        entity.setTip(request.getTip());
        entity.setVremeSlanja(LocalDateTime.now()); // Setujemo vreme slanja na "sad"

        // LOGIKA ZA TEKST PORUKE:
        // Uzimam šablon (npr: "Zdravo %ime, uspesna rezervacija")

        String finalniTekst = tip.getSablon();

        // Prolazimo kroz mapu parametara i menjamo ključeve u šablonu vrednostima
        if (request.getParametri() != null) {
            for (Map.Entry<String, String> entry : request.getParametri().entrySet()) {
                finalniTekst = finalniTekst.replace("%" + entry.getKey(), entry.getValue());
            }
        }

        entity.setTekstPoruke(finalniTekst);
        return entity;
    }
}
