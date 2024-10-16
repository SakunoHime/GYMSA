package com.gymsa.systems.gymsa;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class UserProfileController {

    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField currentPasswordField;
    @FXML
    private PasswordField newPasswordField;

    private UserDAO userDAO; // Declarar la referencia




    private Usuario currentUser; // Usuario actual que está logueado


    // Método para establecer el usuario actual en el controlador
    public void setCurrentUser(Usuario user) {
        this.currentUser = user;
        userNameField.setText(currentUser.getNombre()); // Mostrar el nombre del usuario en el campo de texto
    }

    // Método para establecer el UserDAO en el controlador
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @FXML
    private void handleSaveChanges() {
        String newUsername = userNameField.getText();
        String currentPassword = currentPasswordField.getText().trim(); // Trimming spaces
        String newPassword = newPasswordField.getText();

        if (UserDAO.verifyPassword(currentUser, currentPassword)) {
            UserDAO.updateUsuario(currentUser.getId(), newUsername, newPassword);
            // Actualizar el objeto currentUser con el nuevo nombre y contraseña hasheada
            currentUser.setNombre(newUsername);
            currentUser.setPassword(PasswordUtils.hashPassword(newPassword)); // Actualizar el objeto con la nueva contraseña hasheada
            showCustomAlert("Cambios guardados con éxito!");
            handleExit();
        } else {
            showCustomAlert("Contraseña incorrecta. No se pueden guardar los cambios.");
        }

    }



    @FXML
    private void handleExit() {
        Stage stage = (Stage) userNameField.getScene().getWindow();
        stage.close(); // Cerrar la ventana de perfil
    }

    // Método para mostrar alertas al usuario
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
