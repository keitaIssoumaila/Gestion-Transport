package com.transport.GestionTransport.controllers;

import com.transport.GestionTransport.dtos.PaiementDTO;
import com.transport.GestionTransport.entities.Paiement;
import com.transport.GestionTransport.services.PaiementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import java.util.List;

@RestController
@RequestMapping("/paiements")
@CrossOrigin("*")
public class PaiementController {

    private final PaiementService paiementService;

    public PaiementController(PaiementService paiementService) {
        this.paiementService = paiementService;
    }

    @GetMapping
    public List<Paiement> getAllPaiements() {
        return paiementService.getAllPaiements();
    }

    @GetMapping("rechercher/{id}")
    public ResponseEntity<?> getPaiementById(@PathVariable Long id) {
        return paiementService.getPaiementById(id);
    }

    @PostMapping("creer")
    public ResponseEntity<?> createPaiement(@RequestBody PaiementDTO paiementDTO) {
        return paiementService.createPaiement(paiementDTO);
    }

    @PutMapping("modifier/{id}")
    public ResponseEntity<?> editPaiement(@PathVariable Long id, @RequestBody PaiementDTO paiementDTO) {
        return paiementService.editPaiement(id, paiementDTO);
    }

    @DeleteMapping("supprimer/{id}")
    public ResponseEntity<?> deletePaiement(@PathVariable Long id) {
        return paiementService.deletePaiement(id);
    }

    @GetMapping("rechercher")
    public ResponseEntity<?> searchPaiement(@RequestParam String query) {
        return paiementService.searchPaiement(query);
    }

    @GetMapping("/recu/{id}")
    public ResponseEntity<Resource> downloadRecu(@PathVariable Long id) throws IOException {
        String filepath = "C:/reçus/recu_" + id + ".pdf";
        File file = new File(filepath);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + file.getName())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}

