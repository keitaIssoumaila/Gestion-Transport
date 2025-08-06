package com.transport.GestionTransport.services;

import com.transport.GestionTransport.dtos.HoraireTrajetDTO;
import com.transport.GestionTransport.entities.HoraireTrajet;
import com.transport.GestionTransport.repositories.HoraireTrajetRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoraireTrajetService {

    private final HoraireTrajetRepository horaireTrajetRepository;

    public HoraireTrajetService(HoraireTrajetRepository horaireTrajetRepository) {
        this.horaireTrajetRepository = horaireTrajetRepository;
    }

    public List<HoraireTrajet> getAllHoraireTrajet() {
        return horaireTrajetRepository.findAll();
    }

    public ResponseEntity<?> createHoraireTrajet(HoraireTrajetDTO dto) {
        HoraireTrajet horaire = new HoraireTrajet();
        horaire.setReference(dto.getReference());
        horaire.setDateDebut(dto.getDateDebut());
        horaire.setHeureDebut(dto.getHeureDebut());
        horaire.setDateFin(dto.getDateFin());

        horaireTrajetRepository.save(horaire);
        return ResponseEntity.ok(horaire);
    }

    public ResponseEntity<?> editHoraireTrajet(Long id, HoraireTrajetDTO dto) {
        HoraireTrajet horaire = horaireTrajetRepository.findById(id).orElse(null);
        if (horaire == null) return ResponseEntity.notFound().build();

        horaire.setReference(dto.getReference());
        horaire.setDateDebut(dto.getDateDebut());
        horaire.setHeureDebut(dto.getHeureDebut());
        horaire.setDateFin(dto.getDateFin());

        horaireTrajetRepository.save(horaire);
        return ResponseEntity.ok(horaire);
    }

    public ResponseEntity<?> getHoraireTrajetById(Long id) {
        HoraireTrajet horaireTrajet = horaireTrajetRepository.findById(id).orElse(null);
        if (horaireTrajet == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(horaireTrajet);
        }
    }

    public ResponseEntity<?> deleteHoraireTrajet(Long id) {
        if (!horaireTrajetRepository.existsById(id)) return ResponseEntity.notFound().build();
        horaireTrajetRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> searchHoraireTrajet(String query) {
        List<HoraireTrajet> result = horaireTrajetRepository.findAllByReferenceContainingIgnoreCase(query);
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(result);
    }
    public ResponseEntity<?> getHoraireTrajetsByBus(Long busId) {
        List<HoraireTrajet> horaires = horaireTrajetRepository.findAllByBusId(busId);
        return horaires.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(horaires);
    }

}

