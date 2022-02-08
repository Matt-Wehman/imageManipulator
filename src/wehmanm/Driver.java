/*
 * Course: CS1021 - 051
 * Winter 2022
 * Lab 8 - Image manipulation
 * Name: Matt Wehman
 * Created: 2/7/2022
 */
package wehmanm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Driver Class
 * Launches the JavaFX program
 */
public class Driver extends Application {
    /**
     * Width for JavaFX Scene
     */
    public static final int SCENE_WIDTH = 600;
    /**
     * Height for JavaFX Scene
     */
    public static final int SCENE_HEIGHT = 400;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Image.fxml"));

        stage.setTitle("Image tester");

        stage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));

        stage.show();

    }
}
