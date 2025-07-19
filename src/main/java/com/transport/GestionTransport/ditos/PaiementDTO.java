package com.transport.GestionTransport.ditos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaiementDTO {
    private Long id;
    private String reference;
    private LocalDate date;
    private Double montant;
    private String modePaiement;
    private Long clientId;
}

