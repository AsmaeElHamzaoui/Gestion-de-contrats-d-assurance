package com.assurance.service;

import com.assurance.dao.ClientDAO;
import com.assurance.dao.ContratDAO;
import com.assurance.model.Client;
import com.assurance.model.Contrat;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ContratService {
    private ContratDAO contratDAO;
    private ClientDAO clientDAO;

    public ContratService() {
        this.contratDAO = new ContratDAO();
        this.clientDAO = new ClientDAO();
    }

    public Contrat addContrat(Contrat contrat) throws SQLException {
        if (clientDAO.getClientById(contrat.getClientId()) != null) {
            int generatedId = contratDAO.addContrat(contrat);
            contrat.setId(generatedId);
            return contrat;
        } else {
            throw new IllegalArgumentException("Client ID invalide.");
        }
    }

    public void updateContrat(Contrat contrat) throws SQLException {
        if (clientDAO.getClientById(contrat.getClientId()) != null) {
            contratDAO.updateContrat(contrat);
        } else {
            throw new IllegalArgumentException("Client ID invalide.");
        }
    }

    public Contrat getContratById(int id) throws SQLException {
        Contrat contrat = contratDAO.getContratById(id);
        if (contrat != null) {
            Client client = clientDAO.getClientById(contrat.getClientId());
            if (client != null) {
                return new Contrat(contrat.getId(), contrat.getTypeContrat(), contrat.getDateDebut(), contrat.getDateFin(), contrat.getClientId());
            }
        }
        return null;
    }

    public List<Contrat> getAllContratsWithClients() throws SQLException {
        List<Contrat> contrats = contratDAO.getAllContrats();
        List<Client> clients = clientDAO.getAllClients();
        return contrats.stream()
                .map(contrat -> {
                    Client client = clients.stream()
                            .filter(c -> c.getId() == contrat.getClientId())
                            .findFirst()
                            .orElse(null);
                    return client != null ? new Contrat(contrat.getId(), contrat.getTypeContrat(), contrat.getDateDebut(), contrat.getDateFin(), contrat.getClientId()) : contrat;
                })
                .collect(Collectors.toList());
    }

    public void deleteContrat(int id) throws SQLException {
        contratDAO.deleteContrat(id);
    }

}