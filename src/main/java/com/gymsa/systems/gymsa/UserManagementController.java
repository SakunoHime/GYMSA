package com.gymsa.systems.gymsa;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManagementController {

    @FXML
    private TableView<Usuario> userTableView;

    @FXML
    private TableColumn<Usuario, Integer> idColumn;

    @FXML
    private TableColumn<Usuario, String> nameColumn;

    @FXML
    private TableColumn<Usuario, String> isAdminColumn;

    @FXML
    private Button addUserButton;

    @FXML
    private Button editUserButton;

    @FXML
    private Button deleteUserButton;

    @FXML
    private Button exitButton;

    private static final String DB_URL = "jdbc:sqlite:mydatabase.db";

    private ObservableList<Usuario> userList; // Lista observable para los usuarios

    @FXML
    public void initialize() {
        // Inicializa la lista de usuarios
        userList = FXCollections.observableArrayList();

        // Configura las columnas de la tabla
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        isAdminColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isAdmin() ? "ACTIVADO" : "DESACTIVADO"));

        loadUserData(); // Carga los datos de los usuarios
        userTableView.setItems(userList); // Asigna la lista a la tabla
    }

    private void loadUserData() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                String query = "SELECT * FROM users";
                ResultSet rs = conn.createStatement().executeQuery(query);
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    boolean isAdmin = rs.getBoolean("is_admin");
                    userList.add(new Usuario(id, name, "", isAdmin)); // Se asume que el constructor Usuario acepta estos parámetros
                }
            }
        } catch (SQLException e) {
            showCustomAlert("Error al cargar los usuarios: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddUser() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddUser.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Agregar Usuario");
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED); // Mismo estilo que el resto de la aplicación
            stage.initModality(Modality.APPLICATION_MODAL); // Modal para bloquear la ventana principal
            stage.show();

            // Configura el controlador para que pueda actualizar la lista de usuarios
            AddUserController addUserController = loader.getController();
            addUserController.setUserManagementController(this); // Enlaza el controlador de gestión de usuarios

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshUserList(Usuario newUser) {
        userList.add(newUser); // Agrega el nuevo usuario a la lista observable
        userTableView.refresh(); // Actualiza la tabla
    }

    @FXML
    private void handleEditUser() {
        // Lógica para modificar usuario
        // Implementar según sea necesario
    }

    @FXML
    private void handleDeleteUser() {
        Usuario selectedUser = userTableView.getSelectionModel().getSelectedItem();

        // Verificar si hay un usuario seleccionado
        if (selectedUser == null) {
            showCustomAlert("Por favor, selecciona un usuario para eliminar.");
            return;
        }

        // Verificar si el usuario intenta eliminarse a sí mismo
        int loggedInUserId = getLoggedInUserId(); // Método hipotético para obtener el ID del usuario logueado
        if (selectedUser.getId() == loggedInUserId) {
            showCustomAlert("No puedes eliminarte a ti mismo.");
            return;
        }

        // Verificar las condiciones para la eliminación
        if (selectedUser.getId() == 1) {
            showCustomAlert("No se puede eliminar al usuario administrador (ID 1).");
            return;
        }

        // Comprobar si el usuario seleccionado es administrador
        if (selectedUser.isAdmin() && selectedUser.getId() != 1) {
            // Verificar si el usuario logueado es el administrador
            if (loggedInUserId != 1) {
                showCustomAlert("Solo el administrador puede eliminar a otros administradores.");
                return;
            }
        }

        // Eliminar el usuario
        UserDAO.deleteUsuario(selectedUser.getId());
        userList.remove(selectedUser); // Eliminar de la lista observable
        userTableView.refresh(); // Actualiza la tabla
        showCustomAlert("Usuario eliminado correctamente.");
    }



    @FXML
    private void handleExit() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    private void showCustomAlert(String message) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomAlert.fxml"));
            Pane pane = loader.load();

            CustomAlertController controller = loader.getController();
            controller.setMessage(message);

            Stage stage = new Stage();
            stage.setTitle("Mensaje");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(pane));
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getLoggedInUserId() {
        Usuario loggedUser = LoginController.getCurrentUser();
        return loggedUser != null ? loggedUser.getId() : -1;
    }
}
