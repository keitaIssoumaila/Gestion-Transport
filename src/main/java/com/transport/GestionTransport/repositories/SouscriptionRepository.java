package com.transport.GestionTransport.repositories;

import com.transport.GestionTransport.entities.Souscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SouscriptionRepository extends JpaRepository<Souscription, Long> {
    List<Souscription> findByClientId(Long clientId);
    boolean existsByClientIdAndDateFinAfter(Long clientId, java.time.LocalDate date);
}
