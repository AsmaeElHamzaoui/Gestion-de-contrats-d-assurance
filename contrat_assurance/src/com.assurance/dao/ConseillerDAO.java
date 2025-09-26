package com.assurance.dao;

import com.assurance.model.Conseiller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConseillerDAO {
    public int addConseiller(Conseiller conseiller) throws SQLException {
        String query = "INSERT INTO conseillers (nom, prenom, email) VALUES (?, ?, ?) RETURNING id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, conseiller.getNom());
            stmt.setString(2, conseiller.getPrenom());
            stmt.setString(3, conseiller.getEmail());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            throw new SQLException("Failed to retrieve generated ID");
        }
    }

    public void updateConseiller(Conseiller conseiller) throws SQLException {
        String query = "UPDATE conseillers SET nom = ?, prenom = ?, email = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, conseiller.getNom());
            stmt.setString(2, conseiller.getPrenom());
            stmt.setString(3, conseiller.getEmail());
            stmt.setInt(4, conseiller.getId());
            stmt.executeUpdate();
        }
    }

    public Conseiller getConseillerById(int id) throws SQLException {
        String query = "SELECT * FROM conseillers WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Conseiller(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"));
            }
            return null;
        }
    }

    public List<Conseiller> getAllConseillers() throws SQLException {
        List<Conseiller> conseillers = new ArrayList<>();
        String query = "SELECT * FROM conseillers";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                conseillers.add(new Conseiller(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email")));
            }
        }
        return conseillers;
    }

    public void deleteConseiller(int id) throws SQLException {
        String query = "DELETE FROM conseillers WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}