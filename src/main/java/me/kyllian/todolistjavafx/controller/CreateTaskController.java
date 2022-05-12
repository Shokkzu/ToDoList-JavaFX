package me.kyllian.todolistjavafx.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import me.kyllian.todolistjavafx.StartApplication;
import me.kyllian.todolistjavafx.modele.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class CreateTaskController implements Initializable {
    private final List checkedList;
    private final User currentUser;
    @FXML
    private TextField diffField;

    @FXML
    private Button createList;

    @FXML
    private DatePicker dbutField;

    @FXML
    private DatePicker ddebField;

    @FXML
    private TextField descField;

    @FXML
    private DatePicker dfinField;

    @FXML
    private Label errorText;

    @FXML
    private TextField nameField;

    @FXML
    private Button retour;

    @FXML
    private ComboBox<Type> typeField;

    @FXML
    private ComboBox<Etat> etatField;

    public CreateTaskController(List checkedList, User currentUser){
        this.checkedList = checkedList;
        this.currentUser = currentUser;
    }
    @FXML
    void onCreative(ActionEvent event){
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate != null){
                    return dateFormatter.format(localDate);
                }else{
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String s) {
                if (s != null && !s.isEmpty()){
                    return LocalDate.parse(s);
                }else {
                    return null;
                }
            }
        };
        ddebField.setConverter(converter);
        if (typeField.getSelectionModel().getSelectedItem()==null||etatField.getSelectionModel().getSelectedItem()==null||nameField.getText().isBlank()||nameField.getText().isBlank()||descField.getText().isBlank()||diffField.getText().isBlank()||ddebField.getValue().toString().isBlank()||dfinField.getValue().toString().isBlank()||dbutField.getValue().toString().isBlank()){
            errorText.setText("Veuillez remplir tous les champs");
        }else{
            Task newTask = new Task(nameField.getText(),descField.getText(),diffField.getText(),ddebField.getValue().toString(),dfinField.getValue().toString(),dbutField.getValue().toString(),typeField.getSelectionModel().getSelectedItem().getId_type(),etatField.getSelectionModel().getSelectedItem().getId_type(),checkedList.getIdListe());
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
        StartApplication.changeScene("insideliste", new InsideListeController(currentUser, checkedList));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Type type = new Type(0,"");
        Etat etat = new Etat(0,"");
        try {
            etatField.setItems(etat.read(new BDD()));
            typeField.setItems(type.read(new BDD()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
