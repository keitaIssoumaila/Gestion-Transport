package com.transport.GestionTransport.repositories;

import com.transport.GestionTransport.entities.Conducteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConducteurRepository extends JpaRepository<Conducteur, Long> {

    // Pour retourner tous les conducteurs d’un bus
    List<Conducteur> findAllByBusId(Long busId);

    // Pour retourner le conducteur "actif" d’un bus (ou unique)
    Conducteur findFirstByBusId(Long busId);


    List<Conducteur> findAllByNomContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrTelephoneContainingIgnoreCase(String query, String query1, String query2);
}
