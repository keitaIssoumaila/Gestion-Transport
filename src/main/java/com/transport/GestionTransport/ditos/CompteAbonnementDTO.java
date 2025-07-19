package com.transport.GestionTransport.ditos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteAbonnementDTO {
    private Long id;
    private String reference;
    private Date date;
    private String statusAbonnement;
    private Date dateDebutAbonnement;
    private Date dateFinAbonnement;
    private Long clientId;
}

