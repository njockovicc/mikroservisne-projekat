package com.example.sport_notification_service.controller;

import com.example.sport_notification_service.dto.NotifikacijaDto;
import com.example.sport_notification_service.dto.SlanjeNotifikacijeDto;
import com.example.sport_notification_service.dto.TipNotifikacijeDto;
import com.example.sport_notification_service.security.CheckSecurity;
import com.example.sport_notification_service.service.NotifikacijaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifikacije")
public class NotifikacijaController {
    private final NotifikacijaService notifikacijaService;

    public NotifikacijaController(NotifikacijaService notifikacijaService) {
        this.notifikacijaService = notifikacijaService;
    }

    /*
     * POST /notifikacije/slanje
     * Rucno slanje notifikacije (pozivaju drugi servisi ako ne idu preko brokera)
     */
    @PostMapping("/slanje")
    public ResponseEntity<Void> posaljiNotifikaciju(@RequestBody SlanjeNotifikacijeDto slanjeNotifikacijeDto) {
        notifikacijaService.procesuirajISalji(slanjeNotifikacijeDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * GET /notifikacije
     * Admin pregled svih poslatih notifikacija
     */
    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Page<NotifikacijaDto>> dobaviSveNotifikacije(
            @RequestHeader("Authorization") String authorization, Pageable pageable) {
        return new ResponseEntity<>(notifikacijaService.dobaviSve(pageable), HttpStatus.OK);
    }

    /*
     * GET /notifikacije/{korisnikId}
     * Pregled notifikacija za određenog korisnika (klijent ili trener gledaju svoje)
     */
    @GetMapping("/{korisnikId}")
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_TRAINER", "ROLE_CLIENT"})
    public ResponseEntity<List<NotifikacijaDto>> dobaviZaKorisnika(
            @RequestHeader(value = "Authorization") String authorization,
            @PathVariable Long korisnikId) {
        return new ResponseEntity<>(notifikacijaService.dobaviZaKorisnika(korisnikId), HttpStatus.OK);
    }


    /*
     * POST /notifikacije/tipovi
     * Definisanje novih tipova/šablona notifikacija
     */
    @PostMapping("/tipovi")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<TipNotifikacijeDto> kreirajTip(
            @RequestHeader(value = "Authorization") String authorization,
            @RequestBody TipNotifikacijeDto tipNotifikacijeDto) {
        return new ResponseEntity<>(notifikacijaService.kreirajTip(tipNotifikacijeDto), HttpStatus.CREATED);
    }

    /*
     * PUT /notifikacije/tipovi/{id}
     * Izmena postojećeg šablona
     */
    @PutMapping("/tipovi/{id}")
   @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<TipNotifikacijeDto> izmeniTip(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long id,
            @RequestBody TipNotifikacijeDto tipNotifikacijeDto) {
        return new ResponseEntity<>(notifikacijaService.izmeniTip(id, tipNotifikacijeDto), HttpStatus.OK);
    }

    /*
     * DELETE /notifikacije/tipovi/{id}
     * Brisanje tipa notifikacije
     */
    @DeleteMapping("/tipovi/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Void> obrisiTip(
            @RequestHeader(value = "Authorization") String authorization,
            @PathVariable Long id) {
        notifikacijaService.obrisiTip(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
