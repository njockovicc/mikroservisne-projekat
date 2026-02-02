package com.raf.sport_user_service.controller;

import com.raf.sport_user_service.dto.*;
import com.raf.sport_user_service.security.CheckSecurity;
import com.raf.sport_user_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // --- 1. REGISTRACIJA ---
    // Ne treba token jer se tek registruje
    @PostMapping("/registracija")
    public ResponseEntity<UserDto> register(@RequestBody UserCreateDto userCreateDto) {
        return new ResponseEntity<>(userService.register(userCreateDto), HttpStatus.CREATED);
    }

    // --- 2. PRIJAVA (Login) ---
    // Ne treba token jer se tek prijavljuje
    @PostMapping("/prijava")
    public ResponseEntity<TokenResponseDto> login(@RequestBody TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(userService.login(tokenRequestDto), HttpStatus.OK);
    }

    // --- 3. AŽURIRANJE PROFILA ---
    // Zaštićeno: Mogu svi ulogovani (Admin, Klijent, Trener)
    // BITNO: Parametar se mora zvati "authorization" zbog tvog Aspekta!
    @PutMapping("/korisnici/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_CLIENT", "ROLE_TRAINER"})
    public ResponseEntity<UserDto> updateUser(@RequestHeader("Authorization") String authorization,
                                              @PathVariable("id") Long id,
                                              @RequestBody UserCreateDto userCreateDto) {
        return new ResponseEntity<>(userService.update(id, userCreateDto), HttpStatus.OK);
    }

    // --- 4. BRISANJE KORISNIKA ---
    // Zaštićeno: Samo Admin
    @DeleteMapping("/korisnici/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Void> deleteUser(@RequestHeader("Authorization") String authorization,
                                           @PathVariable("id") Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // --- 5. UPRAVLJANJE ULOGAMA (Zahtev admina) ---

    // Kreiranje nove uloge
    @PostMapping("/uloge")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Void> createRole(@RequestHeader("Authorization") String authorization,
                                           @RequestBody CreateRoleDto createRoleDto) {
        userService.createRole(createRoleDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Brisanje uloge
    // PDF ne definiše tačnu rutu, ali ovo je standard: DELETE /auth/uloge/{id}
    @DeleteMapping("/uloge/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Void> deleteRole(@RequestHeader("Authorization") String authorization,
                                           @PathVariable("id") Long id) {
        userService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Dodeljivanje uloge korisniku
    // PDF: POST /auth/dodela-uloge
    @PostMapping("/dodela-uloge")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Void> assignRoleToUser(@RequestHeader("Authorization") String authorization,
                                                 @RequestBody AssignRoleDto assignRoleDto) {
        userService.assignRole(assignRoleDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
