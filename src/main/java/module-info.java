module me.kyllian.todolistjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens me.kyllian.todolistjavafx to javafx.fxml;
    exports me.kyllian.todolistjavafx;
    exports me.kyllian.todolistjavafx.controller;
    opens me.kyllian.todolistjavafx.controller to javafx.fxml;
}