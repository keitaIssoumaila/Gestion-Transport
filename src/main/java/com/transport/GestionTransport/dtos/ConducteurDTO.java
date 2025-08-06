package com.transport.GestionTransport.dtos;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConducteurDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String nina;
    private LocalDate dateNaissance;
    @Size(min = 8, max = 8)
    private String telephone;
    private Long busId;
}

