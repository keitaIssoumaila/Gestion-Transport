package com.transport.GestionTransport.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trajet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private double montant;
    private String pointDepart;
    private String pointArriver;

    @OneToMany(mappedBy = "trajet")
    @JsonBackReference
    private List<Client> clients;

    @OneToMany(mappedBy = "trajet", cascade = CascadeType.ALL)
    private List<HoraireTrajet> horaires;

    public void setHoraireTrajet(HoraireTrajet horaire) {
    }
}
