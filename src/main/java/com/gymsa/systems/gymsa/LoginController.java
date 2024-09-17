package com.gymsa.systems.gymsa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button minimizeButton;

    @FXML
    private Button closeButton;

    @FXML
    private Button moveButton;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Obtener la contraseña hasheada de la base de datos
        String hashedPassword = UserDAO.getPasswordHashByUsername(username);

        // Verificar si la contraseña ingresada coincide con la contraseña hasheada
        if (hashedPassword != null && PasswordUtils.checkPassword(password, hashedPassword)) {
            // Si las credenciales son correctas, abre el menú principal
            openMainMenu();
        } else {
            // Mostrar un mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de inicio de sesión");
            alert.setHeaderText(null);
            alert.setContentText("Usuario o contraseña incorrectos.");
            alert.showAndWait();
        }
    }

    private void openMainMenu() {
        // Aquí puedes cambiar la escena o abrir una nueva ventana para el menú principal
        // Carga el archivo FXML del menú principal y cámbialo en el Stage actual.
    }



    @FXML
    private void handleMinimize(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true); // Minimizar ventana
    }

    @FXML
    private void handleClose(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close(); // Cerrar ventana
    }

    @FXML
    private void startDrag(MouseEvent event) {
        // Guarda las coordenadas iniciales del mouse
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void dragWindow(MouseEvent event) {
        // Calcula el nuevo lugar y mueve la ventana
        Stage stage = (Stage) moveButton.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }


}
