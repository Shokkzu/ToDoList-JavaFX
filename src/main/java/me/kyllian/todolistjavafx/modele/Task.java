package me.kyllian.todolistjavafx.modele;

import javafx.beans.Observable;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Task {
    private int id_tache;
    private String libelle;
    private String description;
    private String difficulte;
    private String date_debut;
    private String date_fin;
    private String date_butoir;
    private boolean accepte;
    private int ref_type;
    private int ref_etat;
    private int ref_compte;
    private int ref_tache;
    private int ref_liste;
    private String type;
    private String etat;
    private String nomgerant;
    private String prenomgerant;
    private String listName;

    public Task(String libelle, String description, String difficulte, String date_debut, String date_fin, String date_butoir) throws SQLException {
        setLibelle(libelle);
        setDate_butoir(date_butoir);
        setDate_debut(date_debut);
        setDate_fin(date_fin);
        setDescription(description);
        setDifficulte(difficulte);
    }

    public Task(int ref_tache,int ref_compte){
        setRef_compte(ref_compte);
        setRef_tache(ref_tache);
    }


    public Task(String libelle, String description, String difficulte, String date_debut, String date_fin, String date_butoir, int ref_type, int ref_etat, int ref_liste){
        setLibelle(libelle);
        setDescription(description);
        setDifficulte(difficulte);
        setDate_debut(date_debut);
        setDate_fin(date_fin);
        setDate_butoir(date_butoir);
        setRef_etat(ref_etat);
        setRef_type(ref_type);
        setRef_Liste(ref_liste);
    }

    public Task(int id_tache,String libelle){
        this.id_tache = id_tache;
        this.libelle = libelle;
    }

    public Task(int id_tache, String nom, String description, String difficulte, String date_debut, String date_fin, String date_butoir, String type, String etat, String nomgerant, String prenomgerant) {
        this.id_tache = id_tache;
        this.libelle = nom;
        this.description= description;
        this.difficulte = difficulte;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.date_butoir = date_butoir;
        this.type = type;
        this.etat = etat;
        this.nomgerant = nomgerant;
        this.prenomgerant = prenomgerant;
    }

    public Task(int id_tache,int ref_liste, String nom, String description, String difficulte, String date_debut, String date_fin, String date_butoir, String type, String etat, String nomgerant, String prenomgerant) {
        this.id_tache = id_tache;
        this.ref_liste = ref_liste;
        this.libelle = nom;
        this.description= description;
        this.difficulte = difficulte;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.date_butoir = date_butoir;
        this.type = type;
        this.etat = etat;
        this.nomgerant = nomgerant;
        this.prenomgerant = prenomgerant;
    }

    public Task(int ref_liste) {
        this.ref_liste = ref_liste;
    }

    public Task(String nom, String description, String difficulte, String date_debut, String date_fin, String date_butoir, int ref_liste) {
        this.libelle = nom;
        this.description= description;
        this.difficulte = difficulte;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.date_butoir = date_butoir;
        this.ref_liste = ref_liste;
    }

    public Task(String nomListe, int id_tache, String nom, String description, String difficulte, String date_debut, String date_fin, String date_butoir, String type, String etat, String nomgerant, String prenomgerant) {
        this.listName = nomListe;
        this.id_tache = id_tache;
        this.libelle = nom;
        this.description= description;
        this.difficulte = difficulte;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.date_butoir = date_butoir;
        this.type = type;
        this.etat = etat;
        this.nomgerant = nomgerant;
        this.prenomgerant = prenomgerant;
    }

    public Task(String nomListe, int id_tache,boolean accepte, String nom, String description, String difficulte, String date_debut, String date_fin, String date_butoir, String type, String etat, String nomgerant, String prenomgerant) {
        this.listName = nomListe;
        this.id_tache = id_tache;
        this.accepte = accepte;
        this.libelle = nom;
        this.description= description;
        this.difficulte = difficulte;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.date_butoir = date_butoir;
        this.type = type;
        this.etat = etat;
        this.nomgerant = nomgerant;
        this.prenomgerant = prenomgerant;
    }

    public void Ajout_tache(BDD BDD) throws SQLException {
        PreparedStatement req = BDD.getConnection().prepareStatement("INSERT INTO tache(libelle,description, difficulte, date_debut, date_fin,date_butoir) VALUES (?,?,?,?,?,?)");
        req.setString(1,libelle);
        req.setString(2,description);
        req.setString(3,difficulte);
        req.setString(4,date_debut);
        req.setString(5,date_fin);
        req.setString(6,date_butoir);
        req.executeUpdate();
    }

    public ArrayList<Task> specificRead(BDD BDD, User currentUser) throws SQLException {
        ArrayList<Task> mesTaches = new ArrayList<>();
        PreparedStatement req = BDD.getConnection().prepareStatement("SELECT * FROM infotache WHERE ref_compte = ? AND ref_liste = ? AND accepte = 1");
        req.setInt(1, currentUser.getId_compte());
        req.setInt(2,ref_liste);
        ResultSet monResultat = req.executeQuery();
        while (monResultat.next()){
            Task maTache = new Task(monResultat.getInt("id_tache"),monResultat.getInt("ref_liste"),monResultat.getString("nom"),monResultat.getString("description"),monResultat.getString("difficulte"),monResultat.getString("date_debut"),monResultat.getString("date_fin"),monResultat.getString("date_butoir"),monResultat.getString("type"),monResultat.getString("etat"),monResultat.getString("nomgerant"),monResultat.getString("prenomgerant"));
            mesTaches.add(maTache);
        }
        return mesTaches;
    }

    public ArrayList<Task> readAll(BDD bdd) throws SQLException{
        ArrayList<Task> mesListes = new ArrayList<>();
        PreparedStatement maRequete = bdd.getConnection().prepareStatement("SELECT * FROM infotache");
        ResultSet monResultat = maRequete.executeQuery();
        while (monResultat.next()){
            Task maListe = new Task(monResultat.getInt("id_tache"),monResultat.getString("nom"),monResultat.getString("description"),monResultat.getString("difficulte"),monResultat.getString("date_debut"),monResultat.getString("date_fin"),monResultat.getString("date_butoir"),monResultat.getString("type"),monResultat.getString("etat"),monResultat.getString("nomgerant"),monResultat.getString("prenomgerant"));
            mesListes.add(maListe);
        }
        return mesListes;
    }

    public void delete(BDD bdd) throws SQLException {
        PreparedStatement maRequete = bdd.getConnection().prepareStatement("DELETE FROM tache WHERE id_tache = ?");
        maRequete.setInt(1,this.id_tache);
        maRequete.executeUpdate();
    }

    public void create(BDD bdd, User currentUser) throws SQLException{
        PreparedStatement maRequete = bdd.getConnection().prepareStatement("INSERT INTO tache(libelle,description,difficulte,date_debut,date_fin,date_butoir,ref_type,ref_etat,ref_liste) VALUES (?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        maRequete.setString(1, this.libelle);
        maRequete.setString(2, this.description);
        maRequete.setString(3, this.difficulte);
        maRequete.setString(4, this.date_debut);
        maRequete.setString(5, this.date_fin);
        maRequete.setString(6, this.date_butoir);
        maRequete.setInt(7, this.ref_type);
        maRequete.setInt(8, this.ref_etat);
        maRequete.setInt(9, this.ref_liste);
        maRequete.executeUpdate();
        ResultSet resultat = maRequete.getGeneratedKeys();
        if (resultat.next()){
            int id = resultat.getInt(1);
            Gere newGere = new Gere(currentUser.getId_compte(), id);
            newGere.createur(bdd);
        }
    }

    public ArrayList<Task> readGere(BDD bdd, User currentUser) throws SQLException {
        ArrayList<Task> mesTaches = new ArrayList<>();
        PreparedStatement maRequete = bdd.getConnection().prepareStatement("SELECT * FROM infotache WHERE ref_compte = ? AND accepte = 0");
        maRequete.setInt(1, currentUser.getId_compte());
        ResultSet monResultat = maRequete.executeQuery();
        while (monResultat.next()){
            Task maListe = new Task(monResultat.getString("nomListe"),monResultat.getInt("id_tache"),monResultat.getBoolean("accepte"),monResultat.getString("nom"),monResultat.getString("description"),monResultat.getString("difficulte"),monResultat.getString("date_debut"),monResultat.getString("date_fin"),monResultat.getString("date_butoir"),monResultat.getString("type"),monResultat.getString("etat"),monResultat.getString("nomgerant"),monResultat.getString("prenomgerant"));
            mesTaches.add(maListe);
        }
        return mesTaches;
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

    public void setDifficulte(String difficulte) {
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

    public int getIdTache() {
        return id_tache;
    }

    public void setRef_Liste(int ref_liste){
        this.ref_liste = ref_liste;
    }
    public int getRefCompte() {
        return ref_compte;
    }

    public int getRefEtat() {
        return ref_etat;
    }

    public int getRef_liste() {
        return ref_liste;
    }

    public int getRefTache() {
        return ref_tache;
    }

    public int getRefType() {
        return ref_type;
    }

    public String getDateButoir() {
        return date_butoir;
    }

    public String getDateDebut() {
        return date_debut;
    }

    public String getDateFin() {
        return date_fin;
    }

    public String getDescription() {
        return description;
    }

    public String getDifficulte() {
        return difficulte;
    }

    public String getEtat() {
        return etat;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getNomgerant() {
        return nomgerant;
    }

    public String getPrenomgerant() {
        return prenomgerant;
    }

    public String getType() {
        return type;
    }

    public void setRef_type(int ref_type) {
        this.ref_type = ref_type;
    }

    public void setRef_etat(int ref_etat) {
        this.ref_etat = ref_etat;
    }

    public String getListName() {
        return listName;
    }
}
