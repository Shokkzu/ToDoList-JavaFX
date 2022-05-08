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
import me.kyllian.todolistjavafx.modele.BDD;
import me.kyllian.todolistjavafx.modele.List;
import me.kyllian.todolistjavafx.modele.Task;
import me.kyllian.todolistjavafx.modele.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InsideListeController implements Initializable {
    private User currentUser;
    private List checkedList;
    private Task selectedTask;

    @FXML
    private Label connected;

    @FXML
    private Button createList;

    @FXML
    private Button deleteTask;

    @FXML
    private Button modifyTask;

    @FXML
    private Button retour;

    public InsideListeController(User currentUser, List checkedList){
        this.currentUser = currentUser;
        this.checkedList = checkedList;
    }

    @FXML
    private TableView<Task> tableTask;

    @FXML
    private TableColumn<Task, String> taskDesciption;

    @FXML
    private TableColumn<Task, String> taskDifficulty;

    @FXML
    private TableColumn<Task, String> taskEtat;

    @FXML
    private TableColumn<Task, String> taskName;

    @FXML
    private TableColumn<Task, String> taskType;

    @FXML
    private TableColumn<Task, String> dateButoir;

    @FXML
    private TableColumn<Task, String> dateDebut;

    @FXML
    private TableColumn<Task, String> dateFin;

    @FXML
    private Label titreListe;

    @FXML
    void onCreate(ActionEvent event) {

    }

    @FXML
    void onDelete(ActionEvent event) throws SQLException {
        new Task(selectedTask.getIdTache(), selectedTask.getLibelle()).delete(new BDD());
        tableTask.getItems().clear();
        tableTask.getItems().addAll(selectedTask.readAll(new BDD()));
        modifyTask.setDisable(true);
        deleteTask.setDisable(true);
    }

    @FXML
    void onModify(ActionEvent event) {

    }

    @FXML
    void onReturn(ActionEvent event) {
        StartApplication.changeScene("liste", new ListeController(currentUser));
    }

    @FXML
    void onRowClick(MouseEvent event){
        selectedTask =tableTask.getSelectionModel().getSelectedItem();
        if (selectedTask != null){
            modifyTask.setDisable(false);
            deleteTask.setDisable(false);
        }else{
            modifyTask.setDisable(true);
            deleteTask.setDisable(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Task task = new Task(checkedList.getIdListe());
        taskDesciption.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
        taskDifficulty.setCellValueFactory(new PropertyValueFactory<Task, String>("difficulte"));
        taskEtat.setCellValueFactory(new PropertyValueFactory<Task, String>("etat"));
        taskName.setCellValueFactory(new PropertyValueFactory<Task, String>("libelle"));
        taskType.setCellValueFactory(new PropertyValueFactory<Task, String>("type"));
        dateButoir.setCellValueFactory(new PropertyValueFactory<Task, String>("dateButoir"));
        dateDebut.setCellValueFactory(new PropertyValueFactory<Task, String>("dateDebut"));
        dateFin.setCellValueFactory(new PropertyValueFactory<Task, String>("dateFin"));
        try {
            tableTask.getItems().addAll(task.specificRead(new BDD()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        modifyTask.setDisable(true);
        deleteTask.setDisable(true);
    }
}
