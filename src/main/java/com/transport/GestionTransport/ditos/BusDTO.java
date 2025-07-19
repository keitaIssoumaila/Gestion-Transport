package com.transport.GestionTransport.ditos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusDTO {
    private Long id;
    private String nMatricule;
    private Date date;
    private String typeBus;
    private int nombrePlace;
    private String status;
    private Long proprietaireId;
}

