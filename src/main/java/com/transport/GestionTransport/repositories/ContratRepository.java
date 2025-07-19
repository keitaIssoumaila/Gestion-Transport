package com.transport.GestionTransport.repositories;

import com.transport.GestionTransport.entities.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratRepository extends JpaRepository<Contrat, Long> {
    Contrat findContratById(Long id);
    List<Contrat> findByEntrepriseId(Long entrepriseId);
    List<Contrat> findByProprietaireId(Long proprietaireId);

    List<Contrat> findAllByReferenceContainingIgnoreCase(String query);
}

