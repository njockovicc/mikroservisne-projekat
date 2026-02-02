package com.raf.sport_user_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRoleDto {

    private String name;        // npr. "ROLE_SUPERVISOR"
    private String description; // npr. "Nadgleda rad teretane"
}
