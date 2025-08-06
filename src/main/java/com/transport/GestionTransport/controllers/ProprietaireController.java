package com.transport.GestionTransport.controllers;

import com.transport.GestionTransport.dtos.ProprietaireDTO;
import com.transport.GestionTransport.entities.Proprietaire;
import com.transport.GestionTransport.services.ProprietaireService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proprietaires")
@CrossOrigin("*")
public class ProprietaireController {

    private final ProprietaireService proprietaireService;

    public ProprietaireController(ProprietaireService proprietaireService) {
        this.proprietaireService = proprietaireService;
    }

    @GetMapping
    public List<Proprietaire> getAllProprietaires() {
        return proprietaireService.getAllProprietaires();
    }

    @PostMapping("creer")
    public ResponseEntity<?> createProprietaire(@Valid @RequestBody ProprietaireDTO proprietaireDTO) {
        return proprietaireService.createProprietaire(proprietaireDTO);
    }

    @GetMapping("rechercher/{id}")
    public ResponseEntity<?> getProprietaireById(@PathVariable Long id) {
        return proprietaireService.getProprietaireById(id);
    }

    @PutMapping("modifier/{id}")
    public ResponseEntity<?> editProprietaire(@PathVariable Long id, @Valid @RequestBody ProprietaireDTO proprietaireDTO) {
        return proprietaireService.editProprietaire(id, proprietaireDTO);
    }

    @DeleteMapping("supprimer/{id}")
    public ResponseEntity<?> deleteProprietaire(@PathVariable Long id) {
        return proprietaireService.deleteProprietaire(id);
    }

    @GetMapping("rechercher")
    public ResponseEntity<?> searchProprietaire(@RequestParam String query) {
        return proprietaireService.searchProprietaire(query);
    }
}

