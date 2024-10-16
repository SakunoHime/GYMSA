package com.gymsa.systems.gymsa;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HomeViewController {
    @FXML
    private Label welcomeLabel;

    @FXML
    private Label dateTimeLabel;

    private String userName;

    @FXML
    public void initialize() {
        // Inicializar el reloj en tiempo real
        startClock();
    }

    // Método para establecer el nombre del usuario en la pantalla de inicio
    public void setUserName(String userName) {
        this.userName = userName;
        // Configurar el mensaje de bienvenida
        welcomeLabel.setText("¡Bienvenido, " + userName + "!");
        // Aplicar estilo de texto dinámico
        welcomeLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: white;");  // Cambia el tamaño y color
    }

    private void startClock() {
        // Actualizar el reloj cada segundo
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateClock()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateClock() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        dateTimeLabel.setText(now.format(formatter));
        // Aplicar estilo de texto dinámico para la fecha y hora
        dateTimeLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
    }
}
