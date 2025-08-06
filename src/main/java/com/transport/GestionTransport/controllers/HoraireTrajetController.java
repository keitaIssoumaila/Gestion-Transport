package com.transport.GestionTransport.controllers;
import com.transport.GestionTransport.dtos.HoraireTrajetDTO;
import com.transport.GestionTransport.entities.HoraireTrajet;
import com.transport.GestionTransport.services.HoraireTrajetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/horaire-trajet")
@CrossOrigin("*")
public class HoraireTrajetController {

    private final HoraireTrajetService horaireTrajetService;

    public HoraireTrajetController( HoraireTrajetService horaireTrajetService) {
        this.horaireTrajetService = horaireTrajetService;
    }

    @GetMapping
    public List<HoraireTrajet> getHoraireTrajet(){ return horaireTrajetService.getAllHoraireTrajet();}

    @PostMapping("creer")
    public ResponseEntity<?> createHoraireTrajet(@RequestBody HoraireTrajetDTO dto) {
        return horaireTrajetService.createHoraireTrajet(dto);
    }

    @PutMapping("modifier/{id}")
    public ResponseEntity<?> editHoraireTrajet(@PathVariable Long id, @RequestBody HoraireTrajetDTO dto) {
        return horaireTrajetService.editHoraireTrajet(id, dto);
    }

    @GetMapping("recherche/{id}")
    public ResponseEntity<?> getHoraireTrajetById(@PathVariable Long id) {
        return horaireTrajetService.getHoraireTrajetById(id);
    }

    @DeleteMapping("supprimer/{id}")
    public ResponseEntity<?> deleteHoraireTrajet(@PathVariable Long id) {
        return horaireTrajetService.deleteHoraireTrajet(id);
    }

    @GetMapping("rechercher")
    public ResponseEntity<?> searchHoraireTrajet(@RequestParam String query) {
        return horaireTrajetService.searchHoraireTrajet(query);
    }

    @GetMapping("/horaire-trajet/bus/{busId}")
    public ResponseEntity<?> getByBus(@PathVariable Long busId) {
        return horaireTrajetService.getHoraireTrajetsByBus(busId);
    }
}
