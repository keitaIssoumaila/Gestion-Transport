package com.transport.GestionTransport.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trajet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference;
    private String nom;
    private LocalDate date;
    private String pointDepart;
    private String pointArriver;

    @ManyToOne
    @JoinColumn(name = "horaire_trajet_id")
    private HoraireTrajet horaireTrajet;
}
