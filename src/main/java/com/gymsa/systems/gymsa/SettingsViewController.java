package com.gymsa.systems.gymsa;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SettingsViewController {

    @FXML
    private Button manageUsersButton;

    @FXML
    private Button resetConfigButton;

    @FXML
    private Button userProfileButton; // Agregar botón para el perfil de usuario

    @FXML
    public void initialize() {
        // Verificar si el usuario es administrador
        Usuario currentUser = LoginController.getCurrentUser();
        if (currentUser.isAdmin()) {
            manageUsersButton.setDisable(false);
            resetConfigButton.setDisable(false);
        }
    }

    @FXML
    private void handleProfile() {
        openUserProfile(); // Llamar al método para abrir la ventana de perfil
    }

    // Método para abrir la ventana de perfil de usuario
    private void openUserProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserProfileView.fxml"));
            Parent userProfileView = loader.load();

            // Crear una nueva ventana
            Stage userProfileStage = new Stage();
            userProfileStage.setTitle("Perfil de Usuario");
            userProfileStage.initModality(Modality.APPLICATION_MODAL); // Desactivar la ventana principal mientras está abierta
            userProfileStage.initStyle(StageStyle.UNDECORATED);
            userProfileStage.setScene(new Scene(userProfileView));

            // Obtener el controlador de UserProfile y establecer el usuario actual
            UserProfileController userProfileController = loader.getController();
            Usuario currentUser = LoginController.getCurrentUser(); // Obtener el usuario actual
            userProfileController.setCurrentUser(currentUser); // Pasar el usuario al controlador
            userProfileController.setUserDAO(new UserDAO()); // Asegúrate de crear una instancia de UserDAO

            userProfileStage.showAndWait(); // Mostrar la ventana de perfil de usuario

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleManageUsers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserManagement.fxml"));
            Parent userManagementView = loader.load();

            // Crear una nueva ventana para la gestión de usuarios
            Stage userManagementStage = new Stage();
            userManagementStage.setTitle("Gestionar Usuarios");
            userManagementStage.initModality(Modality.APPLICATION_MODAL);
            userManagementStage.initStyle(StageStyle.UNDECORATED);
            userManagementStage.setScene(new Scene(userManagementView));

            userManagementStage.showAndWait(); // Mostrar la ventana

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleAbout() {
        try {
            // Cargar el archivo FXML de la ventana Acerca de
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AboutView.fxml"));
            Pane aboutView = loader.load();

            // Crear una nueva ventana para Acerca de
            Stage aboutStage = new Stage();
            aboutStage.setTitle("Acerca de");
            aboutStage.initModality(Modality.APPLICATION_MODAL); // Desactivar la ventana principal mientras está abierta
            aboutStage.initStyle(StageStyle.UNDECORATED); // Ventana sin bordes
            aboutStage.setScene(new Scene(aboutView));

            // Mostrar la ventana Acerca de
            aboutStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleResetConfig() {
        try {
            // Cargar el archivo FXML de la ventana de confirmación de reinicio
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ResetConfirmation.fxml"));
            Pane resetConfirmationView = loader.load();

            // Crear una nueva ventana para la confirmación
            Stage resetConfirmationStage = new Stage();
            resetConfirmationStage.setTitle("Confirmación de Reinicio");
            resetConfirmationStage.initModality(Modality.APPLICATION_MODAL); // Desactivar la ventana principal mientras está abierta
            resetConfirmationStage.initStyle(StageStyle.UNDECORATED); // Ventana sin bordes
            resetConfirmationStage.setScene(new Scene(resetConfirmationView));

            // Mostrar la ventana de confirmación
            resetConfirmationStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
