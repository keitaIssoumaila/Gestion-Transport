package com.transport.GestionTransport.ditos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProprietaireDTO {
    private Long id;
    private String cin;
    private String nom;
    private String prenom;
    private String dateNaissance;
}

