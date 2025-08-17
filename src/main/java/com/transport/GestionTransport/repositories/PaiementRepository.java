package com.transport.GestionTransport.repositories;

import com.transport.GestionTransport.entities.Entreprise;
import com.transport.GestionTransport.entities.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    Paiement findPaiementById(Long id);

    List<Paiement> findAllByModePaiementContainingIgnoreCase(String query);

    List<Paiement> findByClientIdOrderByDatePaiementDesc(Long id);
}
