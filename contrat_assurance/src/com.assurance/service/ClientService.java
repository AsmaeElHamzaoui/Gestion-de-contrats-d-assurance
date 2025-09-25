package com.assurance.service;

import com.assurance.dao.ClientDAO;
import com.assurance.dao.ConseillerDAO;
import com.assurance.model.Client;
import com.assurance.model.Conseiller;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ClientService {
    private ClientDAO clientDAO;
    private ConseillerDAO conseillerDAO;

    public ClientService() {
        this.clientDAO = new ClientDAO();
        this.conseillerDAO = new ConseillerDAO();
    }

    public Client addClient(Client client) throws SQLException {
        if (conseillerDAO.getConseillerById(client.getConseillerId()) != null) {
            int generatedId = clientDAO.addClient(client);
            client.setId(generatedId);
            return client;
        } else {
            throw new IllegalArgumentException("Conseiller ID invalide.");
        }
    }

    public void updateClient(Client client) throws SQLException {
        if (conseillerDAO.getConseillerById(client.getConseillerId()) != null) {
            clientDAO.updateClient(client);
        } else {
            throw new IllegalArgumentException("Conseiller ID invalide.");
        }
    }

    public Client getClientById(int id) throws SQLException {
        Client client = clientDAO.getClientById(id);
        if (client != null) {
            Conseiller conseiller = conseillerDAO.getConseillerById(client.getConseillerId());
            if (conseiller != null) {
                return new Client(client.getId(), client.getNom(), client.getPrenom(), client.getEmail(), client.getConseillerId());
            }
        }
        return null;
    }

    public List<Client> getAllClientsWithConseillers() throws SQLException {
        List<Client> clients = clientDAO.getAllClients();
        List<Conseiller> conseillers = conseillerDAO.getAllConseillers();
        return clients.stream().map(client -> {
            Conseiller conseiller = conseillers.stream().filter(c -> c.getId() == client.getConseillerId()).findFirst().orElse(null);
            return conseiller != null ? new Client(client.getId(), client.getNom(), client.getPrenom(), client.getEmail(), client.getConseillerId()) : client;
        }).collect(Collectors.toList());
    }

    public void deleteClient(int id) throws SQLException {
        clientDAO.deleteClient(id);
    }

    public List<Client> getClientsByConseillerId(int conseillerId) throws SQLException {
        if (conseillerDAO.getConseillerById(conseillerId) != null) {
            return clientDAO.getClientsByConseillerId(conseillerId);
        } else {
            throw new IllegalArgumentException("Conseiller ID invalide.");
        }
    }

    public List<Client> searchClientsByNom(String nom) throws SQLException {
        List<Client> clients = clientDAO.getAllClients();
        return clients.stream()
                .filter(client -> client.getNom().toLowerCase().contains(nom.toLowerCase()))
                .sorted((c1, c2) -> c1.getNom().compareToIgnoreCase(c2.getNom()))
                .collect(Collectors.toList());
    }
}

