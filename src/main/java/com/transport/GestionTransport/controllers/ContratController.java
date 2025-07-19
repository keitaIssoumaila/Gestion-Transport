package com.transport.GestionTransport.controllers;

import com.transport.GestionTransport.ditos.ContratDTO;
import com.transport.GestionTransport.entities.Contrat;
import com.transport.GestionTransport.services.ContratService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("contrat")
@CrossOrigin("*")
public class ContratController {

    private final ContratService contratService;

    public ContratController(ContratService contratService) {
        this.contratService = contratService;
    }

    @GetMapping
    public List<Contrat> getAll() {
        return contratService.getAllContrats();
    }

    @PostMapping("creer")
    public ResponseEntity<?> create(@RequestBody ContratDTO dto) {
        return contratService.createContrat(dto);
    }

    @PutMapping("modifier/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody ContratDTO dto) {
        return contratService.editContrat(id, dto);
    }

    @GetMapping("recherche/{id}")
    public ResponseEntity<?> getContratById(@PathVariable Long id) {
        return contratService.getContratById(id);
    }

    @DeleteMapping("supprimer/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return contratService.deleteContrat(id);
    }

    @GetMapping("rechercher")
    public ResponseEntity<?> search(@RequestParam String query) {
        return contratService.searchContrat(query);
    }
}

