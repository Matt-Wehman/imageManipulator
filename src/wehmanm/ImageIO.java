/*
 * Course: CS1021 - 051
 * Winter 2022
 * Lab 8 - Image manipulation
 * Name: Matt Wehman
 * Created: 2/7/2022
 */
package wehmanm;


import edu.msoe.cs1021.ImageUtil;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Image IO class is responsible for reading writing to files
 */
public class ImageIO {
    /**
     * Converts the red color of an image to grayscale version
     */
    public static final double GRAY_RED = .2126;
    /**
     * Converts the blue color of an image to grayscale version
     */
    public static final double GRAY_BLUE = .0722;
    /**
     * Converts the green color of an image to grayscale version
     */
    public static final double GRAY_GREEN = .7152;

    /**
     * Reads path and returns image found at that path
     * If the file is a msoe file the method then calls readMSOE
     */
    public static Image read(Path path) {
        Image image = null;
        Scanner reader = null;
        try {
            reader = new Scanner(path);
        } catch (IOException e) {
            System.out.println("bad");
        }

        try {
            if (reader.nextLine().equals("MSOE")) {
                return readMSOE(path);

            } else {
                image = ImageUtil.readImage(path);
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
            alert.setHeaderText("File error");
            alert.setContentText("Unable to read file");
            alert.showAndWait();
        }
        return image;
    }

    /**
     * Writes new pixel values to image and returns image
     * If the file is a msoe file the method then calls writeMSOE
     */
    public static Image write(Image image, Path path) {
        Scanner reader = null;
        WritableImage writableImage = null;
        try {
            reader = new Scanner(path);
        } catch (IOException e) {
            System.out.println("bad");
        }
        return writableImage;
    }

    /**
     * Reads path and returns image found at that path
     * Only works for MSOE images
     */
    public static Image readMSOE(Path path) throws IOException {
        Scanner reader = null;
        int imageHieght = 0;
        int imageWidth = 0;
        ArrayList<String> lines = new ArrayList();
        WritableImage writableImage;
        try {
            reader = new Scanner(path);
            while (reader.hasNextLine()) {
                lines.add(reader.nextLine());
            }

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("File Warning");
            alert.setHeaderText("File error");
            alert.setContentText("Unable to read file");
            alert.showAndWait();

        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        String[] widthHeight = (lines.get(1).split("\s+"));
        imageHieght = Integer.parseInt(widthHeight[0]);
        imageWidth = Integer.parseInt(widthHeight[1]);
        writableImage = new WritableImage(imageHieght, imageWidth);
        PixelWriter writer = writableImage.getPixelWriter();
        for (int i = 2; i < lines.size(); i++) {
            String[] templine = (lines.get(i).split("\s+"));
            for (int j = 0; j < templine.length; j++) {
                Color pixelColor = Color.valueOf(templine[j]);
                writer.setColor(j, i - 2, pixelColor);
            }
        }
        return writableImage;
    }

    /**
     * Writes new pixel values to file and returns image
     * Only works for MSOE files
     */
    public static File writeMSOE(Image image, Path path) {
        ArrayList<String> fileLines = new ArrayList<>();
        int imageHeight = (int)image.getHeight();
        int imageWidth = (int) image.getWidth();
        PixelReader pixels = image.getPixelReader();
        File msoeImage = new File(path.toString());
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(msoeImage);
            writer.println("MSOE");
            writer.print(imageWidth + " ");
            writer.println(imageHeight);
            for (int y = 0; y < image.getHeight(); y++) {
                String colorStrings = "";
                for (int x = 0; x < image.getWidth(); x++) {
                    Color color = pixels.getColor(x, y);
                    colorStrings = colorStrings + color.toString() + "  ";
                }
                fileLines.add(colorStrings);
            }
            for (String line : fileLines) {
                writer.println(line);
            }
        }catch (IOException e){
            System.out.println("sucks");
        }finally {
            if(writer != null) {
                writer.close();
            }
        }
        return msoeImage;
    }
}


