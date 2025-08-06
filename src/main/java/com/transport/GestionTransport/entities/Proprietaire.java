package com.transport.GestionTransport.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    private String nina;
    private String nom;
    private String prenom;
    @Size(min = 8, max = 8)
    private String telephone;
    private String dateNaissance;

    @OneToMany(mappedBy = "proprietaire")
    @JsonManagedReference
    private List<Bus> busList;

    @OneToMany(mappedBy = "proprietaire")
    @JsonManagedReference
    private List<Contrat> contrats;
}
