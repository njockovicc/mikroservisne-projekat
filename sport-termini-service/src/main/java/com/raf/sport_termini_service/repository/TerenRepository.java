package com.raf.sport_termini_service.repository;

import com.raf.sport_termini_service.domain.Teren;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TerenRepository extends JpaRepository<Teren,Long> {

    List<Teren> findByOznakaContainingIgnoreCase(String oznaka);
    List<Teren> findByTipTerenaContainingIgnoreCase(String tip);
    List<Teren> findByOznakaAndTipTerena(String oznaka, String tip);
}
