package com.transport.GestionTransport.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;
    private String name;
    private Date date;
    private String email;
    private int phone;

    @OneToMany(mappedBy = "client")
    private List<CompteAbonnement> abonnements;

    @OneToMany(mappedBy = "client")
    private List<Paiement> paiements;
}
