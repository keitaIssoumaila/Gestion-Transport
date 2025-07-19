package com.transport.GestionTransport.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proprietaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cin;
    private String nom;
    private String prenom;
    private String dateNaissance;

    @OneToMany(mappedBy = "proprietaire")
    private List<Bus> busList;

    @OneToMany(mappedBy = "proprietaire")
    private List<Contrat> contrats;
}
