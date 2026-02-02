package com.raf.sport_termini_service.service;
import com.raf.sport_termini_service.domain.Trener;
import com.raf.sport_termini_service.repository.TrenerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TrenerService {
    private final TrenerRepository trenerRepository;

    public TrenerService(TrenerRepository trenerRepository) {
        this.trenerRepository = trenerRepository;
    }

    public Trener createTrener(Trener trener) {
        return trenerRepository.save(trener);
    }

    public List<Trener> getAllTreneri() {
        return trenerRepository.findAll();
    }

    public List<Trener> getTreneriByImeAndPrezime(String ime, String prezime) {
        return trenerRepository.findByImeAndPrezime(ime, prezime);
    }

}
