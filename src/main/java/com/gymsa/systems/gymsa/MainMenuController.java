package com.gymsa.systems.gymsa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private StackPane contentArea;
    private double xOffset = 0;
    private double yOffset = 0;

    // Inicializa la pantalla y verifica el usuario logueado
    @FXML
    public void initialize() {
        Usuario currentUser = LoginController.getCurrentUser();
        showHome();
    }

    @FXML
    private void showHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeView.fxml"));
            Parent homeView = loader.load();

            // Obtén el controlador de HomeView
            HomeViewController homeViewController = loader.getController();

            // Obtén el usuario actual y pásalo al controlador de HomeView
            Usuario currentUser = LoginController.getCurrentUser();
            homeViewController.setUserName(currentUser.getNombre()); // Suponiendo que tienes un método getNombre en Usuario

            // Cargar la vista
            contentArea.getChildren().setAll(homeView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void showStudents() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentsView.fxml"));
            Parent studentsView = loader.load();

            StudentsViewController studentsViewController = loader.getController();

            contentArea.getChildren().setAll(studentsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void showPayments() {
        loadView("PaymentsView.fxml");
    }

    @FXML
    public void showSettings() {
        loadView("SettingsView.fxml");
    }

    private void loadView(String fxmlFile) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(fxmlFile));
            contentArea.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }
}
