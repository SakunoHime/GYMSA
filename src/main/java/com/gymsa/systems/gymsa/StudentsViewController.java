package com.gymsa.systems.gymsa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StudentsViewController {

    @FXML
    private ComboBox<String> horarioComboBox;
    @FXML
    private TableView<Student> studentsTableView;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Student, String> apPaternoColumn;
    @FXML
    private TableColumn<Student, String> apMaternoColumn;
    @FXML
    private TableColumn<Student, String> telefonoColumn;
    @FXML
    private TableColumn<Student, String> sexoColumn;
    @FXML
    private TableColumn<Student, Integer> edadColumn;
    @FXML
    private TableColumn<Student, Double> estaturaColumn;
    @FXML
    private TableColumn<Student, Double> pesoColumn;

    private ObservableList<Student> studentsList = FXCollections.observableArrayList();
    private Map<String, Integer> horarioMap = new HashMap<>();
    private StudentDAO studentDAO;  // Declaración de la instancia de StudentDAO

    @FXML
    public void initialize() {
        // Crear una instancia de StudentDAO
        studentDAO = new StudentDAO();  // Inicializa el StudentDAO

        // Configuración de las columnas de la tabla
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        apPaternoColumn.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        apMaternoColumn.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
        telefonoColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        sexoColumn.setCellValueFactory(cellData -> {
            int sexoValue = cellData.getValue().getSexo();
            String sexoString = (sexoValue == 0) ? "Hombre" : "Mujer";
            return new SimpleStringProperty(sexoString);
        });
        edadColumn.setCellValueFactory(new PropertyValueFactory<>("edad"));
        estaturaColumn.setCellValueFactory(new PropertyValueFactory<>("estatura"));
        pesoColumn.setCellValueFactory(new PropertyValueFactory<>("peso"));

        // Inicialización del HashMap de horarios
        horarioMap.put("7:00 - 8:00", 0);
        horarioMap.put("8:00 - 9:00", 1);
        horarioMap.put("19:00 - 20:00", 2);

        // Agregar elementos al ComboBox
        horarioComboBox.setItems(FXCollections.observableArrayList(horarioMap.keySet()));

        // Establecer el horario por defecto
        horarioComboBox.getSelectionModel().select(0); // Seleccionar "7:00 - 8:00"
        loadStudents();
    }

    private void loadStudents() {
        String selectedHorario = horarioComboBox.getSelectionModel().getSelectedItem(); // Obtener el horario seleccionado
        Integer index = horarioMap.get(selectedHorario); // Obtener el índice correspondiente del HashMap
        if (index != null) {
            // Usar la instancia de StudentDAO para cargar los estudiantes
            studentsList.setAll(studentDAO.getStudentsByHorario(index)); // Cargar estudiantes por horario
            studentsTableView.setItems(studentsList);
        }
    }

    @FXML
    private void handleHorarioSelection() {
        loadStudents();
    }

    @FXML
    private void handleAddStudent() {
        try {
            // Cargar la ventana de agregar/editar estudiante
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddStudentView.fxml")); // Ruta correcta
            AnchorPane root = loader.load();

            // Obtener el controlador de la vista de agregar estudiante
            AddStudentController addStudentController = loader.getController();
            addStudentController.setStudentsViewController(this); // Pasar la referencia del controlador

            // Crear una nueva ventana
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED); // Estilo sin bordes
            stage.setTitle("Agregar/Editar Estudiante");

            // Mostrar la ventana
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Manejar excepciones adecuadamente en producción
        }
    }



    @FXML
    private void handleEditStudent() {
        // Implementar la lógica para editar un estudiante
    }

    @FXML
    private void handleDeleteStudent() {
        // Implementar la lógica para eliminar un estudiante
    }

    public void refresh() {
        loadStudents(); // Recarga los estudiantes
    }

}
