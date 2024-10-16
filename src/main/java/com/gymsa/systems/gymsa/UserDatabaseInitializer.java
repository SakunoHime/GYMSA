package com.gymsa.systems.gymsa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Crea la base de datos para usuarios (tablas) e inserta un archivo que almacena la información de manera local.
public class UserDatabaseInitializer {
    private static final String DB_URL = "jdbc:sqlite:mydatabase.db";
    private static final String USER_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, password TEXT NOT NULL, is_admin BOOLEAN NOT NULL)";

    public static void main(String[] args) {
        initializeDatabase();
    }

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                System.out.println("Conexión a la base de datos establecida.");

                try (Statement stmt = conn.createStatement()) {
                    // Crear la tabla de usuarios si no existe
                    stmt.execute(USER_TABLE_CREATE);

                    // Verificar si la tabla está vacía
                    String countQuery = "SELECT COUNT(*) FROM users";
                    try (ResultSet rs = stmt.executeQuery(countQuery)) {
                        if (rs.next() && rs.getInt(1) == 0) {
                            // Insertar un usuario administrador por defecto
                            String defaultAdminName = "admin";
                            String defaultAdminPassword = PasswordUtils.hashPassword("admin123");
                            insertUser(conn, defaultAdminName, defaultAdminPassword, true);
                            System.out.println("Usuario administrador creado.");
                        } else {
                            System.out.println("La tabla de usuarios ya contiene datos.");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }

    private static void insertUser(Connection conn, String name, String password, boolean isAdmin) throws SQLException {
        String sql = "INSERT INTO users(name, password, is_admin) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            pstmt.setBoolean(3, isAdmin);
            pstmt.executeUpdate();
        }
    }
}
