package com.transport.GestionTransport.services;

import com.transport.GestionTransport.dtos.ClientDTO;
import com.transport.GestionTransport.entities.Client;
import com.transport.GestionTransport.entities.Souscription;
import com.transport.GestionTransport.repositories.ClientRepository;
import com.transport.GestionTransport.repositories.SouscriptionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final SouscriptionRepository souscriptionRepository;

    public ClientService(ClientRepository clientRepository, SouscriptionRepository souscriptionRepository) {
        this.clientRepository = clientRepository;
        this.souscriptionRepository = souscriptionRepository;
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public ResponseEntity<?> createClient(ClientDTO clientDTO) {
        try {
            Client client = new Client();
            client.setNom(clientDTO.getNom());
            client.setPrenom(clientDTO.getPrenom());
            client.setDateNaissance(clientDTO.getDateNaissance());
            client.setEmail(clientDTO.getEmail());
            client.setTelephone(clientDTO.getTelephone());

            clientRepository.save(client);
            return ResponseEntity.ok("Client créé avec succès");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la création du client : " + e.getMessage());
        }
    }

    public ResponseEntity<?> editClient(ClientDTO clientDTO, Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        client.setNom(clientDTO.getNom());
        client.setPrenom(clientDTO.getPrenom());
        client.setDateNaissance(clientDTO.getDateNaissance());
        client.setEmail(clientDTO.getEmail());
        client.setTelephone(clientDTO.getTelephone());

        clientRepository.save(client);
        return ResponseEntity.ok("Client modifié avec succès");
    }

    public ResponseEntity<?> deleteClient(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        clientRepository.delete(client);
        return ResponseEntity.ok("Client supprimé avec succès");
    }

    public ResponseEntity<?> searchClient(String query) {
        List<Client> clients = clientRepository
                .findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrTelephoneContainingIgnoreCase(query, query, query);

        if (clients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clients);
    }

    public ResponseEntity<?> getClientById(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        // Chargement explicite des souscriptions (si LAZY)
        List<Souscription> souscriptions = souscriptionRepository.findByClientId(id);
        client.setSouscriptions(souscriptions);

        return ResponseEntity.ok(client);
    }
}
