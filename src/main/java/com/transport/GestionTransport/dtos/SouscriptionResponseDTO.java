package com.transport.GestionTransport.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SouscriptionResponseDTO {
    private Long id;
    private String reference;
    private String statut;
    private LocalDate dateSouscription;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    // Infos Client
    private String clientNom;
    private String clientPrenom;
    private String clientTelephone;

    // Infos Abonnement
    private String nomAbonnement;
    private Double montant;
    private Integer dureeMois;
}
