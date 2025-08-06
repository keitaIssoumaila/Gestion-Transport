package com.transport.GestionTransport.services;

import com.transport.GestionTransport.dtos.BusDTO;
import com.transport.GestionTransport.entities.Bus;
import com.transport.GestionTransport.entities.Proprietaire;
import com.transport.GestionTransport.repositories.BusRepository;
import com.transport.GestionTransport.repositories.ProprietaireRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusService {

    private final BusRepository busRepository;
    private final ProprietaireRepository proprietaireRepository;

    public BusService(BusRepository busRepository, ProprietaireRepository proprietaireRepository) {
        this.busRepository = busRepository;
        this.proprietaireRepository = proprietaireRepository;
    }

    public List<Bus> getBus() {return busRepository.findAll();}

    public ResponseEntity<?> createBus(BusDTO busDTO) {
        System.out.println("🚀 Début création Bus");
        try {
            Bus bus = new Bus();
            bus.setMatricule(busDTO.getMatricule());
            bus.setDate(busDTO.getDate());
            bus.setTypeBus(busDTO.getTypeBus());
            bus.setNombrePlace(busDTO.getNombrePlace());
            bus.setStatus(busDTO.getStatus());

            Proprietaire proprietaire = proprietaireRepository.findById(busDTO.getProprietaireId())
                    .orElse(null);

            if (proprietaire == null) {
                return ResponseEntity.badRequest().body(" Propriétaire non trouvé");
            }

            bus.setProprietaire(proprietaire);
            busRepository.save(bus);
            System.out.println("✅ Fin création Bus");
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.status(500).body(" Erreur création Bus : " + e.getMessage());
        }
    }

    public ResponseEntity<?> editBus(BusDTO busDTO, Long id) {
        Bus bus = busRepository.findById(id).orElse(null);
        if (bus == null) {
            return ResponseEntity.notFound().build();
        } else {
            bus.setMatricule(busDTO.getMatricule());
            bus.setDate(busDTO.getDate());
            bus.setTypeBus(busDTO.getTypeBus());
            bus.setNombrePlace(busDTO.getNombrePlace());
            bus.setStatus(busDTO.getStatus());

            Proprietaire proprietaire = proprietaireRepository.findById(busDTO.getProprietaireId())
                    .orElse(null);

            if (proprietaire == null) {
                return ResponseEntity.badRequest().body(" Propriétaire non trouvé");
            }

            bus.setProprietaire(proprietaire);
            busRepository.save(bus);
            return ResponseEntity.ok().build();
        }
    }

    public ResponseEntity<?> deleteBus(Long id) {
        Bus bus = busRepository.findById(id).orElse(null);
        if (bus == null) {
            return ResponseEntity.notFound().build();
        } else {
            busRepository.delete(bus);
            return ResponseEntity.ok().build();
        }
    }

    public ResponseEntity<?> searchBus(String query) {
        List<Bus> bus = busRepository.findByMatriculeContainingIgnoreCase(query);
        if (bus.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(bus);
        }
    }

    public ResponseEntity<?> getBusById(Long id) {
        Bus bus = busRepository.findById(id).orElse(null);
        if (bus == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(bus);
        }
    }
}