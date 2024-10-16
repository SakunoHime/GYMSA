package com.gymsa.systems.gymsa;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ResetConfirmationController {

    @FXML
    private PasswordField adminPasswordField;
    @FXML
    private TextField confirmationField;

    private UserDAO userDAO = new UserDAO(); // Usar el UserDAO para verificar el admin
    private static final int ADMIN_ID = 1;

    @FXML
    private void handleReset() {
        String adminPassword = adminPasswordField.getText().trim();
        String confirmationText = confirmationField.getText().trim();

        // Verificar si se escribió la palabra 'CONFIRMAR'
        if (!"CONFIRMAR".equals(confirmationText)) {
            showCustomAlert("Debes escribir 'CONFIRMAR' para proceder.");
            return;
        }

        // Obtener usuario administrador (ID 1)
        Usuario adminUser = userDAO.getUsuarioById(ADMIN_ID);

        // Verificar si el usuario existe y si es el administrador
        if (adminUser == null || adminUser.getId() != ADMIN_ID) {
            showCustomAlert("El usuario no es el administrador.");
            return;
        }

        // Verificar si la contraseña es correcta
        if (!PasswordUtils.checkPassword(adminPassword, adminUser.getPassword())) {
            showCustomAlert("La contraseña del administrador es incorrecta.");
            return;
        }

        // Proceder a reiniciar la configuración
        resetApplicationData();
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) adminPasswordField.getScene().getWindow();
        stage.close(); // Cerrar la ventana
    }

    // Método que realiza el reinicio de los datos
    private void resetApplicationData() {
        showCustomAlert("La aplicación se reiniciará ahora para aplicar los cambios.");

        // Cerrar la aplicación después de reiniciar los datos
        System.exit(0); // Esto simula el cierre de la aplicación para reiniciar
    }

    // Método para mostrar alertas personalizadas
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
