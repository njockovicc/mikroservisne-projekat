package com.example.sport_notification_service.service;

import com.example.sport_notification_service.dto.NotifikacijaDto;
import com.example.sport_notification_service.dto.SlanjeNotifikacijeDto;
import com.example.sport_notification_service.dto.TipNotifikacijeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; // SAMO OVAJ TREBA

import java.util.List;
import java.util.stream.Collectors;

public interface NotifikacijaService {
    void procesuirajISalji(SlanjeNotifikacijeDto dto);
    Page<NotifikacijaDto> dobaviSve(Pageable pageable);
    List<NotifikacijaDto> dobaviZaKorisnika(Long korisnikId);

    // Admin operacije
    TipNotifikacijeDto kreirajTip(TipNotifikacijeDto dto);
    TipNotifikacijeDto izmeniTip(Long id, TipNotifikacijeDto dto);
    void obrisiTip(Long id);
}