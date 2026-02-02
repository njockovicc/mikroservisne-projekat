package com.raf.sport_termini_service.repository;

import com.raf.sport_termini_service.domain.SportskaGrupa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SportskaGrupaRepository extends JpaRepository<SportskaGrupa,Long> {
    List<SportskaGrupa> findByTipSporta(String tipSporta);
}
