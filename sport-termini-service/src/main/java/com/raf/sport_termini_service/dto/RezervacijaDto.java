package com.raf.sport_termini_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RezervacijaDto {
    private Long terminId;
    // User ID i Email vadimo iz Tokena u kontroleru, ali mogu biti i ovde

}
