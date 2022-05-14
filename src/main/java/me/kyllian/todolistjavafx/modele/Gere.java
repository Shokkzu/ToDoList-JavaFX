package me.kyllian.todolistjavafx.modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Gere {
    private int ref_compte;
    private int ref_tache;
    private boolean estAccepte;

    public Gere(int ref_compte, int ref_tache){
        this.ref_compte = ref_compte;
        this.ref_tache = ref_tache;
    }

    public void createur(BDD bdd) throws SQLException {
        PreparedStatement maRequete = bdd.getConnection().prepareStatement("INSERT INTO gere VALUES (?,?,1)");
        maRequete.setInt(1,this.ref_tache);
        maRequete.setInt(2,this.ref_compte);
        maRequete.executeUpdate();
    }

    public void demande(BDD bdd) throws SQLException {
        PreparedStatement maRequete = bdd.getConnection().prepareStatement("INSERT INTO gere VALUES (?,?,0)");
        maRequete.setInt(1,this.ref_tache);
        maRequete.setInt(2,this.ref_compte);
        maRequete.executeUpdate();
    }

    public int compte(BDD bdd, User currentUser) throws SQLException{
        PreparedStatement maRequete = bdd.getConnection().prepareStatement("SELECT COUNT(*) AS compte FROM gere WHERE ref_compte = ? AND accepte = 0");
        maRequete.setInt(1, currentUser.getId_compte());
        ResultSet maReponse = maRequete.executeQuery();
        maReponse.next();
        return maReponse.getInt("compte");
    }

    public void refus(BDD bdd) throws SQLException{
        PreparedStatement maRequete = bdd.getConnection().prepareStatement("DELETE FROM gere WHERE ref_compte = ? AND ref_tache = ?");
        maRequete.setInt(1,ref_compte);
        maRequete.setInt(2,ref_tache);
        maRequete.executeUpdate();
    }

    public void accept(BDD bdd) throws SQLException{
        PreparedStatement maRequete = bdd.getConnection().prepareStatement("UPDATE gere SET accepte = true WHERE ref_compte = ? AND ref_tache = ?");
        maRequete.setInt(1,ref_compte);
        maRequete.setInt(2,ref_tache);
        maRequete.executeUpdate();
    }
}
