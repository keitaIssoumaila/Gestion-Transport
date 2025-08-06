package com.transport.GestionTransport.controllers;

import com.transport.GestionTransport.dtos.BusDTO;
import com.transport.GestionTransport.entities.Bus;
import com.transport.GestionTransport.services.BusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "bus")
@CrossOrigin("*")
public class BusController {

   public final BusService busService;

    public BusController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping
    public List<Bus> getBus(){ return busService.getBus();}

    @GetMapping(path = "recherche/{id}")
    public ResponseEntity<?> getBusById(@PathVariable Long id) {
        return busService.getBusById(id);
    }
    @PostMapping(path = "creer")
    public ResponseEntity<?> createBus(@RequestBody BusDTO busDTO) {
        return busService.createBus(busDTO);
    }

    @PutMapping(path = "modifier/{id}")
    public ResponseEntity<?> editBus(@RequestBody BusDTO busDTO, @PathVariable Long id) {
        return busService.editBus(busDTO, id);
    }

    @GetMapping(path = "rechercher")
    public ResponseEntity<?> searchBus(@RequestParam String query) {
        return busService.searchBus(query);
    }

    @DeleteMapping(path = "supprimer/{id}")
    public ResponseEntity<?> deleteBus(@PathVariable Long id) {
        return busService.deleteBus(id);
    }

}