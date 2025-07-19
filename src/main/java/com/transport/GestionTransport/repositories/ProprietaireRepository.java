package com.transport.GestionTransport.repositories;

import com.transport.GestionTransport.entities.Proprietaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProprietaireRepository extends JpaRepository<Proprietaire, Long> {
   Proprietaire findProprietaireById(Long id);

   List<Proprietaire> findAllByNomContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrCinContainingIgnoreCase(String query, String query1, String query2);
}

