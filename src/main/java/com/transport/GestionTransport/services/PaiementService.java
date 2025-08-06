package com.transport.GestionTransport.services;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import com.transport.GestionTransport.dtos.PaiementDTO;
import com.transport.GestionTransport.entities.Client;
import com.transport.GestionTransport.entities.Paiement;
import com.transport.GestionTransport.entities.Souscription;
import com.transport.GestionTransport.repositories.ClientRepository;
import com.transport.GestionTransport.repositories.PaiementRepository;
import com.transport.GestionTransport.repositories.SouscriptionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PaiementService {

    private final PaiementRepository paiementRepository;
    private final ClientRepository clientRepository;
    private final SouscriptionRepository souscriptionRepository;

    public PaiementService(PaiementRepository paiementRepository,
                           ClientRepository clientRepository,
                           SouscriptionRepository souscriptionRepository) {
        this.paiementRepository = paiementRepository;
        this.clientRepository = clientRepository;
        this.souscriptionRepository = souscriptionRepository;
    }

    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

    public ResponseEntity<?> getPaiementById(Long id) {
        Paiement paiement = paiementRepository.findPaiementById(id);
        if (paiement == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paiement);
    }

    public ResponseEntity<?> createPaiement(PaiementDTO paiementDTO) {
        Client client = clientRepository.findById(paiementDTO.getClientId()).orElse(null);
        if (client == null) {
            return ResponseEntity.badRequest().body("Client non trouvé");
        }

        Souscription souscription = souscriptionRepository.findById(paiementDTO.getSouscriptionId()).orElse(null);
        if (souscription == null) {
            return ResponseEntity.badRequest().body("Souscription non trouvée");
        }

        if ("Orange Money".equalsIgnoreCase(paiementDTO.getModePaiement())) {
            System.out.println("=== Simulation du paiement via Orange Money ===");
            System.out.println("Client: " + client.getNom());
            System.out.println("Telephone: " + client.getTelephone());
            System.out.println("Montant: " + paiementDTO.getMontant());
            System.out.println("Paiement simulé avec succès !");
        }

        Paiement paiement = new Paiement();
        paiement.setDate(paiementDTO.getDate());
        paiement.setMontant(paiementDTO.getMontant());
        paiement.setModePaiement(paiementDTO.getModePaiement());
        paiement.setClient(client);
        paiement.setSouscription(souscription); // 🔹 association

        paiementRepository.save(paiement);

        generateRecuPdf(paiement);
        return ResponseEntity.ok("Paiement enregistré avec succès.");
    }

    public ResponseEntity<?> editPaiement(Long id, PaiementDTO paiementDTO) {
        Paiement paiement = paiementRepository.findPaiementById(id);
        if (paiement == null) {
            return ResponseEntity.notFound().build();
        }

        Souscription souscription = souscriptionRepository.findById(paiementDTO.getSouscriptionId()).orElse(null);
        if (souscription == null) {
            return ResponseEntity.badRequest().body("Souscription non trouvée");
        }

        paiement.setDate(paiementDTO.getDate());
        paiement.setMontant(paiementDTO.getMontant());
        paiement.setModePaiement(paiementDTO.getModePaiement());
        paiement.setSouscription(souscription); // 🔄 mise à jour

        paiementRepository.save(paiement);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deletePaiement(Long id) {
        Paiement paiement = paiementRepository.findPaiementById(id);
        if (paiement == null) {
            return ResponseEntity.notFound().build();
        }
        paiementRepository.delete(paiement);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> searchPaiement(String query) {
        List<Paiement> paiements = paiementRepository
                .findAllByModePaiementContainingIgnoreCase(query);
        if (paiements.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(paiements);
    }

    private void generateRecuPdf(Paiement paiement) {
        try {
            String filename = "recu_" + paiement.getId() + ".pdf";
            File dossier = new File("C:/reçus/" + paiement.getClient().getNom());
            if (!dossier.exists()) {
                dossier.mkdirs();
            }

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("C:/reçus/" + filename));
            document.open();

            // Polices
            Font fontTitreEntreprise = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, Font.UNDERLINE, new Color(0, 102, 204));
            Font fontTitre = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);

            // Titre
            Paragraph entreprise = new Paragraph("MANDE TRANSPORT", fontTitreEntreprise);
            entreprise.setAlignment(Element.ALIGN_CENTER);
            document.add(entreprise);
            document.add(new Paragraph(" "));

            Paragraph titre = new Paragraph("Reçu de Paiement", fontTitre);
            titre.setAlignment(Element.ALIGN_CENTER);
            document.add(titre);
            document.add(new Paragraph(" "));

            // Infos
            document.add(new Paragraph("Client : " + paiement.getClient().getNom(), fontNormal));
            document.add(new Paragraph("Montant : " + paiement.getMontant() + " FCFA", fontNormal));
            document.add(new Paragraph("Mode de paiement : " + paiement.getModePaiement(), fontNormal));

            if (paiement.getSouscription() != null) {
                document.add(new Paragraph("Souscription : " + paiement.getSouscription().getReference(), fontNormal));
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            document.add(new Paragraph("Date : " + paiement.getDate().format(formatter), fontNormal));

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Merci pour votre paiement.", fontNormal));

            document.close();
            System.out.println("PDF généré : " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
