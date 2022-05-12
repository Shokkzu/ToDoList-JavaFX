package me.kyllian.todolistjavafx.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import me.kyllian.todolistjavafx.StartApplication;
import me.kyllian.todolistjavafx.modele.BDD;
import me.kyllian.todolistjavafx.modele.List;
import me.kyllian.todolistjavafx.modele.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ListeController implements Initializable {
    private final User currentUser;
    private List selectedList;

    @FXML
    private Button checkList;

    @FXML
    private Label connected;

    @FXML
    private Button createList;

    @FXML
    private Button deleteList;

    @FXML
    private Button disconnect;

    @FXML
    private TableColumn<List, String> listName;

    @FXML
    private TableColumn<List, Integer> listTaskCount;

    @FXML
    private TableView<List> tableList;

    public ListeController(User currentUser){
        this.currentUser = currentUser;
    }

    @FXML
    void onCheck(ActionEvent event) {
        StartApplication.changeScene("insideliste", new InsideListeController(currentUser,selectedList));
    }

    @FXML
    void onCreate(ActionEvent event) {
        StartApplication.changeScene("createList", new CreateListController(currentUser));
    }

    @FXML
    void onDelete(ActionEvent event) throws SQLException {
        new List(selectedList.getIdListe(), selectedList.getTitre(), selectedList.getTacheTotal()).delete(new BDD());
        tableList.getItems().clear();
        tableList.getItems().addAll(selectedList.readAll(new BDD()));
        checkList.setDisable(true);
        deleteList.setDisable(true);
    }

    @FXML
    void onRowClick(MouseEvent event){
        selectedList =tableList.getSelectionModel().getSelectedItem();
        if (selectedList != null){
            checkList.setDisable(false);
            deleteList.setDisable(false);
        }else{
            checkList.setDisable(true);
            deleteList.setDisable(true);
        }
    }

    @FXML
    void onDisconnect(ActionEvent event) {
        StartApplication.changeScene("connexion");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connected.setText("Connecté en tant que "+currentUser.toString());
        listName.setCellValueFactory(new PropertyValueFactory<List, String>("titre"));
        listTaskCount.setCellValueFactory(new PropertyValueFactory<List, Integer>("tacheTotal"));
        try {
            tableList.getItems().addAll(new List(0,"",0).specificRead(currentUser, new BDD()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        checkList.setDisable(true);
        deleteList.setDisable(true);
    }
}
