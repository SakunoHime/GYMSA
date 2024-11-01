package com.gymsa.systems.gymsa;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class EditUserController {

    @FXML
    private TextField usernameField;

    @FXML
    private CheckBox adminCheckBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private Usuario selectedUser;
    private int loggedInUserId;

    public void initialize(Usuario user, int loggedInUserId) {
        this.selectedUser = user;
        this.loggedInUserId = loggedInUserId;

        usernameField.setText(user.getNombre());
        adminCheckBox.setSelected(user.isAdmin());

        // Permitir modificar el estado de administrador solo para el usuario con ID 1
        adminCheckBox.setDisable(loggedInUserId != 1);
    }

    @FXML
    private void handleSave() {
        String newUsername = usernameField.getText().trim();

        // Validar el nuevo nombre de usuario
        if (newUsername.isEmpty()) {
            showCustomAlert("Por favor, ingresa un nombre de usuario.");
            return;
        }

        // Actualizar el nombre y el estado de administrador
        selectedUser.setNombre(newUsername);
        selectedUser.setEsAdministrador(adminCheckBox.isSelected());

        // Guardar los cambios en la base de datos
        UserDAO.updateUsuario(selectedUser);

        // Mostrar mensaje de éxito y cerrar la ventana
        showCustomAlert("Usuario modificado correctamente.");
        closeWindow();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
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
}
