package me.kyllian.todolistjavafx.modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDD {
    private final Connection cnx;
    public BDD() throws SQLException {
        cnx = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/kmn_apptodolist_javafx?serverTimezone=UTC",
                "apptodolist",
                "");
    }

    public Connection getConnection(){
        return cnx;
    }
}
