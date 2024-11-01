package com.gymsa.systems.gymsa;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AddStudentController {

    @FXML
    private TextField idField;
    @FXML
    private TextField nombresField;
    @FXML
    private TextField apellidoPaternoField;
    @FXML
    private TextField apellidoMaternoField;
    @FXML
    private TextField telefonoField;
    @FXML
    private ComboBox<String> sexoComboBox;
    @FXML
    private TextField edadField;
    @FXML
    private TextField estaturaField;
    @FXML
    private TextField pesoField;
    @FXML
    private ComboBox<String> horarioComboBox;

    private StudentDAO studentDAO = new StudentDAO();

    private StudentsViewController studentsViewController; // Referencia al controlador principal

    // Método para establecer el controlador de la lista de estudiantes
    public void setStudentsViewController(StudentsViewController controller) {
        this.studentsViewController = controller;
    }

    // Mapas para manejar las opciones de los ComboBox
    private Map<String, Integer> sexoMap = new HashMap<>();
    private Map<String, Integer> horarioMap = new HashMap<>();

    @FXML
    public void initialize() {

        try (Connection conn = StudentDatabaseInitializer.getConnection()) {
            String uniqueId = StudentDatabaseInitializer.generateUniqueStudentId(conn);
            idField.setText(uniqueId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        idField.setEditable(false);
        // Inicializar los mapas
        sexoMap.put("Masculino", 0);
        sexoMap.put("Femenino", 1);

        horarioMap.put("7:00 - 8:00", 0);
        horarioMap.put("19:00 - 20:00", 1);
        horarioMap.put("8:00 - 9:00", 2);

        // Llenar los ComboBox
        sexoComboBox.setItems(FXCollections.observableArrayList(sexoMap.keySet()));
        horarioComboBox.setItems(FXCollections.observableArrayList(horarioMap.keySet()));

        // Personalizar solo el texto de la opción seleccionada
        sexoComboBox.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    setTextFill(Color.WHITE); // Forzar el texto en blanco solo para la selección
                }
            }
        });

        horarioComboBox.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    setTextFill(Color.WHITE); // Forzar el texto en blanco solo para la selección
                }
            }
        });
    }


    @FXML
    private void handleAddStudent() {
        String id = idField.getText();
        String nombres = nombresField.getText();
        String apellidoPaterno = apellidoPaternoField.getText();
        String apellidoMaterno = apellidoMaternoField.getText();
        String telefono = telefonoField.getText();

        // Obtener el índice del ComboBox
        int sexo = sexoMap.get(sexoComboBox.getSelectionModel().getSelectedItem()); // 0 o 1
        int edad = Integer.parseInt(edadField.getText());
        double estatura = Double.parseDouble(estaturaField.getText());
        double peso = Double.parseDouble(pesoField.getText());
        int horario = horarioMap.get(horarioComboBox.getSelectionModel().getSelectedItem()); // 1, 2 o 3

        Student newStudent = new Student(id, nombres, apellidoPaterno, apellidoMaterno, telefono, sexo, edad, estatura, peso, horario);
        studentDAO.createStudent(newStudent);

        if (studentsViewController != null) {
            studentsViewController.refresh();
        }

        // Cerrar la ventana
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) idField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }
}
