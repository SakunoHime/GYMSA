package com.gymsa.systems.gymsa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class StudentDatabaseInitializer {
    private static final String DB_URL = "jdbc:sqlite:mydatabase.db";
    private static final String STUDENT_TABLE_CREATE = """
        CREATE TABLE IF NOT EXISTS students (
            id TEXT PRIMARY KEY,
            nombres TEXT NOT NULL,
            apellido_paterno TEXT NOT NULL,
            apellido_materno TEXT NOT NULL,
            telefono TEXT,
            sexo INTEGER NOT NULL,
            edad INTEGER NOT NULL,
            estatura REAL NOT NULL,
            peso REAL NOT NULL,
            horario INTEGER NOT NULL
        )
        """;

    public static void main(String[] args) {
        initializeDatabase();
    }

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                System.out.println("Conexión a la base de datos establecida.");

                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(STUDENT_TABLE_CREATE);
                    System.out.println("Tabla 'students' verificada o creada.");

                    String countQuery = "SELECT COUNT(*) FROM students";
                    try (ResultSet rs = stmt.executeQuery(countQuery)) {
                        if (rs.next() && rs.getInt(1) == 0) {
                            System.out.println("Tabla alumnos está vacia");
                            insertSampleStudents(conn); // Insertar alumnos de prueba
                        } else {
                            System.out.println("La tabla de alumnos ya contiene datos.");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }

    private static void insertSampleStudents(Connection conn) throws SQLException {
        // Insertar tres alumnos de prueba
        insertSampleStudent(conn, "Juan", "Pérez", "Gómez", "555-1234", 0, 20, 1); // Hombre, horario 1
        insertSampleStudent(conn, "María", "López", "Hernández", "555-5678", 1, 22, 0); // Mujer, horario 0
        insertSampleStudent(conn, "Carlos", "Sánchez", "Torres", "555-8765", 0, 21, 2); // Hombre, horario 2
    }

    private static void insertSampleStudent(Connection conn, String nombres, String apellidoPaterno, String apellidoMaterno, String telefono, int sexo, int edad, int horario) throws SQLException {
        String sql = "INSERT INTO students(id, nombres, apellido_paterno, apellido_materno, telefono, sexo, edad, estatura, peso, horario) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, generateUniqueStudentId(conn));
            pstmt.setString(2, nombres);
            pstmt.setString(3, apellidoPaterno);
            pstmt.setString(4, apellidoMaterno);
            pstmt.setString(5, telefono);
            pstmt.setInt(6, sexo);
            pstmt.setInt(7, edad);
            pstmt.setDouble(8, 1.75); // Estatura de ejemplo
            pstmt.setDouble(9, 70.5);  // Peso de ejemplo
            pstmt.setInt(10, horario);
            pstmt.executeUpdate();
            System.out.println("Estudiante " + nombres + " agregado a la base de datos.");
        }
    }

    public static String generateUniqueStudentId(Connection conn) throws SQLException {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();

        while (true) {
            StringBuilder idBuilder = new StringBuilder(5);
            for (int i = 0; i < 5; i++) {
                int index = random.nextInt(characters.length());
                idBuilder.append(characters.charAt(index));
            }
            String id = idBuilder.toString();

            // Verificar si el ID generado ya existe en la base de datos
            if (!isIdInDatabase(conn, id)) {
                return id;
            }
        }
    }

    private static boolean isIdInDatabase(Connection conn, String id) throws SQLException {
        String query = "SELECT COUNT(*) FROM students WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

}
