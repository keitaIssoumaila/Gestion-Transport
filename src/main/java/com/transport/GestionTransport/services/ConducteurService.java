package com.transport.GestionTransport.services;

import com.transport.GestionTransport.ditos.ConducteurDTO;
import com.transport.GestionTransport.entities.Bus;
import com.transport.GestionTransport.entities.Conducteur;
import com.transport.GestionTransport.repositories.BusRepository;
import com.transport.GestionTransport.repositories.ConducteurRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConducteurService {

    private final ConducteurRepository conducteurRepository;
    private final BusRepository busRepository;

    public ConducteurService(ConducteurRepository conducteurRepository, BusRepository busRepository) {
        this.conducteurRepository = conducteurRepository;
        this.busRepository = busRepository;
    }

    public List<Conducteur> getAllConducteurs() {
        return conducteurRepository.findAll();
    }

    public ResponseEntity<?> createConducteur(ConducteurDTO dto) {
        Bus bus = busRepository.findById(dto.getBusId()).orElse(null);
        if (bus == null) return ResponseEntity.badRequest().body(" Bus non trouvé.");

        Conducteur conducteur = new Conducteur();
        conducteur.setNom(dto.getNom());
        conducteur.setPrenom(dto.getPrenom());
        conducteur.setDateNaissance(dto.getDateNaissance());
        conducteur.setTelephone(dto.getTelephone());
        conducteur.setBus(bus);

        conducteurRepository.save(conducteur);
        return ResponseEntity.ok(conducteur);
    }

    public ResponseEntity<?> editConducteur(Long id, ConducteurDTO dto) {
        Conducteur conducteur = conducteurRepository.findById(id).orElse(null);
        if (conducteur == null) return ResponseEntity.notFound().build();

        conducteur.setNom(dto.getNom());
        conducteur.setPrenom(dto.getPrenom());
        conducteur.setDateNaissance(dto.getDateNaissance());
        conducteur.setTelephone(dto.getTelephone());

        if (dto.getBusId() != null) {
            Bus bus = busRepository.findById(dto.getBusId()).orElse(null);
            if (bus == null) return ResponseEntity.badRequest().body(" Bus non trouvé.");
            conducteur.setBus(bus);
        }

        conducteurRepository.save(conducteur);
        return ResponseEntity.ok(conducteur);
    }

    public ResponseEntity<?> getConducteurById(Long id) {
        Conducteur conducteur = conducteurRepository.findById(id).orElse(null);
        if (conducteur == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(conducteur);
        }
    }

    public ResponseEntity<?> deleteConducteur(Long id) {
        if (!conducteurRepository.existsById(id))
            return ResponseEntity.notFound().build();

        conducteurRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> searchConducteur(String query) {
        List<Conducteur> conducteurs = conducteurRepository
                .findAllByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(query, query);
        if (conducteurs.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(conducteurs);
    }
}

