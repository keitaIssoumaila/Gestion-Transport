package com.transport.GestionTransport.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrajetDTO {
    private Long id;
    private String nom;
    private LocalDate date;
    private String pointDepart;
    private String pointArriver;
    private Long horaireTrajetId;
}

