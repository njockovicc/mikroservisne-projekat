package com.example.sport_notification_service.repository;

import com.example.sport_notification_service.domain.Notifikacija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface NotifikacijaRepository extends JpaRepository<Notifikacija, Long> {
    // Za rutu GET /notifikacije/{korisnikId}
    List<Notifikacija> findByKorisnikId(Long korisnikId);

}


