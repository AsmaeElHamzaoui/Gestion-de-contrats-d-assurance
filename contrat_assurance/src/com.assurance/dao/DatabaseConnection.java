package com.assurance.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/contratAssurance";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    private static Connection connection;

    private DatabaseConnection() {
        // Empêche l'instanciation
    }

    // Singleton : une seule connexion partagée
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver"); // Charger le driver PostgreSQL
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connexion établie avec PostgreSQL !");
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver PostgreSQL introuvable", e);
            }
        }
        return connection;
    }


}
