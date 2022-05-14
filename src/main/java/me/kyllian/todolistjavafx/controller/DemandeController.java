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

public class DemandeController implements Initializable {
    private final User currentUser;
    private Task selectedTask;

    @FXML
    private Label connected;

    @FXML
    private Button acceptTask;

    @FXML
    private Button refuseTask;

    @FXML
    private Button retour;

    public DemandeController(User currentUser){
        this.currentUser = currentUser;
    }

    @FXML
    private TableView<Task> tableTask;

    @FXML
    private TableColumn<Task, String> listName;

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
    void onAccept(ActionEvent event) throws SQLException {
        Gere gere = new Gere(currentUser.getId_compte(), selectedTask.getIdTache());
        gere.accept(new BDD());
        tableTask.getItems().clear();
        tableTask.getItems().addAll(selectedTask.readGere(new BDD(), currentUser));
        acceptTask.setDisable(true);
        refuseTask.setDisable(true);
    }

    @FXML
    void onRefuse(ActionEvent event) throws SQLException {
        Gere gere = new Gere(currentUser.getId_compte(), selectedTask.getIdTache());
        gere.refus(new BDD());
        tableTask.getItems().clear();
        tableTask.getItems().addAll(selectedTask.readGere(new BDD(), currentUser));
        acceptTask.setDisable(true);
        refuseTask.setDisable(true);
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
        System.out.println(selectedTask.getIdTache());
        System.out.println(selectedTask.getRef_liste());
        System.out.println(selectedTask.toString());
        if (selectedTask != null){
            acceptTask.setDisable(false);
            refuseTask.setDisable(false);
        }else{
            acceptTask.setDisable(true);
            refuseTask.setDisable(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connected.setText("Connecté en tant que "+currentUser.toString());
        listName.setCellValueFactory(new PropertyValueFactory<Task, String>("listName"));
        taskDesciption.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
        taskDifficulty.setCellValueFactory(new PropertyValueFactory<Task, String>("difficulte"));
        taskEtat.setCellValueFactory(new PropertyValueFactory<Task, String>("etat"));
        taskName.setCellValueFactory(new PropertyValueFactory<Task, String>("libelle"));
        taskType.setCellValueFactory(new PropertyValueFactory<Task, String>("type"));
        dateButoir.setCellValueFactory(new PropertyValueFactory<Task, String>("dateButoir"));
        dateDebut.setCellValueFactory(new PropertyValueFactory<Task, String>("dateDebut"));
        dateFin.setCellValueFactory(new PropertyValueFactory<Task, String>("dateFin"));
        Task task = new Task(0);
        try {
            tableTask.getItems().addAll(task.readGere(new BDD(), currentUser));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        acceptTask.setDisable(true);
        refuseTask.setDisable(true);
    }
}
