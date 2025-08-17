package com.transport.GestionTransport.dtos;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String email;
    @Size(min = 8, max = 8)
    private String telephone;
    private Long trajetId;
    private Long horaireTrajetId;
    private Long paiementId;
    private String modePaiement;
}

