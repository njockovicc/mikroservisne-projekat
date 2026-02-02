package com.raf.sport_termini_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // Ova anotacija kaže Springu da ova klasa "hvata" greške iz svih kontrolera
public class PorukeException {

    @ExceptionHandler(RuntimeException.class) // Hvata greške tipa RuntimeException
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException e) {
        Map<String, String> response = new HashMap<>();

        // Uzimam tačnu poruku koju sam napisala u servisu:
        //  "Teren ili trener su zauzeti!" ili "Termin je popunjen!"
        response.put("message", e.getMessage());

        // Vraćamo status 400 (Bad Request) jer je to logička greška korisnika,
        // a ne kvar servera (500). Možeš ostaviti i 500 ako ti je lakše.
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
