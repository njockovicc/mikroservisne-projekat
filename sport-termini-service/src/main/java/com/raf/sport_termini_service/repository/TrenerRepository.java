package com.raf.sport_termini_service.repository;

import com.raf.sport_termini_service.domain.Trener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrenerRepository extends JpaRepository<Trener,Long> {
    List<Trener> findByImeAndPrezime(String ime, String prezime);

}
