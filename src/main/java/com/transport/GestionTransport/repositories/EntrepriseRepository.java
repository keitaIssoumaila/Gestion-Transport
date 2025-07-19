package com.transport.GestionTransport.repositories;

import com.transport.GestionTransport.entities.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {
    Entreprise findEntrepriseById(Long id);

    List<Entreprise> findAllByNomContainingIgnoreCaseOrEmailContainingIgnoreCase(String query, String query1);
}

