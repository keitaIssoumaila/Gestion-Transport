package com.transport.GestionTransport.ditos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntrepriseDTO {
    private Long id;
    private String nom;
    private String adresse;
    private String logo;
    private String telephone;
    private String email;

}

