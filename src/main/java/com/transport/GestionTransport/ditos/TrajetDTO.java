package com.transport.GestionTransport.ditos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrajetDTO {
    private Long id;
    private String reference;
    private String nom;
    private LocalDate date;
    private String pointDepart;
    private String pointArriver;
    private Long horaireTrajetId;
}

