package yiarth.raz.java_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import yiarth.raz.java_project.controllers.*;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("CEPE");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("Work in progress...");
        AverageController average = new AverageController();
        System.out.println(average.get_average_deliberation());
        launch();
    }
}