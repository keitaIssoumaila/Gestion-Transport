package com.transport.GestionTransport.repositories;

import com.transport.GestionTransport.entities.Client;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository <Client, Long> {
    Client findClientById(Long id);

    List<Client> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrTelephoneContainingIgnoreCase(String query, String query1, String query2);

    Optional<Client> findByTelephone(String telephone);

}
