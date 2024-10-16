package com.gymsa.systems.gymsa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

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

    private static Usuario currentUser;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Obtener el usuario de la base de datos
        Usuario usuario = UserDAO.getUsuarioByUsername(username);

        if (usuario != null && BCrypt.checkpw(password, usuario.getPassword())) {
            // Credenciales correctas, almacenar el usuario logueado y abrir el menú principal
            currentUser = usuario;
            openMainMenu();
        } else {
            // Mostrar un mensaje de error si las credenciales no son válidas
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de inicio de sesión");
            alert.setHeaderText(null);
            alert.setContentText("Usuario o contraseña incorrectos.");
            alert.showAndWait();
        }
    }


    private void openMainMenu() {
        try {
            // Cargar la ventana del menú principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Parent root = loader.load();

            // Configurar la nueva escena
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));

            // Cerrar la ventana actual (login)
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.close();

            // Mostrar la ventana del menú principal
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Usuario getCurrentUser() {
        return currentUser;
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
