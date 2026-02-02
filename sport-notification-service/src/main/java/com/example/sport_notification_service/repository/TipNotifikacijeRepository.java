package com.example.sport_notification_service.repository;

import com.example.sport_notification_service.domain.TipNotifikacije;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipNotifikacijeRepository extends JpaRepository<TipNotifikacije,Long> {
    // Vraca Optional jer tip mozda ne postoji u bazi pod tim imenom
    Optional<TipNotifikacije> findByNaziv(String naziv);
}
