package com.raf.sport_user_service.domain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    // lista uloga, pa menjamo vezu na ManyToMany
    // FetchType.EAGER znaci da kad ucitamo korisnika, odmah ucitamo i njegove uloge (bitno za Security)
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    private LocalDate registrationDate; // Trazi se u PDF-u
    private String zvanje;

    public User() {
    }
}
