package com.transport.GestionTransport.controllers;

import com.transport.GestionTransport.ditos.ConducteurDTO;
import com.transport.GestionTransport.entities.Conducteur;
import com.transport.GestionTransport.services.ConducteurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("conducteur")
@CrossOrigin("*")
public class ConducteurController {

    private final ConducteurService conducteurService;

    public ConducteurController(ConducteurService conducteurService) {
        this.conducteurService = conducteurService;
    }

    @GetMapping
    public List<Conducteur> getAll() {
        return conducteurService.getAllConducteurs();
    }

    @PostMapping("creer")
    public ResponseEntity<?> createConducteur(@RequestBody ConducteurDTO dto) {
        return conducteurService.createConducteur(dto);
    }

    @PutMapping("modifier/{id}")
    public ResponseEntity<?> editConducteur(@PathVariable Long id, @RequestBody ConducteurDTO dto) {
        return conducteurService.editConducteur(id, dto);
    }

    @GetMapping("recherche/{id}")
    public ResponseEntity<?> getConducteurById(@PathVariable Long id) {
        return conducteurService.getConducteurById(id);
    }

    @DeleteMapping("supprimer/{id}")
    public ResponseEntity<?> deleteConducteur(@PathVariable Long id) {
        return conducteurService.deleteConducteur(id);
    }

    @GetMapping("rechercher")
    public ResponseEntity<?> searchConducteur(@RequestParam String query) {
        return conducteurService.searchConducteur(query);
    }
}

