package com.transport.GestionTransport.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoraireTrajetDTO {
    private Long id;
    private String reference;
    private LocalDate dateDebut;
    private Time heureDebut;
    private LocalDate dateFin;
    private Long busId;
}

