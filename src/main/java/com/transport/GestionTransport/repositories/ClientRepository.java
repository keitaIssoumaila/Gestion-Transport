package com.transport.GestionTransport.repositories;

import com.transport.GestionTransport.entities.Client;
import com.transport.GestionTransport.entities.CompteAbonnement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository <Client, Long> {
    Client findClientById(Long id);

    List<Client> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String query, String query1);
}
