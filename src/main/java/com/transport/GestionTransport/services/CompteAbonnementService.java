package com.transport.GestionTransport.services;

import com.transport.GestionTransport.ditos.CompteAbonnementDTO;
import com.transport.GestionTransport.entities.Client;
import com.transport.GestionTransport.entities.CompteAbonnement;
import com.transport.GestionTransport.repositories.ClientRepository;
import com.transport.GestionTransport.repositories.CompteAbonnementRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompteAbonnementService {

    private final CompteAbonnementRepository compteAbonnementRepository;
    private final ClientRepository clientRepository;

    public CompteAbonnementService(CompteAbonnementRepository compteAbonnementRepository, ClientRepository clientRepository) {
        this.compteAbonnementRepository = compteAbonnementRepository;
        this.clientRepository = clientRepository;
    }

    public List<CompteAbonnement> getAbonnements() {
        return compteAbonnementRepository.findAll();
    }

    public ResponseEntity<?> createAbonnement(CompteAbonnementDTO dto) {
        Client client = clientRepository.findById(dto.getClientId()).orElse(null);
        if (client == null) return ResponseEntity.badRequest().body(" Client introuvable.");

        CompteAbonnement abonnement = new CompteAbonnement();
        abonnement.setReference(dto.getReference());
        abonnement.setDate(dto.getDate());
        abonnement.setStatusAbonnement(dto.getStatusAbonnement());
        abonnement.setDateDebutAbonnement(dto.getDateDebutAbonnement());
        abonnement.setDateFinAbonnement(dto.getDateFinAbonnement());
        abonnement.setClient(client);

        compteAbonnementRepository.save(abonnement);
        return ResponseEntity.ok(abonnement);
    }

    public ResponseEntity<?> editAbonnement(Long id, CompteAbonnementDTO dto) {
        CompteAbonnement abonnement = compteAbonnementRepository.findById(id).orElse(null);
        if (abonnement == null) return ResponseEntity.notFound().build();

        abonnement.setReference(dto.getReference());
        abonnement.setDate(dto.getDate());
        abonnement.setStatusAbonnement(dto.getStatusAbonnement());
        abonnement.setDateDebutAbonnement(dto.getDateDebutAbonnement());
        abonnement.setDateFinAbonnement(dto.getDateFinAbonnement());
        compteAbonnementRepository.save(abonnement);
        return ResponseEntity.ok(abonnement);
    }

    public ResponseEntity<?> getAbonnementById(Long id) {
        CompteAbonnement compteAbonnement = compteAbonnementRepository.findById(id).orElse(null);
        if (compteAbonnement == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(compteAbonnement);
        }
    }

    public ResponseEntity<?> deleteAbonnement(Long id) {
        if (!compteAbonnementRepository.existsById(id))
            return ResponseEntity.notFound().build();

        compteAbonnementRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> searchAbonnement(String query) {
        List<CompteAbonnement> result = compteAbonnementRepository
                .findAllByReferenceContainingIgnoreCaseOrStatusAbonnementContainingIgnoreCase(query, query);
        if (result.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(result);
    }
}

