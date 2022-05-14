package me.kyllian.todolistjavafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import me.kyllian.todolistjavafx.StartApplication;
import me.kyllian.todolistjavafx.modele.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserListController implements Initializable {
    private final User currentUser;
    private User selectedUser;
    private Task selectedTask;

    @FXML
    private Label connected;

    @FXML
    private Button inviteUser;

    @FXML
    private Button retour;

    public UserListController(User currentUser, Task selectedTask){
        this.currentUser = currentUser;
        this.selectedTask = selectedTask;
    }

    @FXML
    private TableView<User> tableUser;

    @FXML
    private TableColumn<User, String> userName;

    @FXML
    private TableColumn<User, String> userSurname;

    @FXML
    private TableColumn<User, String> userMail;



    @FXML
    void onInvite(ActionEvent event) throws SQLException {
        Gere gere = new Gere(selectedUser.getId_compte(), selectedTask.getIdTache());
        gere.demande(new BDD());
        tableUser.getItems().clear();
        tableUser.getItems().addAll(selectedUser.readAll(new BDD()));
        inviteUser.setDisable(true);
    }

    @FXML
    void onReturn(ActionEvent event) {
        StartApplication.changeScene("liste", new ListeController(currentUser));
    }

    @FXML
    void onRowClick(MouseEvent event){
        selectedUser =tableUser.getSelectionModel().getSelectedItem();
        if (selectedUser != null){
            inviteUser.setDisable(false);
        }else{
            inviteUser.setDisable(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connected.setText("Connecté en tant que "+currentUser.toString());
        userName.setCellValueFactory(new PropertyValueFactory<User, String>("nom"));
        userSurname.setCellValueFactory(new PropertyValueFactory<User, String>("prenom"));
        userMail.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        try {
            tableUser.getItems().addAll(currentUser.readAll(new BDD()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        inviteUser.setDisable(true);
    }
}
