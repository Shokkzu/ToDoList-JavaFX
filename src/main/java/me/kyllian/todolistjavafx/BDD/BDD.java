package me.kyllian.todolistjavafx.BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDD {
    private final Connection cnx;
    public BDD() throws SQLException {
        cnx = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app_todo-list?serverTimezone=UTC",
                "root",
                "");
    }

    public Connection getConnection(){
        return cnx;
    }
}
