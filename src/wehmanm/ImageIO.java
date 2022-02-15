/*
 * Course: CS1021 - 051
 * Winter 2022
 * Lab 9 - Image manipulation
 * Name: Matt Wehman
 * Created: 2/7/2022
 */
package wehmanm;
import edu.msoe.cs1021.ImageUtil;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
     * Multiplies color string value so it can be properly formatted
     */
    public static final int RGB = 255;

    /**
     * Reads path and returns image found at that path
     * If the file is a msoe file the method then calls readMSOE
     */
    public static Image read(Path path) throws IOException, IllegalArgumentException {
        Image image = null;
        Scanner reader = null;
        DataInputStream input = null;
        FileInputStream fileInputStream = null;
        reader = new Scanner(path.toFile());
        String[] pathSplit = path.toString().split("\\.");
        fileInputStream = new FileInputStream(path.toFile());
        if(pathSplit[1].equals("bmsoe")){
            return readBMSOE(path);
        } else if (reader.hasNextLine()) {
            if(reader.nextLine().equals("MSOE")) {
                return readMSOE(path);
            }
        } else {
            return ImageUtil.readImage(path);
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
    public static File writeMSOE(Image image, Path path) throws IOException {
        ArrayList<String> fileLines = new ArrayList<>();
        int imageHeight = (int) image.getHeight();
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
                    String hex = String.format("#%02X%02X%02X",
                            (int) (color.getRed() * RGB),
                            (int) (color.getGreen() * RGB),
                            (int) (color.getBlue() * RGB));
                    colorStrings = colorStrings + hex + "  ";
                }
                fileLines.add(colorStrings);
            }
            for (String line : fileLines) {
                writer.println(line);
            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        return msoeImage;
    }
    /**
     * Reads pixel values to file and returns image
     * Only works for BMSOE files
     */
    public static Image readBMSOE(Path path) throws IOException {
        FileInputStream fileInputStream = null;
        DataInputStream dataInputStream = null;
        WritableImage writableImage = null;
        PixelWriter pixelWriter;
        try {
            fileInputStream = new FileInputStream(path.toFile());
            dataInputStream = new DataInputStream(fileInputStream);
            if((char)fileInputStream.read() != 'B'){
                throw new IOException();
            } else if((char)fileInputStream.read() != 'M'){
                throw new IOException();
            } else if((char)fileInputStream.read() != 'S'){
                throw new IOException();
            } else if((char)fileInputStream.read() != 'O'){
                throw new IOException();
            } else if((char)fileInputStream.read() != 'E'){
                throw new IOException();
            }
            int imageWidth = dataInputStream.readInt();
            int imageHeight = dataInputStream.readInt();
            writableImage = new WritableImage(imageWidth, imageHeight);
            pixelWriter = writableImage.getPixelWriter();
            for(int y = 0; y < imageHeight; y++){
                for(int x = 0; x < imageWidth; x++){
                    int colorInt = dataInputStream.readInt();
                    pixelWriter.setColor(x, y, intToColor(colorInt));
                }
            }
        } finally {
            if(dataInputStream != null){
                dataInputStream.close();
            }
        }
        return writableImage;
    }


    /**
     * Writes new pixel values to file and returns image
     * Only works for BMSOE files
     */
    public static void writeBMSOE(Image image, Path path) throws IOException {
        ArrayList<Integer> pixelInts = new ArrayList<>();
        int imageHeight = (int) image.getHeight();
        int imageWidth = (int) image.getWidth();
        PixelReader pixels = image.getPixelReader();
        File bmsoeImage = path.toFile();
        FileOutputStream fileOutputStream = null;
        DataOutputStream dataOutputStream = null;
        try{
            fileOutputStream = new FileOutputStream(bmsoeImage);
            dataOutputStream = new DataOutputStream(fileOutputStream);
            fileOutputStream.write((int) 'B');
            fileOutputStream.write((int) 'M');
            fileOutputStream.write((int) 'S');
            fileOutputStream.write((int) 'O');
            fileOutputStream.write((int) 'E');
            dataOutputStream.writeInt(imageWidth);
            dataOutputStream.writeInt(imageHeight);
            for(int y = 0; y < imageHeight; y++){
                for(int x = 0; x < imageWidth; x++){
                    Color color = pixels.getColor(x, y);
                    int colorInt = colorToInt(color);
                    dataOutputStream.writeInt(colorInt);
                }
            }
        } finally {
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
        }
    }
    /**
     * Converts integer value to color and returns the color
     */
    private static Color intToColor(int color) {
        double red = ((color >> 16) & 0x000000FF)/255.0;
        double green = ((color >> 8) & 0x000000FF)/255.0;
        double blue = (color & 0x000000FF)/255.0;
        double alpha = ((color >> 24) & 0x000000FF)/255.0;
        return new Color(red, green, blue, alpha);
    }
    /**
     * Converts color to integer value and returns new int
     */
    private static int colorToInt(Color color) {
        int red = ((int)(color.getRed()*255)) & 0x000000FF;
        int green = ((int)(color.getGreen()*255)) & 0x000000FF;
        int blue = ((int)(color.getBlue()*255)) & 0x000000FF;
        int alpha = ((int)(color.getOpacity()*255)) & 0x000000FF;
        return (alpha << 24) + (red << 16) + (green << 8) + blue;
    }
}


