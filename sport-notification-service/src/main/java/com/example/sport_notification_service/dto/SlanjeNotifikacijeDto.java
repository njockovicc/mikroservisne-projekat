package com.example.sport_notification_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class SlanjeNotifikacijeDto {
    private String tip;             // npr. "REZERVACIJA"
    private String email;           // na koji mejl saljemo
    private Long korisnikId;        // id korisnika (da znamo u ciju arhivu ide)

    // Mapa: kljuc je naziv (npr. "ime"), vrednost je ono sto ubacujemo (npr. "Natasaa")
    private Map<String, String> parametri;

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Long korisnikId) {
        this.korisnikId = korisnikId;
    }

    public Map<String, String> getParametri() {
        return parametri;
    }

    public void setParametri(Map<String, String> parametri) {
        this.parametri = parametri;
    }
}
