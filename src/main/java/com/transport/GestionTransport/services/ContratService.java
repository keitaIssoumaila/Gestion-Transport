package com.transport.GestionTransport.services;

import com.transport.GestionTransport.dtos.ContratDTO;
import com.transport.GestionTransport.entities.Contrat;
import com.transport.GestionTransport.entities.Entreprise;
import com.transport.GestionTransport.entities.Proprietaire;
import com.transport.GestionTransport.repositories.ContratRepository;
import com.transport.GestionTransport.repositories.EntrepriseRepository;
import com.transport.GestionTransport.repositories.ProprietaireRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContratService {

    private final ContratRepository contratRepository;
    private final ProprietaireRepository proprietaireRepository;
    private final EntrepriseRepository entrepriseRepository;

    public ContratService(ContratRepository contratRepository,
                          ProprietaireRepository proprietaireRepository,
                          EntrepriseRepository entrepriseRepository) {
        this.contratRepository = contratRepository;
        this.proprietaireRepository = proprietaireRepository;
        this.entrepriseRepository = entrepriseRepository;
    }

    public List<Contrat> getAllContrats() {
        return contratRepository.findAll();
    }

    public ResponseEntity<?> createContrat(ContratDTO dto) {
        Proprietaire proprietaire = proprietaireRepository.findById(dto.getProprietaireId()).orElse(null);
        if (proprietaire == null) return ResponseEntity.badRequest().body("❌ Propriétaire non trouvé.");

        Entreprise entreprise = entrepriseRepository.findById(dto.getEntrepriseId()).orElse(null);
        if (entreprise == null) return ResponseEntity.badRequest().body("❌ Entreprise non trouvée.");

        Contrat contrat = new Contrat();
        contrat.setDateContrat(dto.getDateContrat());
        contrat.setReference("SOUSC-" + System.currentTimeMillis());
        contrat.setProprietaire(proprietaire);
        contrat.setEntreprise(entreprise);

        contratRepository.save(contrat);
        return ResponseEntity.ok(contrat);
    }

    public ResponseEntity<?> editContrat(Long id, ContratDTO dto) {
        Contrat contrat = contratRepository.findById(id).orElse(null);
        if (contrat == null) return ResponseEntity.notFound().build();

        contrat.setDateContrat(dto.getDateContrat());
        contrat.setReference("SOUSC-" + System.currentTimeMillis());

        if (dto.getProprietaireId() != null) {
            Proprietaire proprietaire = proprietaireRepository.findById(dto.getProprietaireId()).orElse(null);
            if (proprietaire == null) return ResponseEntity.badRequest().body(" Propriétaire non trouvé.");
            contrat.setProprietaire(proprietaire);
        }

        if (dto.getEntrepriseId() != null) {
            Entreprise entreprise = entrepriseRepository.findById(dto.getEntrepriseId()).orElse(null);
            if (entreprise == null) return ResponseEntity.badRequest().body(" Entreprise non trouvée.");
            contrat.setEntreprise(entreprise);
        }

        contratRepository.save(contrat);
        return ResponseEntity.ok(contrat);
    }

    public ResponseEntity<?> getContratById(Long id) {
        Contrat contrat = contratRepository.findById(id).orElse(null);
        if (contrat == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(contrat);
        }
    }


    public ResponseEntity<?> deleteContrat(Long id) {
        if (!contratRepository.existsById(id))
            return ResponseEntity.notFound().build();

        contratRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> searchContrat(String query) {
        List<Contrat> contrats = contratRepository.findAllByReferenceContainingIgnoreCase(query);
        if (contrats.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(contrats);
    }

    //affiche les contrats d'un propriétaire
    public ResponseEntity<?> getContratsByProprietaire(Long proprietaireId) {
        List<Contrat> contrats = contratRepository.findAllByProprietaireId(proprietaireId);
        if (contrats.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(contrats);
    }

}

