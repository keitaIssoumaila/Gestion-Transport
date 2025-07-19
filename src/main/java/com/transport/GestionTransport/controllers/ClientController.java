package com.transport.GestionTransport.controllers;

import com.transport.GestionTransport.ditos.ClientDTO;
import com.transport.GestionTransport.entities.Client;
import com.transport.GestionTransport.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "client")
@CrossOrigin("*")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getClients() {
        return clientService.getClients();
    }

    @GetMapping(path = "recherche/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    @PostMapping(path = "creer")
    public ResponseEntity<?> createClient(@RequestBody ClientDTO clientDTO) {
        return clientService.createClient(clientDTO);
    }

    @PutMapping(path = "modifier/{id}")
    public ResponseEntity<?> editClient(@RequestBody ClientDTO clientDTO, @PathVariable Long id) {
        return clientService.editClient(clientDTO, id);
    }

    @GetMapping(path = "rechercher")
    public ResponseEntity<?> searchClient(@RequestParam String query) {
        return clientService.searchClient(query);
    }

    @DeleteMapping(path = "supprimer/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        return clientService.deleteClient(id);
    }
}

