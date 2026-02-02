package com.raf.sport_user_service.repository;

import com.raf.sport_user_service.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    // Trazimo ulogu po imenu (npr. "ROLE_TRAINER")
    Optional<Role> findByName(String name);
}
