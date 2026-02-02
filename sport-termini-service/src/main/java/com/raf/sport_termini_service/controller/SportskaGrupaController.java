package com.raf.sport_termini_service.controller;

import com.raf.sport_termini_service.domain.SportskaGrupa;
import com.raf.sport_termini_service.dto.SportskaGrupaDto;
import com.raf.sport_termini_service.service.CheckSecurity;
import com.raf.sport_termini_service.service.SportskaGrupaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grupe")
public class SportskaGrupaController {
    private final SportskaGrupaService sportskaGrupaService;

    public SportskaGrupaController(SportskaGrupaService sportskaGrupaService) {
        this.sportskaGrupaService = sportskaGrupaService;
    }

    @PostMapping
    @CheckSecurity(roles = {"ROLE_TRAINER"})
    public ResponseEntity<SportskaGrupa> create(
            @RequestHeader("Authorization") String authorization,
            @RequestBody SportskaGrupaDto dto) {

        return new ResponseEntity<>(sportskaGrupaService.createGrupa(dto), HttpStatus.CREATED);
    }

}
