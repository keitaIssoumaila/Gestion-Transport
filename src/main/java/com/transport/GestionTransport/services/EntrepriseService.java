package com.transport.GestionTransport.services;

import com.transport.GestionTransport.ditos.EntrepriseDTO;
import com.transport.GestionTransport.entities.Entreprise;
import com.transport.GestionTransport.repositories.EntrepriseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntrepriseService {

    private final EntrepriseRepository entrepriseRepository;

    public EntrepriseService(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }

    public List<Entreprise> getAllEntreprises() {
        return entrepriseRepository.findAll();
    }

    public ResponseEntity<?> createEntreprise(EntrepriseDTO dto) {
        Entreprise entreprise = new Entreprise();
        entreprise.setNom(dto.getNom());
        entreprise.setAdresse(dto.getAdresse());
        entreprise.setLogo(dto.getLogo());
        entreprise.setTelephone(dto.getTelephone());
        entreprise.setEmail(dto.getEmail());

        entrepriseRepository.save(entreprise);
        return ResponseEntity.ok(entreprise);
    }

    public ResponseEntity<?> editEntreprise(Long id, EntrepriseDTO dto) {
        Entreprise entreprise = entrepriseRepository.findById(id).orElse(null);
        if (entreprise == null) {
            return ResponseEntity.notFound().build();
        }
        entreprise.setNom(dto.getNom());
        entreprise.setAdresse(dto.getAdresse());
        entreprise.setLogo(dto.getLogo());
        entreprise.setTelephone(dto.getTelephone());
        entreprise.setEmail(dto.getEmail());

        entrepriseRepository.save(entreprise);
        return ResponseEntity.ok(entreprise);
    }

    public ResponseEntity<?> getEntrepriseById(Long id) {
        Entreprise entreprise = entrepriseRepository.findById(id).orElse(null);
        if (entreprise == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entreprise);
        }
    }


    public ResponseEntity<?> deleteEntreprise(Long id) {
        if (!entrepriseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        entrepriseRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> searchEntreprise(String query) {
        List<Entreprise> entreprises = entrepriseRepository.findAllByNomContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query);
        if (entreprises.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(entreprises);
    }
}

