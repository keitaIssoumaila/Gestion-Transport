package com.transport.GestionTransport.repositories;

import com.transport.GestionTransport.entities.HoraireTrajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoraireTrajetRepository extends JpaRepository<HoraireTrajet, Long> {
    // Exemples de méthodes personnalisées (facultatif)
    HoraireTrajet findHoraireTrajetById(Long id);

    List<HoraireTrajet> findAllByReferenceContainingIgnoreCase(String query);

    List<HoraireTrajet> findAllByBusId(Long busId);
}
