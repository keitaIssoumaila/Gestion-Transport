package com.transport.GestionTransport.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbonnementDTO {
    private Long id;
    private String nom;
    private String description;
    private Double montant;
    private Integer dureeMois;
    private Boolean actif = true;
}

