package com.transport.GestionTransport.services;

import com.transport.GestionTransport.ditos.ClientDTO;
import com.transport.GestionTransport.entities.Client;
import com.transport.GestionTransport.repositories.ClientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public ResponseEntity<?> createClient(ClientDTO clientDTO) {
        try {
            Client client = new Client();
            client.setReference(clientDTO.getReference());
            client.setName(clientDTO.getName());
            client.setDate(clientDTO.getDate());
            client.setEmail(clientDTO.getEmail());
            client.setPhone(clientDTO.getPhone());

            clientRepository.save(client);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.status(500).body(" Erreur lors de la création du client : " + e.getMessage());
        }
    }

    public ResponseEntity<?> editClient(ClientDTO clientDTO, Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            return ResponseEntity.notFound().build();
        } else {
            client.setReference(clientDTO.getReference());
            client.setName(clientDTO.getName());
            client.setDate(clientDTO.getDate());
            client.setEmail(clientDTO.getEmail());
            client.setPhone(clientDTO.getPhone());

            clientRepository.save(client);
            return ResponseEntity.ok().build();
        }
    }

    public ResponseEntity<?> deleteClient(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            return ResponseEntity.notFound().build();
        } else {
            clientRepository.delete(client);
            return ResponseEntity.ok().build();
        }
    }

    public ResponseEntity<?> searchClient(String query) {
        List<Client> clients = clientRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query);
        if (clients.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(clients);
        }
    }

    public ResponseEntity<?> getClientById(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(client);
        }
    }
}

