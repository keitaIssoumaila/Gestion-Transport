package com.transport.GestionTransport.services;

import com.transport.GestionTransport.ditos.ProprietaireDTO;
import com.transport.GestionTransport.entities.Proprietaire;
import com.transport.GestionTransport.repositories.ProprietaireRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProprietaireService {

    private final ProprietaireRepository proprietaireRepository;

    public ProprietaireService(ProprietaireRepository proprietaireRepository) {
        this.proprietaireRepository = proprietaireRepository;
    }

    public List<Proprietaire> getAllProprietaires() {
        return proprietaireRepository.findAll();
    }

    public ResponseEntity<?> getProprietaireById(Long id) {
        Proprietaire proprietaire = proprietaireRepository.findProprietaireById(id);
        if (proprietaire == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(proprietaire);
    }

    public ResponseEntity<?> createProprietaire(ProprietaireDTO proprietaireDTO) {
        Proprietaire proprietaire = new Proprietaire();
        proprietaire.setCin(proprietaireDTO.getCin());
        proprietaire.setNom(proprietaireDTO.getNom());
        proprietaire.setPrenom(proprietaireDTO.getPrenom());
        proprietaire.setDateNaissance(proprietaireDTO.getDateNaissance());
        proprietaireRepository.save(proprietaire);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> editProprietaire(Long id, ProprietaireDTO proprietaireDTO) {
        Proprietaire proprietaire = proprietaireRepository.findProprietaireById(id);
        if (proprietaire == null) {
            return ResponseEntity.notFound().build();
        }
        proprietaire.setCin(proprietaireDTO.getCin());
        proprietaire.setNom(proprietaireDTO.getNom());
        proprietaire.setPrenom(proprietaireDTO.getPrenom());
        proprietaire.setDateNaissance(proprietaireDTO.getDateNaissance());
        proprietaireRepository.save(proprietaire);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deleteProprietaire(Long id) {
        Proprietaire proprietaire = proprietaireRepository.findProprietaireById(id);
        if (proprietaire == null) {
            return ResponseEntity.notFound().build();
        }
        proprietaireRepository.delete(proprietaire);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> searchProprietaire(String query) {
        List<Proprietaire> proprietaires = proprietaireRepository
                .findAllByNomContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrCinContainingIgnoreCase(query, query, query);
        if (proprietaires.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(proprietaires);
    }
}

