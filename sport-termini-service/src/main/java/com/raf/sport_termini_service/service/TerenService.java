package com.raf.sport_termini_service.service;

import com.raf.sport_termini_service.domain.Teren;
import com.raf.sport_termini_service.repository.TerenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TerenService {
    private final TerenRepository terenRepository;

    public TerenService(TerenRepository terenRepository) {
        this.terenRepository = terenRepository;
    }

    public Teren createTeren(Teren teren) {
        return terenRepository.save(teren);
    }

    public List<Teren> searchTereni(String oznaka, String tip) {
        if (oznaka != null && tip != null) return terenRepository.findByOznakaAndTipTerena(oznaka, tip);
        if (oznaka != null) return terenRepository.findByOznakaContainingIgnoreCase(oznaka);
        if (tip != null) return terenRepository.findByTipTerenaContainingIgnoreCase(tip);
        return terenRepository.findAll();
    }
}
