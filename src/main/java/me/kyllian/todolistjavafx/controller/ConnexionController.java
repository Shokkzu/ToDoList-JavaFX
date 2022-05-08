package me.kyllian.todolistjavafx.controller;

import javafx.scene.control.Label;
import me.kyllian.todolistjavafx.StartApplication;
import me.kyllian.todolistjavafx.modele.User;
import me.kyllian.todolistjavafx.modele.BDD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnexionController {

    @FXML
    private TextField mailField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label errorText;

    @FXML
    void onConnexionButtonClick(ActionEvent event) throws SQLException {
        BDD bdd = new BDD();
        User user = new User(mailField.getText(),passwordField.getText());
        ResultSet monResultat = user.connexion(bdd);
        if (monResultat.next()){
            errorText.setText("");
            User currentUser = new User(monResultat.getString("nom"),monResultat.getString("prenom"),monResultat.getString("email"),monResultat.getString("mdp"));
            StartApplication.changeScene("/me/kyllian/todolistjavafx/liste", new ListeController(currentUser));
        }else{
            errorText.setText("Mauvais Mail/Mot de passe");
        }
    }

    @FXML
    void onInscriptionButtonClick(ActionEvent event) {
        StartApplication.changeScene("inscription", new InscriptionController());
    }
}
