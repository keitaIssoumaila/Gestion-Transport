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

    //changer le status d'abonnement mannuellement
    public ResponseEntity<?> changerStatus(Long id, String nouveauStatus) {
        CompteAbonnement abonnement = compteAbonnementRepository.findById(id).orElse(null);
        if (abonnement == null) return ResponseEntity.notFound().build();

        abonnement.setStatusAbonnement(nouveauStatus);
        compteAbonnementRepository.save(abonnement);
        return ResponseEntity.ok(abonnement);
    }

    // Mise à jour globale des statuts d'abonnement
    public ResponseEntity<?> mettreAJourTousLesStatuts() {
        List<CompteAbonnement> abonnements = compteAbonnementRepository.findAll();
        for (CompteAbonnement abonnement : abonnements) {
            boolean estValide = abonnement.getDateDebutAbonnement().before(new java.util.Date()) &&
                    abonnement.getDateFinAbonnement().after(new java.util.Date());

            if (estValide && !"Actif".equalsIgnoreCase(abonnement.getStatusAbonnement())) {
                abonnement.setStatusAbonnement("Actif");
                compteAbonnementRepository.save(abonnement);
            } else if (!estValide && !"Expiré".equalsIgnoreCase(abonnement.getStatusAbonnement())) {
                abonnement.setStatusAbonnement("Expiré");
                compteAbonnementRepository.save(abonnement);
            }
        }
        return ResponseEntity.ok("Statuts des abonnements mis à jour.");
    }


    //Liste d'abonnement d'un client donné
    public ResponseEntity<?> getAbonnementsParClient(Long clientId) {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client == null) return ResponseEntity.badRequest().body("Client introuvable.");

        List<CompteAbonnement> abonnements = compteAbonnementRepository.findAllByClient(client);
        return ResponseEntity.ok(abonnements);
    }

    //Vérifie la validité d'un compte
    public ResponseEntity<?> verifierValiditeAbonnement(Long id) {
        CompteAbonnement abonnement = compteAbonnementRepository.findById(id).orElse(null);
        if (abonnement == null) return ResponseEntity.notFound().build();

        boolean estValide = abonnement.getDateDebutAbonnement().before(new java.util.Date()) &&
                abonnement.getDateFinAbonnement().after(new java.util.Date());

        return ResponseEntity.ok(estValide ? "Abonnement valide." : "Abonnement expiré.");
    }

    //Nombre d'abonnement actifs
    public long compterAbonnementsActifs() {
        return compteAbonnementRepository.countByStatusAbonnementIgnoreCase("Actif");
    }

}

