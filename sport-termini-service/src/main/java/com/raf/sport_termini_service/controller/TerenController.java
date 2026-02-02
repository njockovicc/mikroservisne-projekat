package com.raf.sport_termini_service.controller;

import com.raf.sport_termini_service.domain.Teren;
import com.raf.sport_termini_service.dto.TerenDto;
import com.raf.sport_termini_service.service.CheckSecurity;
import com.raf.sport_termini_service.service.TerenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tereni")
public class TerenController {
    private final TerenService terenService;

    public TerenController(TerenService terenService) {
        this.terenService = terenService;
    }

    // 1. UNOS NOVOG TERENA (Samo Administrator)
    @PostMapping
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Teren> createTeren(
            @RequestHeader("Authorization") String authorization,
            @RequestBody TerenDto terenDto) {

        Teren noviTeren = new Teren();
        noviTeren.setOznaka(terenDto.getOznaka());
        noviTeren.setTipTerena(terenDto.getTipTerena());
        noviTeren.setKapacitet(terenDto.getKapacitet());
        noviTeren.setZatvoren(terenDto.isZatvoren());

        return new ResponseEntity<>(terenService.createTeren(noviTeren), HttpStatus.CREATED);
    }

    // 2. PRETRAGA TERENA (Po oznaci ili tipu)
    @GetMapping
    public ResponseEntity<List<Teren>> getTereni(
            @RequestParam(required = false) String oznaka,
            @RequestParam(required = false) String tip) {

        return ResponseEntity.ok(terenService.searchTereni(oznaka, tip));
    }

}
