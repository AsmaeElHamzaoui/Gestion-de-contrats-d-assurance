package com.assurance.dao;

import com.assurance.model.Sinistre;
import com.assurance.enums.TypeSinistre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SinistreDAO {
    public int addSinistre(Sinistre sinistre) throws SQLException {
        String query = "INSERT INTO sinistres (type_sinistre, date_time, cout, description, contrat_id) VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sinistre.getTypeSinistre().name());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(sinistre.getDateTime()));
            stmt.setDouble(3, sinistre.getCout());
            stmt.setString(4, sinistre.getDescription());
            stmt.setInt(5, sinistre.getContratId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            throw new SQLException("Failed to retrieve generated ID");
        }
    }

    public void updateSinistre(Sinistre sinistre) throws SQLException {
        String query = "UPDATE sinistres SET type_sinistre = ?, date_time = ?, cout = ?, description = ?, contrat_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sinistre.getTypeSinistre().name());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(sinistre.getDateTime()));
            stmt.setDouble(3, sinistre.getCout());
            stmt.setString(4, sinistre.getDescription());
            stmt.setInt(5, sinistre.getContratId());
            stmt.setInt(6, sinistre.getId());
            stmt.executeUpdate();
        }
    }

    public Sinistre getSinistreById(int id) throws SQLException {
        String query = "SELECT * FROM sinistres WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Sinistre(rs.getInt("id"), TypeSinistre.valueOf(rs.getString("type_sinistre")),
                        rs.getTimestamp("date_time").toLocalDateTime(), rs.getDouble("cout"),
                        rs.getString("description"), rs.getInt("contrat_id"));
            }
            return null;
        }
    }

    public List<Sinistre> getAllSinistres() throws SQLException {
        List<Sinistre> sinistres = new ArrayList<>();
        String query = "SELECT * FROM sinistres";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                sinistres.add(new Sinistre(rs.getInt("id"), TypeSinistre.valueOf(rs.getString("type_sinistre")),
                        rs.getTimestamp("date_time").toLocalDateTime(), rs.getDouble("cout"),
                        rs.getString("description"), rs.getInt("contrat_id")));
            }
        }
        return sinistres;
    }

    public void deleteSinistre(int id) throws SQLException {
        String query = "DELETE FROM sinistres WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Sinistre> getSinistresByContratId(int contratId) throws SQLException {
        List<Sinistre> sinistres = new ArrayList<>();
        String query = "SELECT * FROM sinistres WHERE contrat_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, contratId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sinistres.add(new Sinistre(rs.getInt("id"), TypeSinistre.valueOf(rs.getString("type_sinistre")),
                        rs.getTimestamp("date_time").toLocalDateTime(), rs.getDouble("cout"),
                        rs.getString("description"), rs.getInt("contrat_id")));
            }
        }
        return sinistres;
    }

    public List<Sinistre> getSinistresByClientId(int clientId) throws SQLException {
        List<Sinistre> sinistres = new ArrayList<>();
        String query = "SELECT s.* FROM sinistres s JOIN contrats c ON s.contrat_id = c.id WHERE c.client_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sinistres.add(new Sinistre(rs.getInt("id"), TypeSinistre.valueOf(rs.getString("type_sinistre")),
                        rs.getTimestamp("date_time").toLocalDateTime(), rs.getDouble("cout"),
                        rs.getString("description"), rs.getInt("contrat_id")));
            }
        }
        return sinistres;
    }
}