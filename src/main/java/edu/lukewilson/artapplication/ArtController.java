package edu.lukewilson.artapplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.scene.image.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

public class ArtController {

    public ImageView imgCanvas;
    @FXML
            private StackPane stackPane;
    Image image = new Image("/white.jpg");
    private Color color = Color.RED;
    //ImageUtils imageUtils = new ImageUtils();
    private WritableImage writableImage;
    Draw draw = new Draw();
    Fill fill = new Fill();
    ImageUtils imageUtils = new ImageUtils();
    private double lastX = -1;
    private double lastY = -1;
    private int brushSize = 5;
    boolean isFillBucket = false;


    public void initialize() {
        //set the mouse tracking
        stackPane.setOnMouseDragged(this::onDrag);
        stackPane.setOnMouseClicked(this::onClick);

        //set the imageview image
        imgCanvas.setImage(image);
        imgCanvas.setPreserveRatio(true);
        imgCanvas.setFitWidth(image.getWidth());
        imgCanvas.setFitHeight(image.getHeight());

        //change it to a writeable image so that we can write new pixels to it
        writableImage = new WritableImage((int) imgCanvas.getImage().getWidth(), (int) imgCanvas.getImage().getHeight());
        imgCanvas.setImage(writableImage);



    }


    public void onDrag(MouseEvent event) {
        //get the x and y of the mouse while being dragged
        double x = event.getX();
        double y = event.getY();

        //check if the x and y have changed. If not, don't do this stuff
        if (x != lastX || y != lastY) {
            lastX = x;
            lastY = y;

            int imageX = (int)(x / imgCanvas.getBoundsInLocal().getWidth() * imgCanvas.getImage().getWidth());
            int imageY = (int)(y / imgCanvas.getBoundsInLocal().getHeight() * imgCanvas.getImage().getHeight());

            draw.color(writableImage, imageX, imageY, brushSize, imgCanvas, color);

        }

    }

    public void onClick(MouseEvent event) {
        //get the x and y of the mouse while being dragged
        double x = event.getX();
        double y = event.getY();
        int imageX = (int)(x / imgCanvas.getBoundsInLocal().getWidth() * imgCanvas.getImage().getWidth());
        int imageY = (int)(y / imgCanvas.getBoundsInLocal().getHeight() * imgCanvas.getImage().getHeight());
        if (!isFillBucket) {
            draw.color(writableImage, imageX, imageY, brushSize, imgCanvas, color);
        } else {
            fill.fill(writableImage, imageX, imageY, imgCanvas, color);
        }

    }



    public void onEraser(ActionEvent actionEvent) {
        color = Color.WHITE;
        brushSize = 15;
        isFillBucket = false;
    }

    public void onBrush(ActionEvent actionEvent) {
        color = Color.RED;
        brushSize = 5;
        isFillBucket = false;
    }

    public void setFillTool(ActionEvent actionEvent) {
        isFillBucket = true;
    }
}