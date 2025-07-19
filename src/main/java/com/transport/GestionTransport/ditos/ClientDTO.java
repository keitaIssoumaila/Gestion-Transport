package com.transport.GestionTransport.ditos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private Long id;
    private String reference;
    private String name;
    private Date date;
    private String email;
    private int phone;
}

