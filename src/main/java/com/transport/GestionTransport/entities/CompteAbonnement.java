package com.transport.GestionTransport.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteAbonnement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference;
    private Date date;
    private String statusAbonnement;
    private Date dateDebutAbonnement;
    private Date dateFinAbonnement;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
