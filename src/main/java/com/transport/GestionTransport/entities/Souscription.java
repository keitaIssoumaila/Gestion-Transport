package com.transport.GestionTransport.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Souscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference;
    private LocalDate date; // date de souscription
    private String statut;  // Actif, Expiré, Suspendu, etc.

    private LocalDate dateDebut;
    private LocalDate dateFin;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private Client client;


    @ManyToOne
    @JoinColumn(name = "abonnement_id")
    private Abonnement abonnement; // offre choisie
}
