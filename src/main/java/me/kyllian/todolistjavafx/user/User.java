package me.kyllian.todolistjavafx.user;

import me.kyllian.todolistjavafx.BDD.BDD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int id_compte;
    private String nom;
    private String prenom;
    private String email;
    private String mdp;

    public User(String nom, String prenom, String email, String mdp,BDD BDD) throws SQLException {
        setNom(nom);
        setPrenom(prenom);
        setEmail(email);
        setMdp(mdp);
        Inscription(BDD);
    }

    public User(String email, String mdp){
        setEmail(email);
        setMdp(mdp);
    }

    public void Inscription(BDD BDD) throws SQLException {
        PreparedStatement req = BDD.getConnection().prepareStatement("INSERT INTO compte(nom, prenom, email, mdp) VALUES (?,?,?,?)");
        req.setString(1,nom);
        req.setString(2,prenom);
        req.setString(3,email);
        req.setString(4,mdp);
        req.executeUpdate();
    }

    public boolean Connexion(BDD BDD) throws SQLException {
        PreparedStatement req = BDD.getConnection().prepareStatement("SELECT * FROM compte WHERE email = ? AND mdp = ?");
        req.setString(1,email);
        req.setString(2,mdp);
        ResultSet monResulat = req.executeQuery();
        if (monResulat.next()){
            setId_compte(monResulat.getInt(1));
            setNom(monResulat.getString(2));
            setPrenom(monResulat.getString(3));
            setEmail(monResulat.getString(4));
            setMdp(monResulat.getString(5));
            return true;
        } else {
            return false;
        }
    }

    public void Modification(BDD BDD) throws SQLException {
        PreparedStatement req = BDD.getConnection().prepareStatement("UPDATE compte SET nom = ?, prenom = ?, email = ?, mdp = ? WHERE id_compte = ?");
        req.setString(1,nom);
        req.setString(2,prenom);
        req.setString(3,email);
        req.setString(4,mdp);
        req.setInt(5,id_compte);
        req.executeUpdate();
    }

    public void Suppression(BDD BDD) throws SQLException {
        PreparedStatement req = BDD.getConnection().prepareStatement("DELETE FROM compte WHERE id_compte = ?");
        req.setInt(1, id_compte);
        req.executeUpdate();
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