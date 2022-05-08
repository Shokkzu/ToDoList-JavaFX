package me.kyllian.todolistjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {
    private static Stage stage;
    @Override
    public void start(Stage firstStage) throws IOException {
        stage = firstStage;
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("connexion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("App To Do List");
        stage.setScene(scene);
        stage.show();
    }

    public static void changeScene(String fxmlFile, Object controller){
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(fxmlFile+".fxml"));
        Scene scene = null;
        try {
            fxmlLoader.setController(controller);
            scene = new Scene(fxmlLoader.load());
            stage.setTitle("App To-Do List");
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void changeScene(String fxmlFile){
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(fxmlFile+".fxml"));
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load());
            stage.setTitle("App To-Do List");
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}