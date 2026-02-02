package com.raf.sport_termini_service.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Rezervacija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Čuvamo ID ili Email korisnika koji je rezervisao (to stiže iz Tokena)
    private Long userId;
    private String userEmail;

    @ManyToOne
    private Termin termin;

    public Rezervacija() {
    }

    public Rezervacija(Long userId, String userEmail, Termin termin) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.termin = termin;
    }
}
