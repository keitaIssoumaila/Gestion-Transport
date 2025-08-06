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
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String adresse;
    private String logo;
    private String email;

    @ElementCollection
    @CollectionTable(name = "entreprise_telephones", joinColumns = @JoinColumn(name = "entreprise_id"))
    @Column(name = "telephone")
    private List<@Size(min = 8, max = 8) String> telephones;

    @OneToMany(mappedBy = "entreprise")
    @JsonManagedReference
    private List<Contrat> contrats;


}
