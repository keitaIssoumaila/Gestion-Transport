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
    private Time heureDepart;
    private Time heureArriver;
    private int placeRestantes;
    private Long busId;
}

