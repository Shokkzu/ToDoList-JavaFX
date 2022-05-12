package me.kyllian.todolistjavafx.modele;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Etat {
    private int id_etat;
    private String libelle;

    public Etat(int id_etat, String libelle){
        this.id_etat = id_etat;
        this.libelle = libelle;
    }

    public ObservableList<Etat> read(BDD bdd) throws SQLException {
        ObservableList<Etat> mesEtats = FXCollections.observableArrayList();
        PreparedStatement maRequete = bdd.getConnection().prepareStatement("SELECT * FROM etat");
        ResultSet maReponse = maRequete.executeQuery();
        while (maReponse.next()){
            mesEtats.add(new Etat(maReponse.getInt("id_etat"),maReponse.getString("etat")));
        }
        return mesEtats;
    }

    public String toString(){
        return getEtat();
    }

    public String getEtat() {
        return libelle;
    }

    public int getId_type() {
        return id_etat;
    }
}
