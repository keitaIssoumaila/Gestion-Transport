package com.transport.GestionTransport.controllers;

import com.transport.GestionTransport.dtos.SouscriptionDTO;
import com.transport.GestionTransport.dtos.SouscriptionResponseDTO;
import com.transport.GestionTransport.services.SouscriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/souscriptions")
@CrossOrigin(origins = "*") // Autoriser tous les domaines (à adapter si nécessaire)
public class SouscriptionController {

    @Autowired
    private SouscriptionService souscriptionService;

    // 🔹 Créer une souscription
    @PostMapping("/souscrire")
    public ResponseEntity<?> creerSouscription(@RequestBody SouscriptionDTO dto) {
        return souscriptionService.souscrire(dto);
    }

    // 🔹 Récupérer toutes les souscriptions (avec infos client + abonnement)
    @GetMapping
    public List<SouscriptionResponseDTO> getAllSouscriptions() {
        return souscriptionService.getAllSouscriptions();
    }

    // 🔹 Récupérer les souscriptions d’un client
    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getSouscriptionsParClient(@PathVariable Long clientId) {
        return souscriptionService.getSouscriptionsParClient(clientId);
    }

    // 🔹 Vérifier si un client a une souscription active
    @GetMapping("/client/{clientId}/active")
    public ResponseEntity<?> verifierSouscriptionActive(@PathVariable Long clientId) {
        return souscriptionService.verifierSouscriptionActive(clientId);
    }

    // 🔹 Supprimer une souscription
    @DeleteMapping("supprimer/{id}")
    public ResponseEntity<?> supprimerSouscription(@PathVariable Long id) {
        return souscriptionService.supprimerSouscription(id);
    }

    // 🔹 Changer le statut d'une souscription
    @PutMapping("/{id}/statut")
    public ResponseEntity<?> changerStatut(@PathVariable Long id, @RequestParam String statut) {
        return souscriptionService.changerStatutSouscription(id, statut);
    }

    // 🔹 Mettre à jour tous les statuts automatiquement
    @PutMapping("/update-statut")
    public ResponseEntity<?> mettreAJourTousLesStatuts() {
        return souscriptionService.mettreAJourTousLesStatuts();
    }

    // 🔹 Compter les souscriptions actives
    @GetMapping("/actives/count")
    public long compterActives() {
        return souscriptionService.compterSouscriptionsActives();
    }
}
