package com.raf.sport_termini_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotifikacijaDto implements Serializable {
    private String email;
    private String tipNotifikacije; // "REZERVACIJA_USPESNA", "OTKAZIVANJE"
    private String poruka;
}
