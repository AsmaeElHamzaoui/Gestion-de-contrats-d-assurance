package com.assurance.service;

import com.assurance.dao.ConseillerDAO;
import com.assurance.model.Conseiller;

import java.sql.SQLException;
import java.util.List;

public class ConseillerService {
    private ConseillerDAO conseillerDAO;

    public ConseillerService() {
        this.conseillerDAO = new ConseillerDAO();
    }

    public Conseiller addConseiller(Conseiller conseiller) throws SQLException {
        int generatedId = conseillerDAO.addConseiller(conseiller);
        conseiller.setId(generatedId);
        return conseiller;
    }

    public void updateConseiller(Conseiller conseiller) throws SQLException {
        conseillerDAO.updateConseiller(conseiller);
    }

    public Conseiller getConseillerById(int id) throws SQLException {
        return conseillerDAO.getConseillerById(id);
    }

    public List<Conseiller> getAllConseillers() throws SQLException {
        return conseillerDAO.getAllConseillers();
    }

    public void deleteConseiller(int id) throws SQLException {
        conseillerDAO.deleteConseiller(id);
    }
}