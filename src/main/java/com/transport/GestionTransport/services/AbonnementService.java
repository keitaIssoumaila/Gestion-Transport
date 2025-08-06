package com.transport.GestionTransport.services;

import com.transport.GestionTransport.dtos.AbonnementDTO;
import com.transport.GestionTransport.entities.Abonnement;
import com.transport.GestionTransport.repositories.AbonnementRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbonnementService {

    private final AbonnementRepository abonnementRepository;

    public AbonnementService(AbonnementRepository abonnementRepository) {
        this.abonnementRepository = abonnementRepository;
    }

    // 🔹 Lire tous les abonnements
    public List<Abonnement> getAbonnements() {
        return abonnementRepository.findAll();
    }

    // 🔹 Créer un abonnement
    public ResponseEntity<?> createAbonnement(AbonnementDTO dto) {
        try {
            Abonnement abonnement = new Abonnement();
            abonnement.setNom(dto.getNom());
            abonnement.setDescription(dto.getDescription());
            abonnement.setMontant(dto.getMontant());
            abonnement.setDureeMois(dto.getDureeMois());
            abonnement.setActif(true); // actif par défaut

            abonnementRepository.save(abonnement);
            return ResponseEntity.ok().body("Abonnement créé avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur création abonnement : " + e.getMessage());
        }
    }

    // 🔹 Modifier un abonnement
    public ResponseEntity<?> editAbonnement(Long id, AbonnementDTO dto) {
        Abonnement abonnement = abonnementRepository.findById(id).orElse(null);
        if (abonnement == null) {
            return ResponseEntity.notFound().build();
        }

        abonnement.setNom(dto.getNom());
        abonnement.setDescription(dto.getDescription());
        abonnement.setMontant(dto.getMontant());
        abonnement.setDureeMois(dto.getDureeMois());
        abonnement.setActif(dto.getActif());

        abonnementRepository.save(abonnement);
        return ResponseEntity.ok("Abonnement modifié avec succès.");
    }

    // 🔹 Supprimer un abonnement
    public ResponseEntity<?> deleteAbonnement(Long id) {
        Abonnement abonnement = abonnementRepository.findById(id).orElse(null);
        if (abonnement == null) {
            return ResponseEntity.notFound().build();
        }

        abonnementRepository.delete(abonnement);
        return ResponseEntity.ok("Abonnement supprimé avec succès.");
    }

    // 🔹 Rechercher un abonnement par nom (ou partie)
    public ResponseEntity<?> searchAbonnement(String query) {
        List<Abonnement> resultats = abonnementRepository.findByNomContainingIgnoreCase(query);
        if (resultats.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(resultats);
        }
    }

    // 🔹 Lire un abonnement par ID
    public ResponseEntity<?> getAbonnementById(Long id) {
        Abonnement abonnement = abonnementRepository.findById(id).orElse(null);
        if (abonnement == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(abonnement);
        }
    }

    // 🔹 Activer / désactiver un abonnement
    public ResponseEntity<?> changerStatut(Long id, boolean actif) {
        Abonnement abonnement = abonnementRepository.findById(id).orElse(null);
        if (abonnement == null) {
            return ResponseEntity.notFound().build();
        }

        abonnement.setActif(actif);
        abonnementRepository.save(abonnement);
        return ResponseEntity.ok("Statut mis à jour.");
    }
}
