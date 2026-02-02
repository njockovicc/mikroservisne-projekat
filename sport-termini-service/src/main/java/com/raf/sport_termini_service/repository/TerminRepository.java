package com.raf.sport_termini_service.repository;

import com.raf.sport_termini_service.domain.Termin;
import com.raf.sport_termini_service.domain.TipTermina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TerminRepository extends JpaRepository<Termin,Long> {
    @Query("SELECT t FROM Termin t WHERE t.danUNedelji = :dan " +
            "AND t.vremePocetka < :kraj " + "AND t.vremeZavrsetka > :pocetak " //proveru preklapanja vremenskih intervala
            + "AND (t.teren.id = :terenId OR t.trener.id = :trenerId)")
        //Traži se preklapanje ako je zauzet ili isti teren ili isti trener.
        //To znači da se teren i trener ne mogu istovremeno rezervisati za dva termina koja se preklapaju.


    List<Termin> findOverlappingTermini(
            @Param("dan") String dan,
            @Param("pocetak") LocalTime pocetak,
            @Param("kraj") LocalTime kraj,
            @Param("terenId") Long terenId,
            @Param("trenerId") Long trenerId);
//ovo @Param mapira parametre metode na imenovane parametre u JPQL upitu.


    //logika brisanja,query metoda pronalazi jedinst teren po ovim param sto proesljedujem
    Optional<Termin> findByVremePocetkaAndTerenIdAndDanUNedelji(
            LocalTime vremePocetka,
            Long terenId,
            String danUNedelji
    );

    List<Termin> findByDanUNedeljiAndTerenId(String dan, Long terenId);

    List<Termin> findBySportskaGrupaId(Long grupaId);

    List<Termin> findByTrenerId(Long trenerId);

    List<Termin> findBySportskaGrupaTakmicarska(boolean takmicarska);//query metoda
    //Spring Data JPA može da "uđe" u vezane entitete.
    // SportskaGrupaTakmicarska znači "pronađi termine gde je sportskaGrupa povezana sa ovim terminom,
    // a zatim proveri da li je svojstvo takmicarska te grupe jednako datom boolean parametru."

    List<Termin> findByTipTermina(TipTermina tipTermina);

}
