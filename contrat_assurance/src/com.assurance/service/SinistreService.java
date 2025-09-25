package com.assurance.service;

import com.assurance.dao.ContratDAO;
import com.assurance.dao.SinistreDAO;
import com.assurance.model.Contrat;
import com.assurance.model.Sinistre;

import java.sql.SQLException;
import java.time.LocalDateTime;
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

    public double calculateTotalCostByClientId(int clientId) throws SQLException {
        if (contratDAO.getAllContrats().stream().anyMatch(c -> c.getClientId() == clientId)) {
            return sinistreDAO.getSinistresByClientId(clientId).stream()
                    .mapToDouble(Sinistre::getCout)
                    .sum();
        } else {
            throw new IllegalArgumentException("Client ID invalide.");
        }
    }

    public List<Sinistre> getSinistresByContratId(int contratId) throws SQLException {
        if (contratDAO.getContratById(contratId) != null) {
            return sinistreDAO.getSinistresByContratId(contratId);
        } else {
            throw new IllegalArgumentException("Contrat ID invalide.");
        }
    }

    public List<Sinistre> getSinistresByClientId(int clientId) throws SQLException {
        if (contratDAO.getAllContrats().stream().anyMatch(c -> c.getClientId() == clientId)) {
            return sinistreDAO.getSinistresByClientId(clientId);
        } else {
            throw new IllegalArgumentException("Client ID invalide.");
        }
    }

    public List<Sinistre> getSinistresSortedByCostDescending() throws SQLException {
        return sinistreDAO.getAllSinistres().stream()
                .sorted((s1, s2) -> Double.compare(s2.getCout(), s1.getCout()))
                .collect(Collectors.toList());
    }

    public List<Sinistre> getSinistresBeforeDate(LocalDateTime date) throws SQLException {
        return sinistreDAO.getAllSinistres().stream()
                .filter(s -> s.getDateTime().isBefore(date))
                .collect(Collectors.toList());
    }

    public List<Sinistre> getSinistresAboveCost(double cost) throws SQLException {
        return sinistreDAO.getAllSinistres().stream()
                .filter(s -> s.getCout() > cost)
                .collect(Collectors.toList());
    }
}