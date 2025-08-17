package com.transport.GestionTransport.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String email;
    @Size(min = 8, max = 8)
    private String telephone;


    @OneToMany(mappedBy = "client")
    private List<Paiement> paiements;

    @ManyToOne
    @JoinColumn(name = "horaire_trajet_id")
    private HoraireTrajet horaireTrajet;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Souscription> souscriptions;

    @ManyToOne
    @JoinColumn(name = "trajet_id")
    private Trajet trajet;

}
