package com.assurance.dao;

import com.assurance.model.Contrat;
import com.assurance.enums.TypeContrat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContratDAO {
    public int addContrat(Contrat contrat) throws SQLException {
        String query = "INSERT INTO contrats (type_contrat, date_debut, date_fin, client_id) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, contrat.getTypeContrat().name());
            stmt.setDate(2, java.sql.Date.valueOf(contrat.getDateDebut()));
            stmt.setDate(3, java.sql.Date.valueOf(contrat.getDateFin()));
            stmt.setInt(4, contrat.getClientId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            throw new SQLException("Failed to retrieve generated ID");
        }
    }

    public void updateContrat(Contrat contrat) throws SQLException {
        String query = "UPDATE contrats SET type_contrat = ?, date_debut = ?, date_fin = ?, client_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, contrat.getTypeContrat().name());
            stmt.setDate(2, java.sql.Date.valueOf(contrat.getDateDebut()));
            stmt.setDate(3, java.sql.Date.valueOf(contrat.getDateFin()));
            stmt.setInt(4, contrat.getClientId());
            stmt.setInt(5, contrat.getId());
            stmt.executeUpdate();
        }
    }

    public Contrat getContratById(int id) throws SQLException {
        String query = "SELECT * FROM contrats WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Contrat(rs.getInt("id"), TypeContrat.valueOf(rs.getString("type_contrat")),
                        rs.getDate("date_debut").toLocalDate(), rs.getDate("date_fin").toLocalDate(), rs.getInt("client_id"));
            }
            return null;
        }
    }

    public List<Contrat> getAllContrats() throws SQLException {
        List<Contrat> contrats = new ArrayList<>();
        String query = "SELECT * FROM contrats";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                contrats.add(new Contrat(rs.getInt("id"), TypeContrat.valueOf(rs.getString("type_contrat")),
                        rs.getDate("date_debut").toLocalDate(), rs.getDate("date_fin").toLocalDate(), rs.getInt("client_id")));
            }
        }
        return contrats;
    }

    public void deleteContrat(int id) throws SQLException {
        String query = "DELETE FROM contrats WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Contrat> getContratsByClientId(int clientId) throws SQLException {
        List<Contrat> contrats = new ArrayList<>();
        String query = "SELECT * FROM contrats WHERE client_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                contrats.add(new Contrat(rs.getInt("id"), TypeContrat.valueOf(rs.getString("type_contrat")),
                        rs.getDate("date_debut").toLocalDate(), rs.getDate("date_fin").toLocalDate(), rs.getInt("client_id")));
            }
        }
        return contrats;
    }
}