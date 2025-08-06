package com.transport.GestionTransport.repositories;

import com.transport.GestionTransport.entities.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface BusRepository extends JpaRepository <Bus, Long> {
    Bus findBusById(Long id);
    List<Bus> findByMatriculeContainingIgnoreCase(String query);
}
