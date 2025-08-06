package com.transport.GestionTransport.dtos;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProprietaireDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String nina;
    private String dateNaissance;
    @Size(min = 8, max = 8)
    private String telephone;
}

