package com.raf.sport_termini_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.raf.sport_termini_service.domain.TipTermina;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class TerminCreateDto {
    private Long terenId;
    private Long trenerId;
    private Long sportskaGrupaId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime vremePocetka;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime vremeZavrsetka;


    private String danUNedelji;
    private TipTermina tipTermina;
}
