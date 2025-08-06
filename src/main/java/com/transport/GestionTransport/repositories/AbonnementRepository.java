package com.transport.GestionTransport.repositories;

import com.transport.GestionTransport.entities.Client;
import com.transport.GestionTransport.entities.Abonnement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AbonnementRepository extends JpaRepository <Abonnement, Long> {

    List<Abonnement> findByNomContainingIgnoreCase(String query);
}
