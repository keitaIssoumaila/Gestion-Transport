package com.transport.GestionTransport.dtos;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntrepriseDTO {
    private Long id;
    private String nom;
    private String adresse;
    private String logo;
    private String email;
    private List<@Size(min = 8, max = 8) String> telephones;
}

