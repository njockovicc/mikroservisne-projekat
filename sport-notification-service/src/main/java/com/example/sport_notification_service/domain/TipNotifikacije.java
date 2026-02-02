package com.example.sport_notification_service.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class TipNotifikacije {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;  // npr. "REZERVACIJA_TRENINGA"

    // Ovde admin pise: "Pozdrav %ime %prezime, uspesno ste rezervisali termin %vreme"
    private String sablon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getSablon() {
        return sablon;
    }

    public void setSablon(String sablon) {
        this.sablon = sablon;
    }
}
