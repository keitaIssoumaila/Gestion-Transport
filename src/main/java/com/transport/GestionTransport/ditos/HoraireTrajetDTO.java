package com.transport.GestionTransport.ditos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoraireTrajetDTO {
    private Long id;
    private String reference;
    private LocalDate dateDebut;
    private String heureDebut;
    private LocalDate dateFin;
}

