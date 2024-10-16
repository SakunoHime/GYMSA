package com.gymsa.systems.gymsa;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AddUserController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox adminCheckBox;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    private UserManagementController userManagementController; // Controlador de gestión de usuarios

    @FXML
    private void handleAddUser() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Obtener el estado de administrador
        boolean isAdmin = adminCheckBox.isSelected();

        // Validar la entrada
        if (!username.isEmpty() && !password.isEmpty()) {
            // Comprobar si el nombre de usuario ya existe
            if (UserDAO.userExists(username)) { // Este método debe estar en UserDAO
                showCustomAlert("El nombre de usuario ya existe. Por favor, elige otro.");
                return;
            }

            // Obtener el siguiente ID disponible
            int nextId = UserDAO.getNextUserId();

            // Crear un nuevo objeto Usuario
            Usuario newUser = new Usuario(nextId, username, password, isAdmin);

            // Agregar el nuevo usuario usando el UserDAO
            UserDAO.addUsuario(newUser);
            showCustomAlert("Usuario agregado exitosamente.");

            // Notificar al controlador de gestión de usuarios para refrescar la lista
            if (userManagementController != null) {
                userManagementController.refreshUserList(newUser);
            }

            closeWindow();
        } else {
            showCustomAlert("Por favor, complete todos los campos.");
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) addButton.getScene().getWindow(); // Obtener la ventana actual
        stage.close(); // Cerrar la ventana
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

    // Método para establecer el controlador de gestión de usuarios
    public void setUserManagementController(UserManagementController controller) {
        this.userManagementController = controller;
    }
}
