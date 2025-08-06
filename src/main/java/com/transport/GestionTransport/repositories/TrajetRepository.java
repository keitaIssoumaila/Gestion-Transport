package com.transport.GestionTransport.repositories;

import com.transport.GestionTransport.entities.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrajetRepository extends JpaRepository<Trajet, Long> {
    // Exemples de méthodes personnalisées (facultatif)
    Trajet findTrajetById(Long id);

    List<Trajet> findAllByNomContainingIgnoreCaseOrPointDepartContainingIgnoreCase(String query, String query1);

    List<Trajet> findAllByDate(LocalDate date);

    List<Trajet> findByDateAfter(LocalDate date);
}

