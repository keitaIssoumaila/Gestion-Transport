package com.transport.GestionTransport.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SouscriptionDTO {
    private Long clientId;
    private Long abonnementId;
    private String modePaiement;
}
