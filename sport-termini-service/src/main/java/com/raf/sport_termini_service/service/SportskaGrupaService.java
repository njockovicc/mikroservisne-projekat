package com.raf.sport_termini_service.service;

import com.raf.sport_termini_service.domain.SportskaGrupa;
import com.raf.sport_termini_service.dto.SportskaGrupaDto;
import com.raf.sport_termini_service.repository.SportskaGrupaRepository;
import org.springframework.stereotype.Service;

@Service
public class SportskaGrupaService {
    private final SportskaGrupaRepository sportskaGrupaRepository;

    public SportskaGrupaService(SportskaGrupaRepository sportskaGrupaRepository) {
        this.sportskaGrupaRepository = sportskaGrupaRepository;
    }

    public SportskaGrupa createGrupa(SportskaGrupaDto dto) {
        SportskaGrupa grupa = new SportskaGrupa();
        grupa.setOznakaGrupe(dto.getOznakaGrupe());
        grupa.setTipSporta(dto.getTipSporta());
        grupa.setMaksimalniBrojClanova(dto.getMaksimalniBrojClanova());
        grupa.setTakmicarska(dto.isTakmicarska());

        return sportskaGrupaRepository.save(grupa);
    }
}
