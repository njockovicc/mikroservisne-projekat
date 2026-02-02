package com.raf.sport_termini_service.controller;

import com.raf.sport_termini_service.domain.Rezervacija;
import com.raf.sport_termini_service.domain.Termin;
import com.raf.sport_termini_service.dto.TerminCreateDto;
import com.raf.sport_termini_service.service.CheckSecurity;
import com.raf.sport_termini_service.service.TerminService;
import com.raf.sport_termini_service.service.TokenService;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api")
public class TerminController {
    private final TerminService terminService;
    private final TokenService tokenService;

    public TerminController(TerminService terminService, TokenService tokenService) {
        this.terminService = terminService;
        this.tokenService = tokenService;
    }

    // --- 1. PRIKAZ SVIH TERMINA (Dostupno svima) ---
    @GetMapping("/termini")
    public ResponseEntity<List<Termin>> getAll() {
        return ResponseEntity.ok(terminService.getAllTermini());
    }

    // --- 2. DODAVANJE NOVOG TERMINA (Samo Trener) ---
    @PostMapping("/termini")
    @CheckSecurity(roles = {"ROLE_TRAINER"})
    public ResponseEntity<Termin> create(@RequestHeader("Authorization") String authorization,
                                         @RequestBody TerminCreateDto dto) {
        return new ResponseEntity<>(terminService.createTermin(dto), HttpStatus.CREATED);
    }

    // --- 3. BRISANJE/OTKAZIVANJE TERMINA (Trener ili Admin) ---
    @DeleteMapping("/termini/{id}")
    @CheckSecurity(roles = {"ROLE_TRAINER", "ROLE_ADMIN"})
    public ResponseEntity<Void> delete(@RequestHeader("Authorization") String authorization,
                                       @PathVariable Long id) {
        terminService.otkaziTermin(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // --- 4. REZERVACIJA TERMINA (Samo Klijent) ---
    @PostMapping("/rezervacije")
    @CheckSecurity(roles = {"ROLE_CLIENT"})
    public ResponseEntity<Rezervacija> reserve(@RequestHeader("Authorization") String authorization,
                                               @RequestBody Long terminId) {

        // Izvlačim ID i Email iz tokena
        // Token je u formatu "Bearer [string]"
        String token = authorization.split(" ")[1];
        Claims claims = tokenService.parseToken(token);

        Long userId = Long.valueOf((claims.get("id").toString()));
        String userEmail = claims.get("email", String.class);
        return new ResponseEntity<>(terminService.rezervisiTermin(terminId, userId, userEmail), HttpStatus.CREATED);
    }


    // --- 5. IZMENA TERMINA (Trener ili Admin) ---
    @PutMapping("/termini/{id}")
    @CheckSecurity(roles = {"ROLE_TRAINER", "ROLE_ADMIN"})
    public ResponseEntity<Termin> update (@RequestHeader("Authorization") String authorization,
                                          @PathVariable Long id,
                                          @RequestBody TerminCreateDto dto){
        return new ResponseEntity<>(terminService.izmeniTermin(id, dto), HttpStatus.OK);
    }

    }
