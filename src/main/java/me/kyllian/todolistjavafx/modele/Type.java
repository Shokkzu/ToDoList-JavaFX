package me.kyllian.todolistjavafx.modele;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Type {
    private int id_type;
    private String libelle;
    private int ref_type;

    public Type(int id_type, String libelle){
        this.id_type = id_type;
        this.libelle = libelle;
    }

    public ObservableList<Type> read(BDD bdd) throws SQLException {
        ObservableList<Type> mesTypes = FXCollections.observableArrayList();
        PreparedStatement maRequete = bdd.getConnection().prepareStatement("SELECT * FROM type");
        ResultSet maReponse = maRequete.executeQuery();
        while (maReponse.next()){
            mesTypes.add(new Type(maReponse.getInt("id_type"),maReponse.getString("libelle")));
        }
        return mesTypes;
    }

    public String toString(){
        return getLibelle();
    }

    public String getLibelle() {
        return libelle;
    }

    public int getId_type() {
        return id_type;
    }
}
