module me.kyllian.todolistjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;


    opens me.kyllian.todolistjavafx to javafx.fxml;
    exports me.kyllian.todolistjavafx;
    exports me.kyllian.todolistjavafx.controller;
    opens me.kyllian.todolistjavafx.controller to javafx.fxml;
    opens me.kyllian.todolistjavafx.modele to javafx.base;
}