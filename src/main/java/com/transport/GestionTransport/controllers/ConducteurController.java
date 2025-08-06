package com.transport.GestionTransport.controllers;

import com.transport.GestionTransport.dtos.ConducteurDTO;
import com.transport.GestionTransport.entities.Conducteur;
import com.transport.GestionTransport.services.ConducteurService;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> createConducteur(@Valid @RequestBody ConducteurDTO dto) {
        return conducteurService.createConducteur(dto);
    }

    @PutMapping("modifier/{id}")
    public ResponseEntity<?> editConducteur(@PathVariable Long id, @Valid @RequestBody ConducteurDTO dto) {
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

    // ✅ Obtenir tous les conducteurs liés à un bus
    @GetMapping("/bus/{busId}/all")
    public ResponseEntity<?> getAllConducteursByBusId(@PathVariable Long busId) {
        return conducteurService.getConducteursByBusId(busId);
    }

    // ✅ Obtenir le conducteur unique d’un bus (ex : conducteur actif)
    @GetMapping("/bus/{busId}/unique")
    public ResponseEntity<?> getConducteurUniqueByBusId(@PathVariable Long busId) {
        return conducteurService.getConducteurByBusId(busId);
    }
}

