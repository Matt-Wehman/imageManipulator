package wehmanm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
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


    public void load(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(null);
        Path path = selectedFile.toPath();
        view.setImage(ImageIO.read(path));
    }

    public void reload(ActionEvent event){

    }

    public void save(ActionEvent event){

    }

    public void grayscale(ActionEvent event){

    }

    public void negative(ActionEvent event){
        Image file = view.getImage();
        ImageIO.write(view.getImage(), Path.of(view.getImage().getUrl()));
    }
}
