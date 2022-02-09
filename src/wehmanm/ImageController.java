/*
 * Course: CS1021 - 051
 * Winter 2022
 * Lab 8 - Image manipulation
 * Name: Matt Wehman
 * Created: 2/7/2022
 */
package wehmanm;


import edu.msoe.cs1021.ImageUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static wehmanm.ImageIO.GRAY_GREEN;
import static wehmanm.ImageIO.GRAY_RED;

/**
 * Image controller class
 * Holds event handlers for buttons
 */
public class ImageController {

    private static final double GRAY_BLUE = .0722 ;
    @FXML
    Button buttonReload = new Button();

    @FXML
    Button buttonOpen = new Button();

    @FXML
    Button buttonNegative = new Button();

    @FXML
    Button buttonSave = new Button();

    @FXML
    Button buttonGray = new Button();

    @FXML
    ImageView view = new ImageView();

    private Path path;

    private Image originalImage;

    private Image newImage;
    /**
     * Opens file explorer for user
     * Loads image that is chosen into image view
     */
    public void load(ActionEvent event) {
        boolean isTrue = false;
        while (!isTrue) {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.msoe"),
                        new FileChooser.ExtensionFilter("All Files", "*.*"));
                File selectedFile = fileChooser.showOpenDialog(null);
                Path path = selectedFile.toPath();
                originalImage = ImageIO.read(path);
                setImagePath(path);
                view.setImage(originalImage);
                isTrue = true;
            } catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("File Warning");
                alert.setHeaderText("Wrong File Type");
                alert.setContentText("Please select a png, jpg, or msoe file. ");
                alert.showAndWait();
            }
        }
    }
    /**
     * Reloads original image into image view
     */
    public void reload(ActionEvent event){
        view.setImage(originalImage);
    }
    /**
     * Saves new image to original file path
     */
    public void save(ActionEvent event){
        //create another File chooser dialog
        //show open dialog instead
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("MSOE", "*.msoe"));
        File saveFile = fileChooser.showSaveDialog(null);
        Path path = saveFile.toPath();
        try {
            String[] pathString = path.toString().split("\\.");
            if(pathString[1].equals("msoe")){
                ImageIO.writeMSOE(view.getImage(), path);
            }else {
                ImageUtil.writeImage(path, view.getImage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Calls ImageIO.write and passes effect String
     * Then sets this new image to the image view
     */
    public void grayscale(ActionEvent event) {
        WritableImage writableImage = null;
        writableImage = new WritableImage((int) view.getImage().getWidth(), (int) view.getImage().getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        PixelReader pixels = view.getImage().getPixelReader();
        for (int x = 0; x < writableImage.getWidth(); x++) {
            for (int y = 0; y < writableImage.getHeight(); y++) {
                Color color = pixels.getColor(x, y);
                double r = color.getRed() * GRAY_RED;
                double g = color.getGreen() * GRAY_GREEN;
                double b = color.getBlue() * GRAY_BLUE;
                color = Color.color(r + b + g, r + b + g, r + b + g);
                pixelWriter.setColor(x, y, color);
            }
        }
        view.setImage(writableImage);
    }
    /**
     * Calls ImageIO.write and passes effect String
     * Then sets this new image to the image view
     */
    public void negative(ActionEvent event) {
        WritableImage writableImage = null;
        writableImage = new WritableImage((int) view.getImage().getWidth(), (int) view.getImage().getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        PixelReader pixels = view.getImage().getPixelReader();
        for (int x = 0; x < writableImage.getWidth(); x++) {
            for (int y = 0; y < writableImage.getHeight(); y++) {
                Color color = pixels.getColor(x, y);
                double r = 1.0 - color.getRed();
                double g = 1.0 - color.getGreen();
                double b = 1.0 - color.getBlue();
                color = Color.color(r, g, b);
                pixelWriter.setColor(x, y, color);
            }
        }
        view.setImage(writableImage);
    }
    /**
     * Sets path of original image.
     * Used for save function later.
     */
    private void setImagePath(Path path){
        this.path = path;
    }
}
