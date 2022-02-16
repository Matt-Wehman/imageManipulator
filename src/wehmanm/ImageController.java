/*
 * Course: CS1021 - 051
 * Winter 2022
 * Lab 9 - Image manipulation
 * Name: Matt Wehman
 * Created: 2/7/2022
 */
package wehmanm;
import edu.msoe.cs1021.ImageUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Image controller class
 * Holds event handlers for buttons
 */
public class ImageController {

    private static final double GRAY_BLUE = .0722;
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

    @FXML
    Button buttonRed = new Button();

    @FXML
    Button buttonRedGray = new Button();

    @FXML
    Button buttonFilter = new Button();

    private Path path;

    private Image originalImage;

    private Image newImage;
    /**
     * Opens file explorer for user
     * Loads image that is chosen into image view
     */

    private Stage secondaryStage;

    private Stage primaryStage;

    private FilterController controller;

    @FXML
    Label responseLabel;

    protected void setReferenceStage(Stage stage){
        this.secondaryStage = stage;
    }

    protected void setPrimaryStage(Stage stage){
        this.primaryStage = stage;
    }

    public void setFilterController(FilterController filterController){
        this.controller = filterController;
    }

    @FXML
    protected void openWindow(ActionEvent event){
        if(secondaryStage.isShowing()){
            secondaryStage.hide();
        } else if(!secondaryStage.isShowing()){
            Stage currentStage = (Stage) primaryStage.getScene().getWindow();
            secondaryStage.setX(currentStage.getX() + currentStage.getWidth());
            secondaryStage.setY(currentStage.getY());
            secondaryStage.show();
        }
    }
    /**
     * Loads image into image view
     * Opens file chooser to let user select an image
     */
    public void load(ActionEvent event) {
        boolean isTrue = false;
        while (!isTrue) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File selectedFile;
            fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter(
                                "Image Files", "*.png", "*.jpg", "*.msoe", "*.bmsoe"));
            fileChooser.setInitialDirectory(new File("images"));
            selectedFile = fileChooser.showOpenDialog(null);
            Path path = selectedFile.toPath();
            try {
                originalImage = ImageIO.read(path);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("File Warning");
                alert.setHeaderText("Load error");
                alert.setContentText("Cannot load file");
                alert.showAndWait();
            }
            secondaryStage.hide();
            view.setImage(originalImage);
            isTrue = true;
        }
    }

    /**
     * Reloads original image into image view
     */
    public void reload(ActionEvent event){
        view.setImage(originalImage);
    }
    /**
     * Saves new image to path chosen by user
     */
    public void save(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("MSOE", "*.msoe"),
                new FileChooser.ExtensionFilter("BMSOE", "*.bmsoe"));
        fileChooser.setInitialDirectory(new File("images"));
        File saveFile = fileChooser.showSaveDialog(null);
        Path path = saveFile.toPath();
        try {
            String[] pathString = path.toString().split("\\.");
            if(pathString[1].equals("msoe")){
                ImageIO.writeMSOE(view.getImage(), path);
            } else if(pathString[1].equals("bmsoe")){
                ImageIO.writeBMSOE(view.getImage(), path);
            } else {
                ImageUtil.writeImage(path, view.getImage());
            }

        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("File Warning");
            alert.setHeaderText("File error");
            alert.setContentText("Unable to read file");
            alert.showAndWait();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("File Warning");
            alert.setHeaderText("Save error");
            alert.setContentText("Cannot save file");
            alert.showAndWait();
        }
    }
    /**
     * Makes image in imageview grayscaled
     */
    public void grayscale(ActionEvent event) {
        view.setImage(transformImage(view.getImage(), (y, pixelColor) -> {
            return pixelColor.grayscale();
        }));
    }
    /**
     * Makes image in imageview negative
     */
    public void negative(ActionEvent event) {
        view.setImage(transformImage(view.getImage(), (y, pixelColor) -> {
            return pixelColor.invert();
        }));
    }
    /**
     * Makes image in imageview red
     */
    public void red(ActionEvent event){
        view.setImage(transformImage(view.getImage(), (y, pixelColor) -> {
            Color color = new Color(pixelColor.getRed(), 0, 0, 1);
            return color;
        }));
    }
    /**
     * Makes image in imageview redGray
     */
    public void redGray(ActionEvent event){
        view.setImage(transformImage(view.getImage(), (y, pixelColor) -> {
            if(y % 2 == 0){
                return pixelColor.grayscale();
            } else {
                Color color = new Color(pixelColor.getRed(), 0, 0, 1);
                return color;
            }
        }));
    }
    /**
     * Sets path of original image.
     * Used for save function later.
     */
    public void setImageView(Image image){
        view.setImage(image);
    }
    /**
     * Gets image in current imageview
     */
    public Image getImage(){
        return this.view.getImage();
    }

    private static Image transformImage(Image image, Transformable transform) {
        WritableImage writableImage = null;
        writableImage = new WritableImage(
                (int) image.getWidth(),
                (int) image.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        PixelReader pixels = image.getPixelReader();
        for (int x = 0; x < writableImage.getWidth(); x++) {
            for (int y = 0; y < writableImage.getHeight(); y++) {
                Color color = pixels.getColor(x, y);
                pixelWriter.setColor(x, y, transform.apply(y, color));
            }
        }
        return writableImage;
    }
}
