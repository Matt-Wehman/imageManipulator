/*
 * CS1021 - <TODO Section>
 * Winter 2021-2022
 * Lab 8: Image Manipulation
 * <TODO Name>
 * <TODO Creation date>
 */
package wehmanm;


import edu.msoe.cs1021.ImageUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
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
            System.out.println("Illegal arguement");
        }
        catch (IOException e) {
            System.out.println("IOException");
        }
        return image;
    }

    public static void write(Image image, Path path){
        Scanner reader = null;
        try{
            reader = new Scanner(path);
        }catch (IOException e){
            System.out.println("bad");
        }

        try {

            if(reader.nextLine().equals("MSOE")) {
                writeMSOE(image,path);

            }else {
                ImageUtil.writeImage(path,image);
                image.
            }
        } catch (IllegalArgumentException e){
            System.out.println("Illegal arguement");
        }
        catch (IOException e) {
            System.out.println("IOException");
        }
        return image;
    }

    public static Image readMSOE(Path path){
        return null;
    }

    public static Image writeMSOE(Image image, Path path){
        return null;
    }

}

