package com.transport.GestionTransport.repositories;

import com.transport.GestionTransport.entities.Conducteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConducteurRepository extends JpaRepository<Conducteur, Long> {
    Conducteur findConducteurById(Long id);

    List<Conducteur> findAllByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String query, String query1);
}
