package com.transport.GestionTransport.controllers;


import com.transport.GestionTransport.dtos.TrajetDTO;
import com.transport.GestionTransport.entities.Trajet;
import com.transport.GestionTransport.services.TrajetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @PostMapping("creer")
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
//    // Endpoint pour récupérer tous les trajets à une date donnée
//    @GetMapping("/par-date")
//    public ResponseEntity<?> getTrajetsByDate(@RequestParam("date") String dateString) {
//        try {
//            LocalDate date = LocalDate.parse(dateString); // Format attendu : YYYY-MM-DD
//            List<Trajet> trajets = trajetService.getTrajetsByDate(date);
//            if (trajets.isEmpty()) {
//                return ResponseEntity.noContent().build();
//            }
//            return ResponseEntity.ok(trajets);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Date invalide. Utilise le format AAAA-MM-JJ.");
//        }
//    }
//
//    // Endpoint pour récupérer les trajets à venir
//    @GetMapping("/futurs")
//    public ResponseEntity<?> getUpcomingTrajets() {
//        List<Trajet> trajets = trajetService.getUpcomingTrajets();
//        if (trajets.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(trajets);
//    }
}

