/*
 * CS1021 - <TODO Section>
 * Winter 2021-2022
 * Lab 8: Image Manipulation
 * <TODO Name>
 * <TODO Creation date>
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
import java.util.Arrays;
import java.util.Scanner;
public class ImageIO {

    public static Image read (Path path){
        Image image = null;
        Scanner reader = null;
        try{
            reader = new Scanner(path);
        }catch (IOException e){
            System.out.println("bad");
        }

        try {
           if(reader.nextLine().equals("MSOE")) {
           return readMSOE(path);

           }else {
               image = ImageUtil.readImage(path);
           }
        } catch (IllegalArgumentException e){
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

    public static Image write(Image image, Path path,String effect) {
        Scanner reader = null;
        WritableImage writableImage = null;
        try {
            reader = new Scanner(path);
        } catch (IOException e) {
            System.out.println("bad");
        }

        try {

            if (reader.nextLine().equals("MSOE")) {
                return writeMSOE(image, path, effect);

            } else if (effect.equals("grayscale")) {
                writableImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
                PixelWriter pixelWriter = writableImage.getPixelWriter();
                PixelReader pixels = image.getPixelReader();
                for (int x = 0; x < writableImage.getWidth(); x++) {
                    for (int y = 0; y < writableImage.getHeight(); y++) {
                        Color color = pixels.getColor(x, y);
                        double r = color.getRed() * .2126;
                        double g = color.getGreen() * .7152;
                        double b = color.getBlue() * .0722;
                        color = Color.color(r + b + g, r + b + g, r + b + g);
                        pixelWriter.setColor(x, y, color);
                    }
                }
            } else if (effect.equals("negative")) {
                writableImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
                PixelWriter pixelWriter = writableImage.getPixelWriter();
                PixelReader pixels = image.getPixelReader();
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
            }
        }catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return writableImage;
    }


    public static Image readMSOE(Path path) throws IOException {
       Scanner reader = null;
       int imageHieght = 0;
       int imageWidth = 0;
       ArrayList<String> lines = new ArrayList();
       WritableImage writableImage;
        try{
            reader = new Scanner(path);
            while(reader.hasNextLine()) {
                lines.add(reader.nextLine());
            }

        } catch (IOException e){
            System.out.println(e.getMessage());

        } finally {
            if(reader != null) {
                reader.close();
            }
        }
        String[] widthHeight = (lines.get(1).split("\s+"));
        imageHieght = Integer.parseInt(widthHeight[0]);
        imageWidth = Integer.parseInt(widthHeight[1]);

        writableImage = new WritableImage(imageHieght, imageWidth);
        PixelWriter writer = writableImage.getPixelWriter();
        for (int i = 2; i< lines.size();i++){
            String[] templine = (lines.get(i).split("\s+"));
            for (int j = 0; j < templine.length;j++){
                Color pixelColor = Color.web(templine[j]);
                writer.setColor(j, i-2, pixelColor);
            }
        }
        return writableImage;
    }

    public static Image writeMSOE(Image image, Path path,String effect){
        Scanner reader = null;
        WritableImage writableImage = null;
        try {
            reader = new Scanner(path);
        } catch (IOException e) {
            System.out.println("bad");
        }

        try {

             if (effect.equals("grayscale")) {
                writableImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
                PixelWriter pixelWriter = writableImage.getPixelWriter();
                PixelReader pixels = image.getPixelReader();
                for (int x = 0; x < writableImage.getWidth(); x++) {
                    for (int y = 0; y < writableImage.getHeight(); y++) {
                        Color color = pixels.getColor(x, y);
                        double r = color.getRed() * .2126;
                        double g = color.getGreen() * .7152;
                        double b = color.getBlue() * .0722;
                        color = Color.color(r + b + g, r + b + g, r + b + g);
                        pixelWriter.setColor(x, y, color);
                    }
                }
            } else if (effect.equals("negative")) {
                writableImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
                PixelWriter pixelWriter = writableImage.getPixelWriter();
                PixelReader pixels = image.getPixelReader();
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
            }
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        return writableImage;
    }
    }


