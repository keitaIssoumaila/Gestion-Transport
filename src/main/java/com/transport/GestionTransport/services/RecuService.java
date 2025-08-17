package com.transport.GestionTransport.services;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.transport.GestionTransport.entities.Client;
import com.transport.GestionTransport.entities.Paiement;
import com.transport.GestionTransport.entities.Souscription;
import org.springframework.stereotype.Service;

import javax.print.*;
import java.io.File;
import java.io.FileInputStream;
import java.time.format.DateTimeFormatter;

@Service
public class RecuService {

    private static final DateTimeFormatter DT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter D = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /** Ticket thermique 80mm pour un paiement "course" (trajet ponctuel) */
    public String genererTicketThermique(Client client, Paiement paiement) {
        String fileName = "ticket_paiement_" + client.getId() + "_" + System.currentTimeMillis() + ".pdf";
        String folderPath = System.getProperty("user.dir") + File.separator + "recus";
        String filePath = folderPath + File.separator + fileName;

        try {
            File folder = new File(folderPath);
            if (!folder.exists()) folder.mkdirs();

            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);

            // Largeur 80mm (1mm ≈ 2.83465pt)
            float ticketWidth = 80 * 2.83465f;
            PageSize pageSize = new PageSize(ticketWidth, PageSize.A4.getHeight());

            Document document = new Document(pdf, pageSize);
            document.setMargins(5, 5, 5, 5);

            // Titre
            document.add(new Paragraph("REÇU DE PAIEMENT")
                    .setBold().setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.BLACK));

            document.add(new Paragraph("--------------------------------"));

            // Infos client
            document.add(new Paragraph("Client : " + safe(client.getNom()) + " " + safe(client.getPrenom())).setFontSize(8));
            document.add(new Paragraph("Tel : " + safe(client.getTelephone())).setFontSize(8));
            if (client.getEmail() != null) {
                document.add(new Paragraph("Email : " + client.getEmail()).setFontSize(8));
            }

            document.add(new Paragraph("--------------------------------"));

            // Trajet & horaire (si présents)
            if (client.getTrajet() != null) {
                String depart = safe(client.getTrajet().getPointDepart());
                String arrivee = safe(client.getTrajet().getPointArriver()); // ✅ correction
                document.add(new Paragraph("Trajet : " + depart + " → " + arrivee).setFontSize(8));
            } else {
                document.add(new Paragraph("Trajet : -").setFontSize(8));
            }
            if (client.getHoraireTrajet() != null && client.getHoraireTrajet().getHeureDepart() != null) {
                document.add(new Paragraph("Départ : " + client.getHoraireTrajet().getHeureDepart().toString()).setFontSize(8));
            }

            document.add(new Paragraph("--------------------------------"));

            // Paiement
            document.add(new Paragraph("Montant : " + String.format("%.2f FCFA", paiement.getMontant())).setFontSize(8));
            document.add(new Paragraph("Mode   : " + (paiement.getModePaiement() != null ? paiement.getModePaiement() : "Non spécifié")).setFontSize(8));
            if (paiement.getDatePaiement() != null) {
                document.add(new Paragraph("Date   : " + paiement.getDatePaiement().format(DT)).setFontSize(8));
            }

            document.add(new Paragraph("--------------------------------"));
            document.add(new Paragraph("Merci pour votre confiance")
                    .setFontSize(8)
                    .setTextAlignment(TextAlignment.CENTER));

            document.close();

            // Impression auto
            imprimerPDF(filePath);
            return filePath;

        } catch (Exception e) {
            throw new RuntimeException("Erreur génération ticket (paiement) : " + e.getMessage());
        }
    }

    /** Ticket thermique 80mm pour une SOUSCRIPTION (abonnement) */
    public String genererTicketSouscription(Client client, Souscription souscription, Paiement paiement) {
        String fileName = "ticket_souscription_" + client.getId() + "_" + System.currentTimeMillis() + ".pdf";
        String folderPath = System.getProperty("user.dir") + File.separator + "recus";
        String filePath = folderPath + File.separator + fileName;

        try {
            File folder = new File(folderPath);
            if (!folder.exists()) folder.mkdirs();

            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);

            float ticketWidth = 80 * 2.83465f;
            PageSize pageSize = new PageSize(ticketWidth, PageSize.A4.getHeight());

            Document document = new Document(pdf, pageSize);
            document.setMargins(5, 5, 5, 5);

            // Titre
            document.add(new Paragraph("REÇU SOUSCRIPTION")
                    .setBold().setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.BLACK));

            document.add(new Paragraph("--------------------------------"));

            // Infos client
            document.add(new Paragraph("Client : " + safe(client.getNom()) + " " + safe(client.getPrenom())).setFontSize(8));
            document.add(new Paragraph("Tel : " + safe(client.getTelephone())).setFontSize(8));

            document.add(new Paragraph("--------------------------------"));

            // Infos souscription
            if (souscription != null) {
                document.add(new Paragraph("Réf : " + safe(souscription.getReference())).setFontSize(8));
                if (souscription.getAbonnement() != null) {
                    document.add(new Paragraph("Offre : " + safe(souscription.getAbonnement().getNom())).setFontSize(8));
                    // durée & montant affichables si existants
                    if (souscription.getAbonnement().getDureeMois() != null) {
                        document.add(new Paragraph("Durée : " + souscription.getAbonnement().getDureeMois() + " mois").setFontSize(8));
                    }
                }
                if (souscription.getDateDebut() != null && souscription.getDateFin() != null) {
                    document.add(new Paragraph("Période : " + souscription.getDateDebut().format(D) +
                            " → " + souscription.getDateFin().format(D)).setFontSize(8));
                }
                if (souscription.getStatut() != null) {
                    document.add(new Paragraph("Statut : " + souscription.getStatut()).setFontSize(8));
                }
            }

            document.add(new Paragraph("--------------------------------"));

            // Paiement lié à la souscription
            if (paiement != null) {
                document.add(new Paragraph("Montant : " + String.format("%.2f FCFA", paiement.getMontant())).setFontSize(8));
                document.add(new Paragraph("Mode   : " + (paiement.getModePaiement() != null ? paiement.getModePaiement() : "Non spécifié")).setFontSize(8));
                if (paiement.getDatePaiement() != null) {
                    document.add(new Paragraph("Date   : " + paiement.getDatePaiement().format(DT)).setFontSize(8));
                }
            }

            document.add(new Paragraph("--------------------------------"));
            document.add(new Paragraph("Merci pour votre confiance")
                    .setFontSize(8)
                    .setTextAlignment(TextAlignment.CENTER));

            document.close();

            // Impression auto
            imprimerPDF(filePath);
            return filePath;

        } catch (Exception e) {
            throw new RuntimeException("Erreur génération ticket (souscription) : " + e.getMessage());
        }
    }

    private void imprimerPDF(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            PrintService printService = PrintServiceLookup.lookupDefaultPrintService();

            if (printService != null) {
                DocPrintJob job = printService.createPrintJob();
                Doc doc = new SimpleDoc(fis, flavor, null);
                job.print(doc, null);
                System.out.println("🖨 Impression envoyée à : " + printService.getName());
            } else {
                System.err.println("⚠ Aucune imprimante trouvée !");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur impression ticket : " + e.getMessage());
        }
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }
}
