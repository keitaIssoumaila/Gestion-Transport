package com.transport.GestionTransport.repositories;

import com.transport.GestionTransport.entities.Conducteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ConducteurRepository extends JpaRepository<Conducteur, Long> {
    Conducteur findConducteurById(Long id);

    List<Conducteur> findAllByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String query, String query1);
}
