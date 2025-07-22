package com.transport.GestionTransport.repositories;

import com.transport.GestionTransport.entities.Client;
import com.transport.GestionTransport.entities.CompteAbonnement;
import com.transport.GestionTransport.entities.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CompteAbonnementRepository extends JpaRepository <CompteAbonnement, Long> {
    CompteAbonnement findCompteAbonnementById(Long id);

    List<CompteAbonnement> findAllByReferenceContainingIgnoreCaseOrStatusAbonnementContainingIgnoreCase(String query, String query1);

    List<CompteAbonnement> findAllByClient(Client client);

    long countByStatusAbonnementIgnoreCase(String actif);
}
