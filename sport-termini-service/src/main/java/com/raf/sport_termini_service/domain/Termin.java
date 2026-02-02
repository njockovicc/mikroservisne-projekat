package com.raf.sport_termini_service.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "termini")
@Data
public class Termin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vreme_pocetka", nullable = false)
    private LocalTime vremePocetka;

    @Column(name = "vreme_zavrsetka", nullable = false)
    private LocalTime vremeZavrsetka;

    @Column(name = "dan_u_nedelji", nullable = false)
    private String  danUNedelji;

    @Enumerated(EnumType.STRING)
    @Column(name = "tip_termina", nullable = false)
    private TipTermina tipTermina;

    @Column(nullable = false)
    private Integer trenutnoPrijavljenih = 0; // Početno je 0



    @ManyToOne
    @JoinColumn(name = "teren_id", nullable = false)
    private Teren teren;

    @ManyToOne
    @JoinColumn(name = "trener_id", nullable = false)
    private Trener trener;

    @ManyToOne
    @JoinColumn(name = "sportska_grupa_id", nullable = false)
    private SportskaGrupa sportskaGrupa;
}
