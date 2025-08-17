package com.transport.GestionTransport.services;

import com.transport.GestionTransport.dtos.ClientDTO;
import com.transport.GestionTransport.entities.*;
import com.transport.GestionTransport.repositories.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final SouscriptionRepository souscriptionRepository;
    private final TrajetRepository trajetRepository;
    private final HoraireTrajetRepository horaireTrajetRepository;
    private final PaiementRepository paiementRepository;
    private final RecuService recuService;

    public ClientService(ClientRepository clientRepository,
                         SouscriptionRepository souscriptionRepository,
                         TrajetRepository trajetRepository,
                         HoraireTrajetRepository horaireTrajetRepository,
                         PaiementRepository paiementRepository, RecuService recuService) {
        this.clientRepository = clientRepository;
        this.souscriptionRepository = souscriptionRepository;
        this.trajetRepository = trajetRepository;
        this.horaireTrajetRepository = horaireTrajetRepository;
        this.paiementRepository = paiementRepository;
        this.recuService = recuService;
    }

    // 🔹 Récupérer tous les clients
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    // 🔹 Création d'un client avec sélection Trajet & Horaire et enregistrement Paiement
    // 🔹 Création d'un client avec vérification des places
    public ResponseEntity<?> createClient(ClientDTO clientDTO) {
        try {
            // Vérifier l'existence du trajet
            Trajet trajet = trajetRepository.findById(clientDTO.getTrajetId()).orElse(null);
            if (trajet == null) {
                return ResponseEntity.badRequest().body("Trajet introuvable");
            }

            // Vérifier l'existence de l'horaire
            HoraireTrajet horaire = horaireTrajetRepository.findById(clientDTO.getHoraireTrajetId()).orElse(null);
            if (horaire == null) {
                return ResponseEntity.badRequest().body("Horaire de trajet introuvable");
            }

            // Vérification des places disponibles
            if (horaire.getPlacesRestantes() <= 0) {
                // 🔹 Proposer un autre horaire du même trajet qui a encore des places
                List<HoraireTrajet> alternatives = horaireTrajetRepository
                        .findByTrajetIdAndPlacesRestantesGreaterThan(trajet.getId(), 0);

                if (!alternatives.isEmpty()) {
                    return ResponseEntity.badRequest().body("Ce bus est complet. Horaires alternatifs : " + alternatives);
                }

                return ResponseEntity.badRequest().body("Ce bus est complet et aucun horaire alternatif disponible");
            }

            // Vérifier si le client existe déjà par téléphone
            Client client = clientRepository.findByTelephone(clientDTO.getTelephone())
                    .orElseGet(() -> {
                        Client newClient = new Client();
                        newClient.setNom(clientDTO.getNom());
                        newClient.setPrenom(clientDTO.getPrenom());
                        newClient.setDateNaissance(clientDTO.getDateNaissance());
                        newClient.setEmail(clientDTO.getEmail());
                        newClient.setTelephone(clientDTO.getTelephone());
                        return clientRepository.save(newClient);
                    });

            // Associer trajet et horaire
            client.setTrajet(trajet);
            client.setHoraireTrajet(horaire);
            clientRepository.save(client);

            // Création du paiement
            Paiement paiement = new Paiement();
            paiement.setClient(client);
            paiement.setMontant(trajet.getMontant());
            paiement.setDatePaiement(LocalDateTime.now());
            paiementRepository.save(paiement);

            // 📌 Génération automatique du PDF après paiement
            recuService.genererTicketThermique(client, paiement);

            // Mise à jour des places restantes
            horaire.setPlacesRestantes(horaire.getPlacesRestantes() - 1);
            horaireTrajetRepository.save(horaire);

            return ResponseEntity.ok("Client créé avec succès et paiement enregistré");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la création du client : " + e.getMessage());
        }
    }

    // 🔹 Modification d'un client
    public ResponseEntity<?> editClient(ClientDTO clientDTO, Long id) {
        // 1. Récupération du client
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            // 2. Mise à jour des infos de base
            if (clientDTO.getNom() != null) client.setNom(clientDTO.getNom());
            if (clientDTO.getPrenom() != null) client.setPrenom(clientDTO.getPrenom());
            if (clientDTO.getDateNaissance() != null) client.setDateNaissance(clientDTO.getDateNaissance());
            if (clientDTO.getEmail() != null) client.setEmail(clientDTO.getEmail());
            if (clientDTO.getTelephone() != null) client.setTelephone(clientDTO.getTelephone());

            boolean trajetChanged = false;
            boolean horaireChanged = false;

            // 3. Mise à jour du trajet
            if (clientDTO.getTrajetId() != null) {
                Trajet trajet = trajetRepository.findById(clientDTO.getTrajetId()).orElse(null);
                if (trajet == null) {
                    return ResponseEntity.badRequest().body("Trajet introuvable");
                }
                if (!trajet.equals(client.getTrajet())) {
                    client.setTrajet(trajet);
                    trajetChanged = true;
                }
            }

            // 4. Mise à jour de l’horaireTrajet
            if (clientDTO.getHoraireTrajetId() != null) {
                HoraireTrajet horaireTrajet = horaireTrajetRepository.findById(clientDTO.getHoraireTrajetId()).orElse(null);
                if (horaireTrajet == null) {
                    return ResponseEntity.badRequest().body("Horaire de trajet introuvable");
                }
                if (!horaireTrajet.equals(client.getHoraireTrajet())) {
                    client.setHoraireTrajet(horaireTrajet);
                    horaireChanged = true;
                }
            }

            // Sauvegarde des infos du client
            clientRepository.save(client);

            // 5. Mise à jour d’un paiement précis si paiementId fourni
            if (clientDTO.getPaiementId() != null) {
                Paiement paiement = paiementRepository.findById(clientDTO.getPaiementId())
                        .orElseThrow(() -> new RuntimeException("Paiement non trouvé"));

                // Sécurité : vérifier que le paiement appartient bien au client
                if (!paiement.getClient().getId().equals(id)) {
                    return ResponseEntity.badRequest().body("Ce paiement n'appartient pas au client");
                }

                // Mise à jour du montant si le trajet change
                if (trajetChanged) {
                    paiement.setMontant(client.getTrajet().getMontant());
                }

                // Mise à jour du mode de paiement
                if (clientDTO.getModePaiement() != null) {
                    paiement.setModePaiement(clientDTO.getModePaiement());
                }

                paiementRepository.save(paiement);
            }

            return ResponseEntity.ok("Client et paiement modifiés avec succès");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la modification du client : " + e.getMessage());
        }
    }

    // 🔹 Suppression d'un client
    public ResponseEntity<?> deleteClient(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        clientRepository.delete(client);
        return ResponseEntity.ok("Client supprimé avec succès");
    }

    // 🔹 Recherche de clients
    public ResponseEntity<?> searchClient(String query) {
        List<Client> clients = clientRepository
                .findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrTelephoneContainingIgnoreCase(query, query, query);

        if (clients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clients);
    }

    // 🔹 Récupération d'un client par ID
    public ResponseEntity<?> getClientById(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        List<Souscription> souscriptions = souscriptionRepository.findByClientId(id);
        client.setSouscriptions(souscriptions);

        return ResponseEntity.ok(client);
    }
}
