package com.example.sport_notification_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipNotifikacijeDto {
    private Long id;
    private String naziv;  // npr. "AKTIVACIJA_NALOGA", "REZERVACIJA_TRENINGA"
    private String sablon; // npr. "Pozdrav %ime, vas trening je zakazan za %vreme"

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
