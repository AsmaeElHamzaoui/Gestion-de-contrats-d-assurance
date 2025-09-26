package com.assurance.service;

import com.assurance.dao.ContratDAO;
import com.assurance.dao.SinistreDAO;
import com.assurance.model.Contrat;
import com.assurance.model.Sinistre;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class SinistreService {
    private SinistreDAO sinistreDAO;
    private ContratDAO contratDAO;

    public SinistreService() {
        this.sinistreDAO = new SinistreDAO();
        this.contratDAO = new ContratDAO();
    }

    public Sinistre addSinistre(Sinistre sinistre) throws SQLException {
        if (contratDAO.getContratById(sinistre.getContratId()) != null) {
            int generatedId = sinistreDAO.addSinistre(sinistre);
            sinistre.setId(generatedId);
            return sinistre;
        } else {
            throw new IllegalArgumentException("Contrat ID invalide.");
        }
    }

    public void updateSinistre(Sinistre sinistre) throws SQLException {
        if (contratDAO.getContratById(sinistre.getContratId()) != null) {
            sinistreDAO.updateSinistre(sinistre);
        } else {
            throw new IllegalArgumentException("Contrat ID invalide.");
        }
    }

    public Sinistre getSinistreById(int id) throws SQLException {
        Sinistre sinistre = sinistreDAO.getSinistreById(id);
        if (sinistre != null) {
            Contrat contrat = contratDAO.getContratById(sinistre.getContratId());
            if (contrat != null) {
                return new Sinistre(sinistre.getId(), sinistre.getTypeSinistre(), sinistre.getDateTime(), sinistre.getCout(), sinistre.getDescription(), sinistre.getContratId());
            }
        }
        return null;
    }

    public List<Sinistre> getAllSinistresWithContrats() throws SQLException {
        List<Sinistre> sinistres = sinistreDAO.getAllSinistres();
        List<Contrat> contrats = contratDAO.getAllContrats();
        return sinistres.stream()
                .map(sinistre -> {
                    Contrat contrat = contrats.stream()
                            .filter(c -> c.getId() == sinistre.getContratId())
                            .findFirst()
                            .orElse(null);
                    return contrat != null ? new Sinistre(sinistre.getId(), sinistre.getTypeSinistre(), sinistre.getDateTime(), sinistre.getCout(), sinistre.getDescription(), sinistre.getContratId()) : sinistre;
                })
                .collect(Collectors.toList());
    }

    public void deleteSinistre(int id) throws SQLException {
        sinistreDAO.deleteSinistre(id);
    }
}