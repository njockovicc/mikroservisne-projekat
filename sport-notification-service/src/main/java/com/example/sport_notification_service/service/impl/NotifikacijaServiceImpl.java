package com.example.sport_notification_service.service.impl;

import com.example.sport_notification_service.domain.Notifikacija;
import com.example.sport_notification_service.domain.TipNotifikacije;
import com.example.sport_notification_service.dto.NotifikacijaDto;
import com.example.sport_notification_service.dto.SlanjeNotifikacijeDto;
import com.example.sport_notification_service.dto.TipNotifikacijeDto;
import com.example.sport_notification_service.mapper.NotifikacijaMapper;
import com.example.sport_notification_service.repository.NotifikacijaRepository;
import com.example.sport_notification_service.repository.TipNotifikacijeRepository;
import com.example.sport_notification_service.service.NotifikacijaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // Lombok će automatski napraviti konstruktor za final polja
public class NotifikacijaServiceImpl implements NotifikacijaService {

    private final NotifikacijaRepository notifikacijaRepository;
    private final TipNotifikacijeRepository tipRepository;
    private final NotifikacijaMapper mapper;
    private final JavaMailSender mailSender; // Za slanje pravih mejlova

    @Override
    public void procesuirajISalji(SlanjeNotifikacijeDto dto) {
        // 1. Nađi šablon za taj tip notifikacije
        TipNotifikacije tip = tipRepository.findByNaziv(dto.getTip())
                .orElseThrow(() -> new RuntimeException("Greška: Tip notifikacije '" + dto.getTip() + "' nije pronađen u bazi!"));

        // 2. KORISTIMO MAPPER: Pretvaramo DTO u Entity i menjamo %parametre u tekst
        Notifikacija notifikacija = mapper.toEntityFromSlanje(dto, tip);

        // 3. Čuvamo u arhivu (bazu)
        notifikacijaRepository.save(notifikacija);

        // 4. ŠALJEMO MEJL
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notifikacija.getEmail());
        message.setSubject("Sport Service - " + notifikacija.getTip());
        message.setText(notifikacija.getTekstPoruke());
        mailSender.send(message);
    }

    @Override
    public Page<NotifikacijaDto> dobaviSve(Pageable pageable) {
        // Mapiramo svaku Notifikaciju iz baze u DTO za prikaz
        return notifikacijaRepository.findAll(pageable)
                .map(mapper::toDto);
    }

    @Override
    public List<NotifikacijaDto> dobaviZaKorisnika(Long korisnikId) {
        return notifikacijaRepository.findByKorisnikId(korisnikId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TipNotifikacijeDto kreirajTip(TipNotifikacijeDto dto) {
        TipNotifikacije noviTip = mapper.toTipEntity(dto);
        noviTip = tipRepository.save(noviTip);
        return mapper.toTipDto(noviTip);
    }

    @Override
    public TipNotifikacijeDto izmeniTip(Long id, TipNotifikacijeDto dto) {
        TipNotifikacije postojeci = tipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tip nije nadjen"));

        postojeci.setNaziv(dto.getNaziv());
        postojeci.setSablon(dto.getSablon());

        return mapper.toTipDto(tipRepository.save(postojeci));
    }

    @Override
    public void obrisiTip(Long id) {
        tipRepository.deleteById(id);
    }
}