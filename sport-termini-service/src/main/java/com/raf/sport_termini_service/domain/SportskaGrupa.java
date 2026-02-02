package com.raf.sport_termini_service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity //klasa Jpa entitet,mapiran u tabeli
@Table(name = "sportske_grupe")
@Data
public class SportskaGrupa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //generise privatni kljuc,IDENTITY - autoincrement key
    private Long id;

    @Column(name = "oznaka_grupe", nullable = false, unique = true)
    @JsonProperty("oznakaGrupe")
    private String oznakaGrupe;

    @Column(name = "tip_sporta", nullable = false)
    @JsonProperty("tipSporta") //definise kako ce nam se zvati polje u Json kad se objekat salje/prima
    private String tipSporta;

    @Column(name = "maksimalni_broj_clanova", nullable = false)
    @JsonProperty("maksimalniBrojClanova")
    private Integer maksimalniBrojClanova;

    @Column(name = "takmicarska", nullable = false)
    @JsonProperty("takmicarska")
    private boolean takmicarska;

    @ManyToMany(mappedBy = "sportskeGrupe")
    @JsonIgnore
    @ToString.Exclude
    private Set<Trener> treneri;
}
