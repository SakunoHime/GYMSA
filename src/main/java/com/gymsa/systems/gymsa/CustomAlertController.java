package com.gymsa.systems.gymsa;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CustomAlertController {

    @FXML
    private Label messageLabel;

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) messageLabel.getScene().getWindow();
        stage.close();
    }
}
