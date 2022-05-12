package me.kyllian.todolistjavafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import me.kyllian.todolistjavafx.StartApplication;
import me.kyllian.todolistjavafx.modele.BDD;
import me.kyllian.todolistjavafx.modele.List;
import me.kyllian.todolistjavafx.modele.User;

import java.sql.SQLException;

public class CreateListController {
    private final User currentUser;

    @FXML
    private Button createList;

    @FXML
    private TextField listNameField;

    @FXML
    private Label errorText;

    @FXML
    private Button retour;

    public CreateListController(User currentUser){
        this.currentUser = currentUser;
    }

    @FXML
    void onCreate(ActionEvent event) throws SQLException {
        if (listNameField.getText().isBlank()){
            errorText.setText("Veuillez rentrez un nom");
        }else{
            List newList = new List(listNameField.getText());
            newList.create(new BDD(), currentUser);
            StartApplication.changeScene("liste", new ListeController(currentUser));
        }
    }

    @FXML
    void onReturn(ActionEvent event) {
        StartApplication.changeScene("liste", new ListeController(currentUser));
    }

}