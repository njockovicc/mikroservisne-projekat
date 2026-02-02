package com.raf.sport_user_service.service;
import com.raf.sport_user_service.dto.*;


public interface UserService {
    // Registracija (pokriva i Klijenta i Trenera)
    UserDto register(UserCreateDto userCreateDto);

    // Login
    TokenResponseDto login(TokenRequestDto tokenRequestDto);

    // Brisanje (za Admina)
    void deleteById(Long id);

    UserDto update(Long id, UserCreateDto userCreateDto);

    void createRole(CreateRoleDto createRoleDto);
    void deleteRole(Long id);
    void assignRole(AssignRoleDto assignRoleDto);
}
