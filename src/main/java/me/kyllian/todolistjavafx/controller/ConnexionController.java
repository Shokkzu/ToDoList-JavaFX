package me.kyllian.todolistjavafx.controller;

import me.kyllian.todolistjavafx.BDD.BDD;
import me.kyllian.todolistjavafx.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ConnexionController {

    @FXML
    private TextField mailField;

    @FXML
    private TextField passwordField;

    @FXML
    void onConnexionButtonClick(ActionEvent event) throws SQLException {
        User user = new User(mailField.getText(),passwordField.getText());
    }

    @FXML
    void onInscriptionButtonClick(ActionEvent event) {
        //TODO Les méthodes changeScene et ajouter un changeScene vers la page d'inscription
    }
}
