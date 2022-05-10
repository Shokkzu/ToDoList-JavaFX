package me.kyllian.todolistjavafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import me.kyllian.todolistjavafx.StartApplication;
import me.kyllian.todolistjavafx.modele.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateTaskController implements Initializable {
    private List checkedList;
    private User currentUser;
    @FXML
    private TextField diffField;

    @FXML
    private Button createList;

    @FXML
    private TextField dbutField;

    @FXML
    private TextField ddebField;

    @FXML
    private TextField descField;

    @FXML
    private TextField dfinField;

    @FXML
    private Label errorText;

    @FXML
    private TextField nameField;

    @FXML
    private Button retour;

    public CreateTaskController(List checkedList, User currentUser){
        this.checkedList = checkedList;
        this.currentUser = currentUser;
    }

    @FXML
    void onCreative(ActionEvent event){
        if (nameField.getText().isBlank()||nameField.getText().isBlank()||descField.getText().isBlank()||diffField.getText().isBlank()||ddebField.getText().isBlank()||dfinField.getText().isBlank()||dbutField.getText().isBlank()){
            errorText.setText("Veuillez remplir tous les champs");
        }else{
            Task newTask = new Task(nameField.getText(),descField.getText(),diffField.getText(),ddebField.getText(),dfinField.getText(),dbutField.getText(),checkedList.getIdListe());
            try {
                newTask.create(new BDD(), currentUser);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            StartApplication.changeScene("insideliste", new InsideListeController(currentUser, checkedList));
        }
    }

    @FXML
    void onReturn(ActionEvent event) {
        StartApplication.changeScene("liste", new ListeController(currentUser));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
