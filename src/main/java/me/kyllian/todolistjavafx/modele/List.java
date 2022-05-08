package me.kyllian.todolistjavafx.modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class List {
    private int idListe;
    private String titre;
    private int tacheTotal;

    public List(int idListe, String titre, int tacheTotal){
        this.idListe = idListe;
        this.titre = titre;
        this.tacheTotal = tacheTotal;
    };

    public List(String titre){
        this.titre = titre;
    }

    public ArrayList<List> readAll(BDD bdd) throws SQLException{
        ArrayList<List> mesListes = new ArrayList<>();
        PreparedStatement maRequete = bdd.getConnection().prepareStatement("SELECT * FROM infoliste");
        ResultSet maReponse = maRequete.executeQuery();
        while (maReponse.next()){
            List maListe = new List(maReponse.getInt("id_liste"),maReponse.getString("titre"), maReponse.getInt("compteTache"));
            mesListes.add(maListe);
        }
        return mesListes;
    }

    public ArrayList<List> specificRead(User currentUser,BDD bdd) throws SQLException{
        ArrayList<List> mesListes = new ArrayList<>();
        PreparedStatement maRequete = bdd.getConnection().prepareStatement("SELECT titre,COUNT(tache.ref_liste) as tacheTotal FROM infoliste, gere, tache WHERE gere.ref_compte = ? AND id_tache = gere.ref_tache AND id_liste = tache.ref_liste");
        maRequete.setInt(1, currentUser.getId_compte());
        ResultSet maReponse = maRequete.executeQuery();
        while (maReponse.next()){
            List maListe = new List(maReponse.getInt("id_liste"),maReponse.getString("titre"), maReponse.getInt("compteTache"));
            mesListes.add(maListe);
        }
        return mesListes;
    }

    public void delete(BDD bdd) throws SQLException {
        PreparedStatement maRequete = bdd.getConnection().prepareStatement("DELETE FROM liste WHERE id_liste=?");
        maRequete.setInt(1,this.idListe);
        maRequete.executeUpdate();
    }

    public void create(BDD bdd) throws SQLException{
        PreparedStatement maRequete = bdd.getConnection().prepareStatement("INSERT INTO liste(titre) VALUES (?)");
        maRequete.setString(1, this.titre);
        maRequete.executeUpdate();
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getTacheTotal() {
        return tacheTotal;
    }

    public void setTacheTotal(int tacheTotal) {
        this.tacheTotal = tacheTotal;
    }

    public int getIdListe() {
        return idListe;
    }

    public void setIdListe(int idListe) {
        this.idListe = idListe;
    }
}
