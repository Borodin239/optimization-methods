package lab01.graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWin extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {

        String path = "mainWin.fxml";

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource(path).openStream());
        primaryStage.setTitle("Hello, додик!");
        primaryStage.setScene(new Scene(root));
        primaryStage.setAlwaysOnTop(false);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}