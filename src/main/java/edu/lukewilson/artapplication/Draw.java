package edu.lukewilson.artapplication;

import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Draw {

    public void color(WritableImage writableImage, int x, int y, int brushSize, ImageView imgCanvas, Color color) {
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        // Set the color of the pixel at (imageX, imageY) to targetColor
        for (int i = x-brushSize; i<x +brushSize; i++) {
            for (int j = y -brushSize; j<y +brushSize; j++)
                try {
                    pixelWriter.setColor(i, j, color);
                    imgCanvas.setImage(writableImage);
                } catch (Exception e) {

                }
        }
    }
}
