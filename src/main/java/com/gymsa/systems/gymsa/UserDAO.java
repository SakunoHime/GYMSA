package com.gymsa.systems.gymsa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Coleccion de operaciones para trabajar con la clase usuario

public class UserDAO {
    private static final String DB_URL = "jdbc:sqlite:mydatabase.db";

    public static String getPasswordHashByUsername(String username) {
        String sql = "SELECT password FROM users WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la contraseña: " + e.getMessage());
        }
        return null;
    }
}

