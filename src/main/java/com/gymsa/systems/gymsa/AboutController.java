package com.gymsa.systems.gymsa;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class AboutController {

    @FXML
    private Button closeButton;

    @FXML
    private void handleClose(ActionEvent event) {
        // Cerrar la ventana de Acerca de
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
