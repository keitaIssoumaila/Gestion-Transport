package com.transport.GestionTransport.controllers;

import com.transport.GestionTransport.ditos.CompteAbonnementDTO;
import com.transport.GestionTransport.entities.CompteAbonnement;
import com.transport.GestionTransport.services.CompteAbonnementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("abonnement")
@CrossOrigin("*")
public class CompteAbonnementController {

    private final CompteAbonnementService compteAbonnementService;

    public CompteAbonnementController(CompteAbonnementService compteAbonnementService) {
        this.compteAbonnementService = compteAbonnementService;
    }

    @GetMapping
    public List<CompteAbonnement> getAll() {
        return compteAbonnementService.getAbonnements();
    }

    @PostMapping("creer")
    public ResponseEntity<?> createAbonnement(@RequestBody CompteAbonnementDTO dto) {
        return compteAbonnementService.createAbonnement(dto);
    }

    @PutMapping("modifier/{id}")
    public ResponseEntity<?> editAbonnement(@PathVariable Long id, @RequestBody CompteAbonnementDTO dto) {
        return compteAbonnementService.editAbonnement(id, dto);
    }

    @GetMapping("recherche/{id}")
    public ResponseEntity<?> getAbonnementById(@PathVariable Long id) {
        return compteAbonnementService.getAbonnementById(id);
    }

    @DeleteMapping("supprimer/{id}")
    public ResponseEntity<?> deleteAbonnement(@PathVariable Long id) {
        return compteAbonnementService.deleteAbonnement(id);
    }

    @GetMapping("rechercher")
    public ResponseEntity<?> searchAbonnement(@RequestParam String query) {
        return compteAbonnementService.searchAbonnement(query);
    }

    @GetMapping("verifier-validite/{id}")
    public ResponseEntity<?> verifierValidite(@PathVariable Long id) {
        return compteAbonnementService.verifierValiditeAbonnement(id);
    }

    //changer le status d'abonnement mannuellement
    @PutMapping("changer-status/{id}")
    public ResponseEntity<?> changerStatus(@PathVariable Long id, @RequestParam String status) {
        return compteAbonnementService.changerStatus(id, status);
    }

    // Mise à jour globale des statuts d'abonnement
    @PutMapping("/mise-a-jour-status")
    public ResponseEntity<?> mettreAJourTousLesStatuts() {
        return compteAbonnementService.mettreAJourTousLesStatuts();
    }

    // ✅ Lister les abonnements d’un client donné
    @GetMapping("par-client/{clientId}")
    public ResponseEntity<?> getAbonnementsParClient(@PathVariable Long clientId) {
        return compteAbonnementService.getAbonnementsParClient(clientId);
    }

    // ✅ Compter le nombre d’abonnements actifs
    @GetMapping("actifs/count")
    public ResponseEntity<?> compterAbonnementsActifs() {
        long count = compteAbonnementService.compterAbonnementsActifs();
        return ResponseEntity.ok(count);
    }
}

