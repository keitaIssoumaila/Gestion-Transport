package com.transport.GestionTransport.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoraireTrajet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference;
    private Time heureDepart;
    private Time heureArriver;
    private int placesRestantes;

    @ManyToOne
    @JoinColumn(name = "trajet_id")
    private Trajet trajet;

    @ManyToOne
    @JoinColumn(name = "bus_id")
    @JsonBackReference
    private Bus bus;

    @OneToMany(mappedBy = "horaireTrajet")
    @JsonBackReference
    private List<Client> clients;

}
