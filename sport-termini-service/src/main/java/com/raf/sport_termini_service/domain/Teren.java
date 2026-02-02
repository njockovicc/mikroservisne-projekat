package com.raf.sport_termini_service.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tereni")
@Data
public class Teren {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //baza pravi id
    private Long id;

    @Column(name = "oznaka", nullable = false, unique = true)
    @JsonProperty("oznaka")
    private String oznaka;

    @Column(name = "tip_terena", nullable = false)
    @JsonProperty("tipTerena")
    private String tipTerena;

    @Column(name = "kapacitet", nullable = false)
    @JsonProperty("kapacitet")
    private Integer kapacitet;

    @Column(name = "zatvoren", nullable = false)
    @JsonProperty("zatvoren")
    private boolean zatvoren;
}
