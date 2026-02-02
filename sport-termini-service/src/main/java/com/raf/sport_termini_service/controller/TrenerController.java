package com.raf.sport_termini_service.controller;
import com.raf.sport_termini_service.domain.Trener;
import com.raf.sport_termini_service.dto.TrenerDto;
import com.raf.sport_termini_service.service.TrenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers") //treba da mi gadja kao u authservisu onom prvom
public class TrenerController {
    private final TrenerService trenerService;

    public TrenerController(TrenerService trenerService) {
        this.trenerService = trenerService;
    }

    // OVO JE ONAJ SINHRONI POZIV IZ AUTH SERVISA
    @PostMapping
    public ResponseEntity<Trener> createTrener(@RequestBody TrenerDto trenerDto) {
        // Mapiramo DTO u Entitet
        Trener trener = new Trener();
        trener.setIme(trenerDto.getIme());
        trener.setPrezime(trenerDto.getPrezime());
        trener.setEmail(trenerDto.getEmail());
        trener.setSpecijalnost(trenerDto.getSpecijalnost());
        trener.setRoleName(trenerDto.getRoleName());

        return new ResponseEntity<>(trenerService.createTrener(trener), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Trener>> getTreneri(@RequestParam(required = false) String ime,
                                                   @RequestParam(required = false) String prezime) {
        if (ime != null && prezime != null) {
            return ResponseEntity.ok(trenerService.getTreneriByImeAndPrezime(ime, prezime));
        }
        return ResponseEntity.ok(trenerService.getAllTreneri());
    }
}
