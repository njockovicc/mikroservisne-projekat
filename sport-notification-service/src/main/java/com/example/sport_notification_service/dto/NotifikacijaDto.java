package com.example.sport_notification_service.dto;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class NotifikacijaDto {
    private Long id;
    private Long korisnikId;
    private String email;
    private String tip;
    private String tekstPoruke;
    private LocalDateTime vremeSlanja;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Long korisnikId) {
        this.korisnikId = korisnikId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getTekstPoruke() {
        return tekstPoruke;
    }

    public void setTekstPoruke(String tekstPoruke) {
        this.tekstPoruke = tekstPoruke;
    }

    public LocalDateTime getVremeSlanja() {
        return vremeSlanja;
    }

    public void setVremeSlanja(LocalDateTime vremeSlanja) {
        this.vremeSlanja = vremeSlanja;
    }
}
