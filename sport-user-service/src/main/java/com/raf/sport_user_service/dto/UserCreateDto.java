package com.raf.sport_user_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserCreateDto {
    // Obavezna polja za Auth servis
    private String username;
    private String password;
    private String email;
    private String roleName; // npr. "ROLE_CLIENT" ili "ROLE_TRAINER"

    // Opciona polja (šaljemo ih drugom servisu SAMO ako je Trener)
    private String ime;
    private String prezime;
    private String specijalnost;
}
