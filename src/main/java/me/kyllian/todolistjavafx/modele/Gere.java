package me.kyllian.todolistjavafx.modele;

import java.sql.PreparedStatement;
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
}
