package com.transport.GestionTransport.controllers;

import com.transport.GestionTransport.dtos.AbonnementDTO;
import com.transport.GestionTransport.services.AbonnementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/abonnements")
@RequiredArgsConstructor
public class AbonnementController {

    private final AbonnementService abonnementService;

    // 🔹 Créer un nouvel abonnement
    @PostMapping("creer")
    public ResponseEntity<?> createAbonnement(@RequestBody AbonnementDTO dto) {
        return abonnementService.createAbonnement(dto);
    }

    // 🔹 Lire tous les abonnements
    @GetMapping
    public ResponseEntity<?> getAllAbonnements() {
        return ResponseEntity.ok(abonnementService.getAbonnements());
    }

    // 🔹 Lire un abonnement par ID
    @GetMapping("rechercher/{id}")
    public ResponseEntity<?> getAbonnementById(@PathVariable Long id) {
        return abonnementService.getAbonnementById(id);
    }

    // 🔹 Modifier un abonnement
    @PutMapping("modifier/{id}")
    public ResponseEntity<?> editAbonnement(@PathVariable Long id, @RequestBody AbonnementDTO dto) {
        return abonnementService.editAbonnement(id, dto);
    }

    // 🔹 Supprimer un abonnement
    @DeleteMapping("supprimer/{id}")
    public ResponseEntity<?> deleteAbonnement(@PathVariable Long id) {
        return abonnementService.deleteAbonnement(id);
    }

    // 🔹 Rechercher un abonnement par nom
    @GetMapping("search")
    public ResponseEntity<?> searchAbonnement(@RequestParam String query) {
        return abonnementService.searchAbonnement(query);
    }

    // 🔹 Activer ou désactiver un abonnement
    @PatchMapping("statut/{id}")
    public ResponseEntity<?> changerStatut(@PathVariable Long id, @RequestParam boolean actif) {
        return abonnementService.changerStatut(id, actif);
    }
}
