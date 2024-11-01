package com.gymsa.systems.gymsa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private static final String DB_URL = "jdbc:sqlite:mydatabase.db";

    // Método para obtener el usuario por su nombre de usuario
    public static Usuario getUsuarioByUsername(String username) {
        String sql = "SELECT id, name, password, is_admin FROM users WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            // Si el usuario existe, devolvemos un objeto Usuario con los datos obtenidos
            if (rs.next()) {
                int id = rs.getInt("id"); // Obtener el ID del usuario
                String name = rs.getString("name");
                String passwordHash = rs.getString("password");
                boolean isAdmin = rs.getBoolean("is_admin");
                return new Usuario(id, name, passwordHash, isAdmin); // Usar el ID en el constructor
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el usuario: " + e.getMessage());
        }
        return null;
    }


    // Método para actualizar el nombre de usuario y la contraseña
    public static void updateUsuario(int userId, String newUsername, String newPassword) {
        // Hashear la nueva contraseña antes de actualizarla
        String hashedPassword = PasswordUtils.hashPassword(newPassword);

        String sql = "UPDATE users SET name = ?, password = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newUsername);
            pstmt.setString(2, hashedPassword); // Usar la contraseña hasheada
            pstmt.setInt(3, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar el usuario: " + e.getMessage());
        }
    }



    // Método para verificar si la contraseña actual coincide
    // Método para verificar si la contraseña actual coincide
    public static boolean verifyPassword(Usuario usuario, String password) {
        String sql = "SELECT password FROM users WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getNombre());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPasswordHash = rs.getString("password");
                // Usar el método checkPassword para verificar
                return PasswordUtils.checkPassword(password, storedPasswordHash);
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar la contraseña: " + e.getMessage());
        }
        return false;
    }

    // Método para obtener el usuario por su ID
    public Usuario getUsuarioById(int id) {
        String query = "SELECT * FROM users WHERE id = ?"; // Consulta para obtener el usuario por ID

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id); // Asignar el valor del parámetro ID

            try (ResultSet rs = pstmt.executeQuery()) {
                // Si se encuentra el usuario con el ID especificado
                if (rs.next()) {
                    int userId = rs.getInt("id");
                    String name = rs.getString("name");
                    String password = rs.getString("password");
                    boolean isAdmin = rs.getBoolean("is_admin");

                    // Crear y retornar un objeto Usuario con los datos obtenidos
                    return new Usuario(userId, name, password, isAdmin);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener el usuario por ID: " + e.getMessage());
        }

        // Retornar null si no se encuentra ningún usuario con el ID especificado
        return null;
    }

    // Método para agregar un nuevo usuario
    public static void addUsuario(Usuario usuario) {
        String sql = "INSERT INTO users (name, password, is_admin) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, PasswordUtils.hashPassword(usuario.getPassword())); // Asegúrate de tener acceso a la contraseña
            pstmt.setBoolean(3, usuario.isAdmin());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al agregar el usuario: " + e.getMessage());
        }
    }

    // Método para modificar un usuario existente
    public static void updateUsuario(Usuario usuario) {
        String sql = "UPDATE users SET name = ?, password = ?, is_admin = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, PasswordUtils.hashPassword(usuario.getPassword())); // Hashear la contraseña si se modifica
            pstmt.setBoolean(3, usuario.isAdmin());
            pstmt.setInt(4, usuario.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al modificar el usuario: " + e.getMessage());
        }
    }

    // Método para eliminar un usuario
    public static void deleteUsuario(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar el usuario: " + e.getMessage());
        }
    }

    public static int getNextUserId() {
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 AS next_id FROM users"; // Obtener el máximo ID y sumar 1
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("next_id"); // Retornar el siguiente ID disponible
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el siguiente ID: " + e.getMessage());
        }
        return 1; // Retornar 1 si hay un error (para el caso de un primer usuario)
    }

    // Método para verificar si un usuario ya existe en la base de datos
    public static boolean userExists(String username) {
        String sql = "SELECT COUNT(*) AS count FROM users WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0; // Retornar true si el conteo es mayor que 0
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar si el usuario existe: " + e.getMessage());
        }
        return false; // Retornar false si hay un error o si el usuario no existe
    }

    public static boolean isUserAdmin(int userId) {
        // Consulta la base de datos para verificar si el usuario con el ID dado es administrador
        String query = "SELECT is_admin FROM users WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("is_admin");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }







}

