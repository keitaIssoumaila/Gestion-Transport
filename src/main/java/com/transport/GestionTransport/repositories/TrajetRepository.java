package com.transport.GestionTransport.repositories;

import com.transport.GestionTransport.entities.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrajetRepository extends JpaRepository<Trajet, Long> {
    // Exemples de méthodes personnalisées (facultatif)
    Trajet findTrajetById(Long id);

    List<Trajet> findAllByNomContainingIgnoreCaseOrReferenceContainingIgnoreCaseOrPointDepartContainingIgnoreCase(String query, String query1, String query2);
}

