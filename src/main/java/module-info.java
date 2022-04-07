module me.kyllian.todolistjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens me.kyllian.todolistjavafx to javafx.fxml;
    exports me.kyllian.todolistjavafx;
}