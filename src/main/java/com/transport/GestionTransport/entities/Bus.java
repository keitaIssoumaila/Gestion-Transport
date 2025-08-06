package com.transport.GestionTransport.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String matricule;
    private LocalDate date;
    private String typeBus;
    private int nombrePlace;
    private String status;

    @ManyToOne
    @JoinColumn(name = "proprietaire_id")
    @JsonBackReference
    private Proprietaire proprietaire;

    // 🔁 Boucle possible avec Conducteur → Bus
    @OneToOne(mappedBy = "bus")
    @JsonManagedReference
    private Conducteur conducteur;

    // 🔁 Boucle possible avec HoraireTrajet → Bus
    @OneToMany(mappedBy = "bus")
    @JsonManagedReference
    private List<HoraireTrajet> horaireTrajets;

}
