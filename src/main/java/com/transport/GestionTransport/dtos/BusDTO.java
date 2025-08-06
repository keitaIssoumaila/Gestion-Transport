package com.transport.GestionTransport.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusDTO {
    private Long id;
    private String matricule;
    private LocalDate date;
    private String typeBus;
    private int nombrePlace;
    private String status;
    private Long proprietaireId;
}

