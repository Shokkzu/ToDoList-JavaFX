package me.kyllian.todolistjavafx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ConnexionController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}