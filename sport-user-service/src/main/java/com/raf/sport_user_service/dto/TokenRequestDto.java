package com.raf.sport_user_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequestDto {
    private String email;
    private String password;
}
