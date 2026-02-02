package com.raf.sport_termini_service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "treneri")
@Data
public class Trener {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ime", nullable = false)
    @JsonProperty("ime")
    private String ime;

    @Column(name = "prezime", nullable = false)
    @JsonProperty("prezime")
    private String prezime;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "zvanje")
    @JsonProperty("roleName")
    private String roleName;

    @Column(name = "specijalnost")
    @JsonProperty("specijalnost")
    private String specijalnost;


    @ManyToMany
    @JoinTable(
            name = "trener_grupa",
            joinColumns = @JoinColumn(name = "trener_id"),
            inverseJoinColumns = @JoinColumn(name = "grupa_id")
    )
    @JsonIgnore
    private Set<SportskaGrupa> sportskeGrupe;

    public Set<SportskaGrupa> getSportskeGrupe() {
        return this.sportskeGrupe;
    }


}
