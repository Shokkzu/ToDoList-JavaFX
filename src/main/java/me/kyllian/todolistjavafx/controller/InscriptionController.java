package me.kyllian.todolistjavafx.controller;

import me.kyllian.todolistjavafx.modele.BDD;
import me.kyllian.todolistjavafx.modele.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import me.kyllian.todolistjavafx.StartApplication;

import java.sql.SQLException;

public class InscriptionController {

    @FXML
    private Label errorText;

    @FXML
    private TextField mail;

    @FXML
    private TextField nom;

    @FXML
    private TextField password;

    @FXML
    private TextField prenom;

    @FXML
    void onConnect(ActionEvent event) {
        StartApplication.changeScene("connexion");
    }

    @FXML
    void onInscription() throws SQLException {
        BDD bdd = new BDD();
        User user = new User(nom.getText(),prenom.getText(),mail.getText(),password.getText());
        user.inscription(bdd);
        StartApplication.changeScene("connexion");
    }
}