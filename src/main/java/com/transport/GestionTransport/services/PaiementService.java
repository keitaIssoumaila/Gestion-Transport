package com.transport.GestionTransport.services;

import com.transport.GestionTransport.ditos.PaiementDTO;
import com.transport.GestionTransport.entities.Client;
import com.transport.GestionTransport.entities.Paiement;
import com.transport.GestionTransport.repositories.ClientRepository;
import com.transport.GestionTransport.repositories.PaiementRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaiementService {

    private final PaiementRepository paiementRepository;
    private final ClientRepository clientRepository;

    public PaiementService(PaiementRepository paiementRepository, ClientRepository clientRepository) {
        this.paiementRepository = paiementRepository;
        this.clientRepository = clientRepository;
    }

    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

    public ResponseEntity<?> getPaiementById(Long id) {
        Paiement paiement = paiementRepository.findPaiementById(id);
        if (paiement == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paiement);
    }

    public ResponseEntity<?> createPaiement(PaiementDTO paiementDTO) {
        Client client = clientRepository.findById(paiementDTO.getClientId()).orElse(null);
        if (client == null) {
            return ResponseEntity.badRequest().body(" Client non trouver");
        }
        Paiement paiement = new Paiement();
        paiement.setReference(paiementDTO.getReference());
        paiement.setDate(paiementDTO.getDate());
        paiement.setMontant(paiementDTO.getMontant());
        paiement.setModePaiement(paiementDTO.getModePaiement());
        paiement.setClient(client);
        paiementRepository.save(paiement);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> editPaiement(Long id, PaiementDTO paiementDTO) {
        Paiement paiement = paiementRepository.findPaiementById(id);
        if (paiement == null) {
            return ResponseEntity.notFound().build();
        }
        paiement.setReference(paiementDTO.getReference());
        paiement.setDate(paiementDTO.getDate());
        paiement.setMontant(paiementDTO.getMontant());
        paiement.setModePaiement(paiementDTO.getModePaiement());
        paiementRepository.save(paiement);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deletePaiement(Long id) {
        Paiement paiement = paiementRepository.findPaiementById(id);
        if (paiement == null) {
            return ResponseEntity.notFound().build();
        }
        paiementRepository.delete(paiement);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> searchPaiement(String query) {
        List<Paiement> paiements = paiementRepository
                .findAllByReferenceContainingIgnoreCaseOrModePaiementContainingIgnoreCase(query, query);
        if (paiements.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(paiements);
    }
}

