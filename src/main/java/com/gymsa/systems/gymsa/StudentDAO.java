package com.gymsa.systems.gymsa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final String DB_URL = "jdbc:sqlite:mydatabase.db";

    // Crear un nuevo alumno
    public void createStudent(Student student) {
        String sql = "INSERT INTO students(id, nombres, apellido_paterno, apellido_materno, telefono, sexo, edad, estatura, peso, horario) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getId());
            pstmt.setString(2, student.getNombres());
            pstmt.setString(3, student.getApellidoPaterno());
            pstmt.setString(4, student.getApellidoMaterno());
            pstmt.setString(5, student.getTelefono());
            pstmt.setInt(6, student.getSexo());
            pstmt.setInt(7, student.getEdad());
            pstmt.setDouble(8, student.getEstatura());
            pstmt.setDouble(9, student.getPeso());
            pstmt.setInt(10, student.getHorario());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al crear el alumno: " + e.getMessage());
        }
    }

    // Leer un alumno por ID
    public Student readStudent(String id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        Student student = null;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                student = new Student();
                student.setId(rs.getString("id"));
                student.setNombres(rs.getString("nombres"));
                student.setApellidoPaterno(rs.getString("apellido_paterno"));
                student.setApellidoMaterno(rs.getString("apellido_materno"));
                student.setTelefono(rs.getString("telefono"));
                student.setSexo(rs.getInt("sexo"));
                student.setEdad(rs.getInt("edad"));
                student.setEstatura(rs.getDouble("estatura"));
                student.setPeso(rs.getDouble("peso"));
                student.setHorario(rs.getInt("horario"));
            }
        } catch (SQLException e) {
            System.out.println("Error al leer el alumno: " + e.getMessage());
        }

        return student;
    }

    // Leer todos los alumnos
    public List<Student> readAllStudents() {
        String sql = "SELECT * FROM students";
        List<Student> students = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getString("id"));
                student.setNombres(rs.getString("nombres"));
                student.setApellidoPaterno(rs.getString("apellido_paterno"));
                student.setApellidoMaterno(rs.getString("apellido_materno"));
                student.setTelefono(rs.getString("telefono"));
                student.setSexo(rs.getInt("sexo"));
                student.setEdad(rs.getInt("edad"));
                student.setEstatura(rs.getDouble("estatura"));
                student.setPeso(rs.getDouble("peso"));
                student.setHorario(rs.getInt("horario"));
                students.add(student);
            }
        } catch (SQLException e) {
            System.out.println("Error al leer todos los alumnos: " + e.getMessage());
        }

        return students;
    }

    // Actualizar un alumno
    public void updateStudent(Student student) {
        String sql = "UPDATE students SET nombres = ?, apellido_paterno = ?, apellido_materno = ?, telefono = ?, sexo = ?, edad = ?, estatura = ?, peso = ?, horario = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getNombres());
            pstmt.setString(2, student.getApellidoPaterno());
            pstmt.setString(3, student.getApellidoMaterno());
            pstmt.setString(4, student.getTelefono());
            pstmt.setInt(5, student.getSexo());
            pstmt.setInt(6, student.getEdad());
            pstmt.setDouble(7, student.getEstatura());
            pstmt.setDouble(8, student.getPeso());
            pstmt.setInt(9, student.getHorario());
            pstmt.setString(10, student.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar el alumno: " + e.getMessage());
        }
    }

    // Eliminar un alumno
    public void deleteStudent(String id) {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar el alumno: " + e.getMessage());
        }
    }

    // Leer alumnos por horario
    public List<Student> getStudentsByHorario(int horario) {
        String sql = "SELECT * FROM students WHERE horario = ?";
        List<Student> students = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, horario);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getString("id"));
                student.setNombres(rs.getString("nombres"));
                student.setApellidoPaterno(rs.getString("apellido_paterno"));
                student.setApellidoMaterno(rs.getString("apellido_materno"));
                student.setTelefono(rs.getString("telefono"));
                student.setSexo(rs.getInt("sexo"));
                student.setEdad(rs.getInt("edad"));
                student.setEstatura(rs.getDouble("estatura"));
                student.setPeso(rs.getDouble("peso"));
                student.setHorario(rs.getInt("horario"));
                students.add(student);
            }
        } catch (SQLException e) {
            System.out.println("Error al leer alumnos por horario: " + e.getMessage());
        }

        return students;
    }

}
