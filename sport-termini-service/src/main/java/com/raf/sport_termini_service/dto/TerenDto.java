package com.raf.sport_termini_service.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TerenDto {
    private Long id;
    private String oznaka;
    private String tipTerena; // npr. fudbalski, košarkaški
    private Integer kapacitet;
    private boolean zatvoren; // true za unutrašnji, false za spoljašnji

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOznaka() {
        return oznaka;
    }

    public void setOznaka(String oznaka) {
        this.oznaka = oznaka;
    }

    public String getTipTerena() {
        return tipTerena;
    }

    public void setTipTerena(String tipTerena) {
        this.tipTerena = tipTerena;
    }

    public Integer getKapacitet() {
        return kapacitet;
    }

    public void setKapacitet(Integer kapacitet) {
        this.kapacitet = kapacitet;
    }

    public boolean isZatvoren() {
        return zatvoren;
    }

    public void setZatvoren(boolean zatvoren) {
        this.zatvoren = zatvoren;
    }
}
