package com.transport.GestionTransport.repositories;

import com.transport.GestionTransport.entities.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusRepository extends JpaRepository <Bus, Long> {
    Bus findBusById(Long id);
    List<Bus> findByNMatriculeContainingIgnoreCase(String query);

}
