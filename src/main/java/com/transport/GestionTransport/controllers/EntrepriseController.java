package com.transport.GestionTransport.controllers;

import com.transport.GestionTransport.ditos.EntrepriseDTO;
import com.transport.GestionTransport.entities.Entreprise;
import com.transport.GestionTransport.services.EntrepriseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("entreprise")
@CrossOrigin("*")
public class EntrepriseController {

    private final EntrepriseService entrepriseService;

    public EntrepriseController(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    @GetMapping
    public List<Entreprise> getAll() {
        return entrepriseService.getAllEntreprises();
    }

    @PostMapping("creer")
    public ResponseEntity<?> createEntreprise(@RequestBody EntrepriseDTO dto) {
        return entrepriseService.createEntreprise(dto);
    }

    @PutMapping("modifier/{id}")
    public ResponseEntity<?> editEntreprise(@PathVariable Long id, @RequestBody EntrepriseDTO dto) {
        return entrepriseService.editEntreprise(id, dto);
    }

    @GetMapping("recherche/{id}")
    public ResponseEntity<?> getEntrepriseById(@PathVariable Long id) {
        return entrepriseService.getEntrepriseById(id);
    }

    @DeleteMapping("supprimer/{id}")
    public ResponseEntity<?> deleteEntreprise(@PathVariable Long id) {
        return entrepriseService.deleteEntreprise(id);
    }

    @GetMapping("rechercher")
    public ResponseEntity<?> searchEntreprise(@RequestParam String query) {
        return entrepriseService.searchEntreprise(query);
    }
}

