package com.transport.GestionTransport.services;

import com.transport.GestionTransport.dtos.TrajetDTO;
import com.transport.GestionTransport.entities.HoraireTrajet;
import com.transport.GestionTransport.entities.Trajet;
import com.transport.GestionTransport.repositories.HoraireTrajetRepository;
import com.transport.GestionTransport.repositories.TrajetRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TrajetService {

    private final TrajetRepository trajetRepository;
    private final HoraireTrajetRepository horaireTrajetRepository;

    public TrajetService(TrajetRepository trajetRepository, HoraireTrajetRepository horaireTrajetRepository) {
        this.trajetRepository = trajetRepository;
        this.horaireTrajetRepository = horaireTrajetRepository;
    }

    public List<Trajet> getAllTrajets() {
        return trajetRepository.findAll();
    }

    public ResponseEntity<?> getTrajetById(Long id) {
        Trajet trajet = trajetRepository.findTrajetById(id);
        if (trajet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(trajet);
    }

    public ResponseEntity<?> createTrajet(TrajetDTO trajetDTO) {
        Trajet trajet = new Trajet();
        trajet.setNom(trajetDTO.getNom());
        trajet.setDate(trajetDTO.getDate());
        trajet.setPointDepart(trajetDTO.getPointDepart());
        trajet.setPointArriver(trajetDTO.getPointArriver());

        HoraireTrajet horaire = horaireTrajetRepository.findHoraireTrajetById(trajetDTO.getHoraireTrajetId());
        if (horaire == null) {
            return ResponseEntity.badRequest().body(" HoraireTrajet non trouvé");
        }

        trajet.setHoraireTrajet(horaire);
        trajetRepository.save(trajet);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> editTrajet(Long id, TrajetDTO trajetDTO) {
        Trajet trajet = trajetRepository.findTrajetById(id);
        if (trajet == null) {
            return ResponseEntity.notFound().build();
        }
        trajet.setNom(trajetDTO.getNom());
        trajet.setDate(trajetDTO.getDate());
        trajet.setPointDepart(trajetDTO.getPointDepart());
        trajet.setPointArriver(trajetDTO.getPointArriver());

        HoraireTrajet horaire = horaireTrajetRepository.findHoraireTrajetById(trajetDTO.getHoraireTrajetId());
        if (horaire != null) {
            trajet.setHoraireTrajet(horaire);
        }

        trajetRepository.save(trajet);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deleteTrajet(Long id) {
        Trajet trajet = trajetRepository.findTrajetById(id);
        if (trajet == null) {
            return ResponseEntity.notFound().build();
        }
        trajetRepository.delete(trajet);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> searchTrajet(String query) {
        List<Trajet> trajets = trajetRepository
                .findAllByNomContainingIgnoreCaseOrPointDepartContainingIgnoreCase(query, query);
        if (trajets.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(trajets);
    }

    public List<Trajet> getTrajetsByDate(LocalDate date) {
        return trajetRepository.findAllByDate(date);
    }

    public List<Trajet> getUpcomingTrajets() {
        return trajetRepository.findByDateAfter(LocalDate.now());
    }


}

