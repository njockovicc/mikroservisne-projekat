package com.raf.sport_termini_service.dto;

public class SportskaGrupaDto {
    private String oznakaGrupe;
    private String tipSporta;
    private Integer maksimalniBrojClanova;
    private boolean takmicarska; // true za takmičarsku, false za rekreativnu

    public String getOznakaGrupe() {
        return oznakaGrupe;
    }

    public void setOznakaGrupe(String oznakaGrupe) {
        this.oznakaGrupe = oznakaGrupe;
    }

    public String getTipSporta() {
        return tipSporta;
    }

    public void setTipSporta(String tipSporta) {
        this.tipSporta = tipSporta;
    }

    public Integer getMaksimalniBrojClanova() {
        return maksimalniBrojClanova;
    }

    public void setMaksimalniBrojClanova(Integer maksimalniBrojClanova) {
        this.maksimalniBrojClanova = maksimalniBrojClanova;
    }

    public boolean isTakmicarska() {
        return takmicarska;
    }

    public void setTakmicarska(boolean takmicarska) {
        this.takmicarska = takmicarska;
    }
}
