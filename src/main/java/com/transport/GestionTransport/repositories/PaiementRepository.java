package com.transport.GestionTransport.repositories;

import com.transport.GestionTransport.entities.Entreprise;
import com.transport.GestionTransport.entities.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    Paiement findPaiementById(Long id);

    List<Paiement> findAllByReferenceContainingIgnoreCaseOrModePaiementContainingIgnoreCase(String query, String query1);
}
