package wehmanm;

import edu.msoe.cs1021.ImageUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ImageController {

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

    public void reload(ActionEvent event){
        view.setImage(originalImage);
    }

    public void save(ActionEvent event){
        try {
            if(view.getImage() == originalImage){
                ImageUtil.writeImage(path,originalImage);
            }else {
                ImageUtil.writeImage(path, newImage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void grayscale(ActionEvent event){
        String effect = "grayscale";
        Image image = view.getImage();
        newImage = ImageIO.write(image,path,effect);
        view.setImage(newImage);
    }

    public void negative(ActionEvent event){
        String effect = "negative";
        Image image = view.getImage();
        newImage = ImageIO.write(image,path,effect);
        view.setImage(newImage);
    }

    private void setImagePath(Path path){
        this.path = path;
    }
}
