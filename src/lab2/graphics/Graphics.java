package lab2.graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Graphics extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {

        String path = "graphics.fxml";

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource(path).openStream());
        GraphicsController controller = fxmlLoader.getController();
        controller.build();
        primaryStage.setTitle("Графики с линиями уровня функций и траектроиями методов");
        primaryStage.setScene(new Scene(root));
        primaryStage.setAlwaysOnTop(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
