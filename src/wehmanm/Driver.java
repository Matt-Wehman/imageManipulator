/*
 * Course: CS1021 - 051
 * Winter 2022
 * Lab 9 - Image manipulation
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
    public void start(Stage primaryStage) throws Exception {

        //Primary Loader Instantiation
        FXMLLoader primaryLoader = new FXMLLoader();
        //Get root
        Parent primaryRoot = primaryLoader.load(getClass().getResource("Image.fxml").openStream());

        //Primary (main) Stage/Window
        primaryStage.setTitle("Image Manipulator");
        primaryStage.setScene(new Scene(primaryRoot, SCENE_WIDTH, SCENE_HEIGHT));
        primaryStage.show();

        FXMLLoader secondaryLoader = new FXMLLoader();

        Parent secondaryRoot = secondaryLoader.load(getClass()
                .getResource("filter.fxml").openStream());

        //Create secondary stage (Instantiation)
        Stage secondaryStage = new Stage();

        //Secondary Stage/Window
        secondaryStage.setTitle("Filter Settings");
        secondaryStage.setScene(new Scene(secondaryRoot));
        secondaryStage.hide();


        //Get Controller
        ImageController primaryController = primaryLoader.getController();
        FilterController secondaryController = secondaryLoader.getController();


        //Pass stage references to each of the controllers
        primaryController.setReferenceStage(secondaryStage);
        primaryController.setPrimaryStage(primaryStage);
        secondaryController.setReferenceStage(primaryStage);
        secondaryController.setSecondaryStage(secondaryStage);
        primaryController.setFilterController(secondaryController);
        secondaryController.setImageController(primaryController);

    }
}
