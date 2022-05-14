package me.kyllian.todolistjavafx.modele;

import me.kyllian.todolistjavafx.modele.BDD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
    private int id_compte;
    private String nom;
    private String prenom;
    private String email;
    private String mdp;

    public User(int id,String nom, String prenom, String email) throws SQLException {
        setId_compte(id);
        setNom(nom);
        setPrenom(prenom);
        setEmail(email);
    }


    public User(String nom, String prenom, String email, String mdp) throws SQLException {
        setNom(nom);
        setPrenom(prenom);
        setEmail(email);
        setMdp(mdp);
    }

    public User(int id_compte, String nom, String prenom, String email, String mdp) throws SQLException {
        setId_compte(id_compte);
        setNom(nom);
        setPrenom(prenom);
        setEmail(email);
        setMdp(mdp);
    }

    public User(String email, String mdp){
        setEmail(email);
        setMdp(mdp);
    }

    public User(String nom, String prenom, String mail) {
        setNom(nom);
        setPrenom(prenom);
        setEmail(email);
    }

    public void inscription(BDD bdd) throws SQLException {
        bdd.getConnection().prepareStatement("ALTER TABLE compte AUTO_INCREMENT = 1;").executeUpdate();
        PreparedStatement req = bdd.getConnection().prepareStatement("INSERT INTO compte(nom, prenom, email, mdp) VALUES (?,?,?,?)");
        req.setString(1,nom);
        req.setString(2,prenom);
        req.setString(3,email);
        req.setString(4,mdp);
        req.executeUpdate();
    }

    public ResultSet connexion(BDD bdd) throws SQLException {
        PreparedStatement maRequete = bdd.getConnection().prepareStatement("SELECT * FROM compte WHERE email=? AND mdp=?");
        maRequete.setString(1,this.email);
        maRequete.setString(2,this.mdp);
        return maRequete.executeQuery();
    }

    public void modification(BDD BDD) throws SQLException {
        PreparedStatement req = BDD.getConnection().prepareStatement("UPDATE compte SET nom = ?, prenom = ?, email = ?, mdp = ? WHERE id_compte = ?");
        req.setString(1,nom);
        req.setString(2,prenom);
        req.setString(3,email);
        req.setString(4,mdp);
        req.setInt(5,id_compte);
        req.executeUpdate();
    }

    public void suppression(BDD BDD) throws SQLException {
        PreparedStatement req = BDD.getConnection().prepareStatement("DELETE FROM compte WHERE id_compte = ?");
        req.setInt(1, id_compte);
        req.executeUpdate();
    }

    public ArrayList<User> readAll(BDD bdd) throws SQLException{
        ArrayList<User> mesComptes = new ArrayList<>();
        PreparedStatement maRequete = bdd.getConnection().prepareStatement("SELECT * FROM compte");
        ResultSet monResultat = maRequete.executeQuery();
        while (monResultat.next()){
            User monCompte = new User(monResultat.getInt("id_compte"),monResultat.getString("nom"),monResultat.getString("prenom"),monResultat.getString("email"));
            mesComptes.add(monCompte);
        }
        return mesComptes;
    }

    public String toString(){
        return getNom()+" "+getPrenom();
    }

    public int getId_compte() {
        return id_compte;
    }

    public void setId_compte(int id_compte) {
        this.id_compte = id_compte;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}