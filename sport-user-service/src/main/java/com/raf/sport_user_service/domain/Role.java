package com.raf.sport_user_service.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name; // npr. "ROLE_CLIENT", "ROLE_TRAINER"

    private String description;

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
