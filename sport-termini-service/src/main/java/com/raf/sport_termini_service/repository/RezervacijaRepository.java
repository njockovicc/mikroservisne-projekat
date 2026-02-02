package com.raf.sport_termini_service.repository;

import com.raf.sport_termini_service.domain.Rezervacija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RezervacijaRepository extends JpaRepository<Rezervacija,Long> {

    List<Rezervacija> findByTerminId(Long terminId);


}
