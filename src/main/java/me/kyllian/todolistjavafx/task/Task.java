package me.kyllian.todolistjavafx.task;

import me.kyllian.todolistjavafx.BDD.BDD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Task {
    private int id_tache = 0;
    private String libelle;
    private String description;
    private int difficulte;
    private String date_debut;
    private String date_fin;
    private String date_butoir;
    private int ref_type;
    private int ref_etat;
    private int ref_compte;
    private int ref_tache;

    public Task(String libelle, String description, int difficulte, String date_debut, String date_fin, String date_butoir,BDD BDD) throws SQLException {
        setLibelle(libelle);
        setDate_butoir(date_butoir);
        setDate_debut(date_debut);
        setDate_fin(date_fin);
        setDescription(description);
        setDifficulte(difficulte);
        Ajout_tache(BDD);

    }
    public Task(int ref_compte){
        setRef_compte(ref_compte);
    }

    public Task(int ref_tache,int ref_compte){
        setRef_compte(ref_compte);
        setRef_tache(ref_tache);
    }
    public void Ajout_tache(BDD BDD) throws SQLException {
        PreparedStatement req = BDD.getConnection().prepareStatement("INSERT INTO tache(libelle,description, difficulte, date_debut, date_fin,date_butoir) VALUES (?,?,?,?,?,?)");
        req.setString(1,libelle);
        req.setString(2,description);
        req.setInt(3,difficulte);
        req.setString(4,date_debut);
        req.setString(5,date_fin);
        req.setString(6,date_butoir);
        req.executeUpdate();
    }

    public void Affiche(BDD BDD) throws SQLException {
        PreparedStatement req = BDD.getConnection().prepareStatement("SELECT * FROM tache INNER JOIN gere ON tache.id_tache = gere.ref_tache INNER JOIN compte ON gere.ref_compte = compte.id_compte WHERE ref_compte= ?");
        req.setInt(1,ref_compte);
        ResultSet monResultat = req.executeQuery();
        while(monResultat.next()){
            System.out.println("\n--------App Todo-List--------");
            System.out.println("1.libelle : "+monResultat.getString("libelle"));
            System.out.println("2.description : "+monResultat.getString("description"));
            System.out.println("3.difficulte : "+ monResultat.getInt("difficulte"));
            System.out.println("4.date_debut : "+monResultat.getString("date_debut"));
            System.out.println("5.date_fin : "+monResultat.getString("date_fin"));
            System.out.println("6.date_butoir : "+monResultat.getString("date_butoir"));
            setId_tache(monResultat.getInt(1));
            setLibelle(monResultat.getString(2));
            setDescription(monResultat.getString(3));
            setDifficulte(monResultat.getInt(4));
            setDate_debut(monResultat.getString(5));
            setDate_fin(monResultat.getString(6));
            setDate_butoir(monResultat.getString(7));
        }
    }

    public void Affiche_All_Tache(BDD BDD) throws SQLException {
        PreparedStatement req = BDD.getConnection().prepareStatement("SELECT * FROM tache LEFT JOIN gere ON tache.id_tache = gere.ref_tache LEFT JOIN compte ON gere.ref_compte = compte.id_compte");
        ResultSet monResultat = req.executeQuery();

        while(monResultat.next()){
            System.out.println("\n--------App Todo-List--------");
            System.out.println("0.Tache numero : "+ monResultat.getInt("id_tache"));
            System.out.println("1.libelle : "+monResultat.getString("libelle"));
            System.out.println("2.description : "+monResultat.getString("description"));
            System.out.println("3.difficulte : "+ monResultat.getInt("difficulte"));
            System.out.println("4.date_debut : "+monResultat.getString("date_debut"));
            System.out.println("5.date_fin : "+monResultat.getString("date_fin"));
            System.out.println("6.date_butoir : "+monResultat.getString("date_butoir"));
            System.out.println("7.Gere par : "+ monResultat.getInt("ref_compte"));
        }
    }

    public void Assigner_tache(BDD BDD) throws SQLException {
        PreparedStatement req = BDD.getConnection().prepareStatement("SELECT * FROM gere WHERE ref_tache = ?");
        req.setInt(1,ref_tache);
        ResultSet monResulat = req.executeQuery();
        if (monResulat.next()){
            PreparedStatement req1 = BDD.getConnection().prepareStatement("UPDATE gere SET ref_tache = ?, ref_compte = ? WHERE ref_tache = ?");
            req1.setInt(1,ref_tache);
            req1.setInt(2,ref_compte);
            req1.setInt(3,ref_tache);
            req1.executeUpdate();
        }
        else{
            PreparedStatement req2 = BDD.getConnection().prepareStatement("INSERT INTO gere(ref_tache,ref_compte) VALUES (?,?)");
            req2.setInt(1,ref_tache);
            req2.setInt(2,ref_compte);
            req2.executeUpdate();
        }
    }

    public void rechercheTache(BDD BDD, String filtre, String terme) throws SQLException{
        PreparedStatement req = BDD.getConnection().prepareStatement("SELECT * FROM tache WHERE "+filtre+" LIKE ? ");
        req.setString(1,"%"+terme+"%");
        System.out.println(filtre);
        System.out.println(terme);
        ResultSet monResultat = req.executeQuery();
        while (monResultat.next()){
            System.out.println("\n--------App Todo-List--------");
            System.out.println("1.libelle : "+monResultat.getString("libelle"));
            System.out.println("2.description : "+monResultat.getString("description"));
            System.out.println("3.difficulte : "+ monResultat.getInt("difficulte"));
            System.out.println("4.date_debut : "+monResultat.getString("date_debut"));
            System.out.println("5.date_fin : "+monResultat.getString("date_fin"));
            System.out.println("6.date_butoir : "+monResultat.getString("date_butoir"));
            setId_tache(monResultat.getInt(1));
            setLibelle(monResultat.getString(2));
            setDescription(monResultat.getString(3));
            setDifficulte(monResultat.getInt(4));
            setDate_debut(monResultat.getString(5));
            setDate_fin(monResultat.getString(6));
            setDate_butoir(monResultat.getString(7));
        }
        System.out.println("Fin du filtrage");
    }

    public void setId_tache(int id_tache) {
        this.id_tache = id_tache;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDifficulte(int difficulte) {
        this.difficulte = difficulte;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public void setDate_butoir(String date_butoir) {
        this.date_butoir = date_butoir;
    }

    public void setRef_compte(int ref_compte) {
        this.ref_compte = ref_compte;
    }

    public void setRef_tache(int ref_tache) {
        this.ref_tache = ref_tache;
    }
}
