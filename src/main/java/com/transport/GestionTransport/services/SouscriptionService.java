package com.transport.GestionTransport.services;

import com.transport.GestionTransport.dtos.SouscriptionDTO;
import com.transport.GestionTransport.dtos.SouscriptionResponseDTO;
import com.transport.GestionTransport.entities.*;
import com.transport.GestionTransport.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SouscriptionService {
    @Autowired
    private RecuService recuService;


    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AbonnementRepository abonnementRepository;

    @Autowired
    private SouscriptionRepository souscriptionRepository;

    @Autowired
    private PaiementRepository paiementRepository;

    // 🔹 Créer une nouvelle souscription
    public ResponseEntity<?> souscrire(SouscriptionDTO dto) {
        Client client = clientRepository.findById(dto.getClientId()).orElse(null);
        if (client == null) return ResponseEntity.badRequest().body("Client introuvable");

        Abonnement offre = abonnementRepository.findById(dto.getAbonnementId()).orElse(null);
        if (offre == null) return ResponseEntity.badRequest().body("Offre introuvable");

        LocalDate today = LocalDate.now();
        LocalDate debut = today;
        LocalDate fin = debut.plusMonths(offre.getDureeMois());

        Souscription souscription = new Souscription();
        souscription.setClient(client);
        souscription.setAbonnement(offre);
        souscription.setDate(today);
        souscription.setDateDebut(debut);
        souscription.setDateFin(fin);
        souscription.setReference("SOUSC-" + System.currentTimeMillis());
        souscription.setStatut("Actif");

        souscriptionRepository.save(souscription);

        Paiement paiement = new Paiement();
        paiement.setClient(client);
        paiement.setSouscription(souscription);
        paiement.setMontant(offre.getMontant());
        paiement.setModePaiement(dto.getModePaiement());
        paiement.setDatePaiement(LocalDateTime.now());

        paiementRepository.save(paiement);
        // Générer & imprimer automatiquement le ticket de souscription
        recuService.genererTicketSouscription(client, souscription, paiement);

        // ✅ DTO enrichi
        SouscriptionResponseDTO response = new SouscriptionResponseDTO();
        response.setId(souscription.getId());
        response.setReference(souscription.getReference());
        response.setStatut(souscription.getStatut());
        response.setDateSouscription(souscription.getDate());
        response.setDateDebut(debut);
        response.setDateFin(fin);

        // Infos Client
        response.setClientNom(client.getNom());
        response.setClientPrenom(client.getPrenom());
        response.setClientTelephone(client.getTelephone());

        // Infos Abonnement
        response.setNomAbonnement(offre.getNom());
        response.setMontant(offre.getMontant());
        response.setDureeMois(offre.getDureeMois());

        return ResponseEntity.ok(response);
    }

    public List<SouscriptionResponseDTO> getAllSouscriptions() {
        List<Souscription> souscriptions = souscriptionRepository.findAll();

        return souscriptions.stream().map(s -> {
            SouscriptionResponseDTO dto = new SouscriptionResponseDTO();
            dto.setId(s.getId());
            dto.setReference(s.getReference());
            dto.setStatut(s.getStatut());
            dto.setDateSouscription(s.getDate());
            dto.setDateDebut(s.getDateDebut());
            dto.setDateFin(s.getDateFin());

            // Infos client
            Client c = s.getClient();
            dto.setClientNom(c.getNom());
            dto.setClientPrenom(c.getPrenom());
            dto.setClientTelephone(c.getTelephone());

            // Infos abonnement
            Abonnement a = s.getAbonnement();
            dto.setNomAbonnement(a.getNom());
            dto.setMontant(a.getMontant());
            dto.setDureeMois(a.getDureeMois());

            return dto;
        }).toList();
    }

    // 🔹 Souscriptions par client
    public ResponseEntity<?> getSouscriptionsParClient(Long clientId) {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client == null) return ResponseEntity.badRequest().body("Client introuvable.");
        List<Souscription> souscriptions = souscriptionRepository.findByClientId(clientId);
        return ResponseEntity.ok(souscriptions);
    }

    // 🔹 Vérifier si client a une souscription active
    public ResponseEntity<?> verifierSouscriptionActive(Long clientId) {
        boolean existe = souscriptionRepository.existsByClientIdAndDateFinAfter(clientId, LocalDate.now());
        return ResponseEntity.ok(existe ? "Souscription active." : "Pas de souscription active.");
    }

    // 🔹 Supprimer une souscription
    public ResponseEntity<?> supprimerSouscription(Long id) {
        if (!souscriptionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        souscriptionRepository.deleteById(id);
        return ResponseEntity.ok("Souscription supprimée avec succès.");
    }

    // 🔹 Changer le statut
    public ResponseEntity<?> changerStatutSouscription(Long id, String statut) {
        Souscription souscription = souscriptionRepository.findById(id).orElse(null);
        if (souscription == null) return ResponseEntity.notFound().build();

        souscription.setStatut(statut);
        souscriptionRepository.save(souscription);

        return ResponseEntity.ok("Statut mis à jour.");
    }

    // 🔹 Mettre à jour les statuts automatiquement
    public ResponseEntity<?> mettreAJourTousLesStatuts() {
        List<Souscription> souscriptions = souscriptionRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Souscription s : souscriptions) {
            boolean estValide =
                    (s.getDateDebut().isBefore(today) || s.getDateDebut().isEqual(today)) &&
                            (s.getDateFin().isAfter(today) || s.getDateFin().isEqual(today));

            if (estValide && !"Actif".equalsIgnoreCase(s.getStatut())) {
                s.setStatut("Actif");
                souscriptionRepository.save(s);
            } else if (!estValide && !"Expiré".equalsIgnoreCase(s.getStatut())) {
                s.setStatut("Expiré");
                souscriptionRepository.save(s);
            }
        }

        return ResponseEntity.ok("Statuts des souscriptions mis à jour.");
    }

    // 🔹 Compter les souscriptions actives
    public long compterSouscriptionsActives() {
        return souscriptionRepository.findAll().stream()
                .filter(s -> "Actif".equalsIgnoreCase(s.getStatut()))
                .count();
    }
}
