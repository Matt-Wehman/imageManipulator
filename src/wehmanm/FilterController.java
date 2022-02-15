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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.ArrayList;
/**
 * Filter controller class
 * Holds event handlers for filter buttons
 */
public class FilterController {
    @FXML
    Button buttonBlur = new Button();

    @FXML
    Button buttonApply = new Button();

    @FXML
    Button buttonSharpen = new Button();

    @FXML
    TextField kernelTopRight = new TextField();

    @FXML
    TextField kernelTopCenter = new TextField();

    @FXML
    TextField kernelTopLeft = new TextField();

    @FXML
    TextField kernelCenterLeft = new TextField();

    @FXML
    TextField kernelCenter = new TextField();

    @FXML
    TextField kernelCenterRight = new TextField();

    @FXML
    TextField kernelBottomLeft = new TextField();

    @FXML
    TextField kernelBottomRight = new TextField();

    @FXML
    TextField kernelBottomCenter = new TextField();

    private Stage primaryStage;

    private Stage secondaryStage;

    protected ImageController imageController;

    protected Image viewImage;

    protected void setReferenceStage(Stage stage){
        primaryStage = stage;
    }

    protected void setSecondaryStage(Stage stage){
        this.secondaryStage = stage;
    }

    protected void setImageController(ImageController imageController){
        this.imageController = imageController;
    }
    /**
     * Gets image view from image controller
     */
    public void data(Image image){
        this.viewImage = image;
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
     * Sets filter kernel for blur
     */
    public void setButtonBlur(ActionEvent event){
        kernelTopRight.setText("0");
        kernelTopCenter.setText("1");
        kernelTopLeft.setText("0");
        kernelCenterLeft.setText("1");
        kernelCenter.setText("5");
        kernelCenterRight.setText("1");
        kernelBottomLeft.setText("0");
        kernelBottomCenter.setText("1");
        kernelBottomRight.setText("0");
    }
    /**
     * Sets filter kernel for sharpen
     */
    public void setButtonSharpen(ActionEvent event){
        kernelTopRight.setText("0");
        kernelTopCenter.setText("-1");
        kernelTopLeft.setText("0");
        kernelCenterLeft.setText("-1");
        kernelCenter.setText("5");
        kernelCenterRight.setText("-1");
        kernelBottomLeft.setText("0");
        kernelBottomCenter.setText("-1");
        kernelBottomRight.setText("0");
    }
    /**
     * Applies filter kernel to image then replaces in imageview
     */
    public void setButtonApply(ActionEvent event){
        ArrayList<Double> values = new ArrayList<>();
        double sum = 0;
        values.add(Double.parseDouble(kernelBottomRight.getText()));
        values.add(Double.parseDouble(kernelBottomCenter.getText()));
        values.add(Double.parseDouble(kernelBottomLeft.getText()));
        values.add(Double.parseDouble(kernelTopCenter.getText()));
        values.add(Double.parseDouble(kernelTopLeft.getText()));
        values.add(Double.parseDouble(kernelTopRight.getText()));
        values.add(Double.parseDouble(kernelCenter.getText()));
        values.add(Double.parseDouble(kernelCenterLeft.getText()));
        values.add(Double.parseDouble(kernelCenterRight.getText()));
        for(int i = 0; i < values.size(); i++){
            sum = sum + values.get(i);
        }

        double[] kernel = {
                (Double.parseDouble(kernelTopLeft.getText()) / sum),
                (Double.parseDouble(kernelTopCenter.getText()) / sum),
                (Double.parseDouble(kernelTopRight.getText()) / sum),
                (Double.parseDouble(kernelCenterLeft.getText()) / sum),
                (Double.parseDouble(kernelCenter.getText()) / sum),
                (Double.parseDouble(kernelCenterRight.getText()) / sum),
                (Double.parseDouble(kernelBottomLeft.getText()) / sum),
                (Double.parseDouble(kernelBottomCenter.getText()) / sum),
                (Double.parseDouble(kernelBottomRight.getText()) / sum)};

        String kernels = " ";
        for(int i = 0; i < kernel.length; i++){
            kernels = kernels + kernel[i] + "  ";
        }
        imageController.setImageView(ImageUtil.convolve(viewImage, kernel));
        System.out.println(kernels);
        //secondaryStage.hide();
    }
}
