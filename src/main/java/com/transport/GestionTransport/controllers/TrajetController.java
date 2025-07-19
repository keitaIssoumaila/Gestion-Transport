package com.transport.GestionTransport.controllers;


import com.transport.GestionTransport.ditos.TrajetDTO;
import com.transport.GestionTransport.entities.Trajet;
import com.transport.GestionTransport.services.TrajetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trajets")
@CrossOrigin
public class TrajetController {

    private final TrajetService trajetService;

    public TrajetController(TrajetService trajetService) {
        this.trajetService = trajetService;
    }

    @GetMapping
    public List<Trajet> getAllTrajets() {
        return trajetService.getAllTrajets();
    }

    @GetMapping("/rechercher/{id}")
    public ResponseEntity<?> getTrajetById(@PathVariable Long id) {
        return trajetService.getTrajetById(id);
    }

    @PostMapping
    public ResponseEntity<?> createTrajet(@RequestBody TrajetDTO trajetDTO) {
        return trajetService.createTrajet(trajetDTO);
    }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<?> editTrajet(@PathVariable Long id, @RequestBody TrajetDTO trajetDTO) {
        return trajetService.editTrajet(id, trajetDTO);
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<?> deleteTrajet(@PathVariable Long id) {
        return trajetService.deleteTrajet(id);
    }

    @GetMapping("/rechercher")
    public ResponseEntity<?> searchTrajet(@RequestParam String query) {
        return trajetService.searchTrajet(query);
    }
}

