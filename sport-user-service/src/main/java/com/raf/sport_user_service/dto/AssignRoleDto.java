package com.raf.sport_user_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignRoleDto {
    private String userEmail; // Kome dodajemo
    private String roleName;  // Koju ulogu dodajemo
}
