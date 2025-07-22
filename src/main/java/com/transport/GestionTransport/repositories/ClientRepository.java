package com.transport.GestionTransport.repositories;

import com.transport.GestionTransport.entities.Client;
import com.transport.GestionTransport.entities.CompteAbonnement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClientRepository extends JpaRepository <Client, Long> {
    Client findClientById(Long id);

    List<Client> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String query, String query1);
}
