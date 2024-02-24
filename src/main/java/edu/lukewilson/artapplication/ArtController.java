package edu.lukewilson.artapplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.concurrent.TimeUnit;

public class ArtController {

    public ImageView imgCanvas;
    public Label lblBrushSize;
    @FXML
            private StackPane stackPane;
    Image image = new Image("/white.jpg");
    private Color color = Color.RED;
    //ImageUtils imageUtils = new ImageUtils();
    private WritableImage writableImage;
    Draw draw = new Draw();
    Fill fill = new Fill();
    Undo undo = new Undo();
    ImageUtils imageUtils = new ImageUtils();
    final KeyCombination ctrlZ = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
    private double lastX = -1;
    private double lastY = -1;
    private int brushSize = 2;
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
        undo.addToUndo(writableImage);



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
        undo.addToUndo(writableImage);

    }

    /**
     * Handles keyboard shortcuts for undo, brush, and eraser
     * @param keyEvent - the keyboard shortcut pressed
     */
    public void handle(KeyEvent keyEvent) {
        if (ctrlZ.match(keyEvent)) {
            onUndo(new ActionEvent());
        } else if (keyEvent.getCode() == KeyCode.B) {
            onBrush(new ActionEvent());
        } else if (keyEvent.getCode() == KeyCode.E) {
            onEraser(new ActionEvent());
        } else if (keyEvent.getCode() == KeyCode.G) {
            setFillTool(new ActionEvent());
        } else if (keyEvent.getCode() == KeyCode.F) {
                try {
                    decreaseBrush();
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (Exception e) {
                    System.out.println("Interrupted");
                }

        } else if (keyEvent.getCode() == KeyCode.R) {
            try {
                increaseBrush();
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (Exception e) {
                System.out.println("Interrupted");
            }
        }

    }

    public void onEraser(ActionEvent actionEvent) {
        color = Color.TRANSPARENT;
        isFillBucket = false;
    }

    public void onBrush(ActionEvent actionEvent) {
        color = Color.RED;
        isFillBucket = false;
    }

    public void setFillTool(ActionEvent actionEvent) {
        isFillBucket = true;
    }

    public void onDragEnd(MouseDragEvent mouseDragEvent) {
        undo.addToUndo(writableImage);

    }

    public void onUndo(ActionEvent actionEvent) {
        try {
        if (undo.getSizeOfQueue() >=1) {
            copyWriteableImageWithUndo(undo.getUndo());
            imgCanvas.setImage(writableImage);
            undo.removeLast();
            if (undo.getSizeOfQueue() ==0) {
                undo.addToUndo(writableImage);
            }
        } } catch (Exception e) {
            undo.addToUndo(writableImage);
        }
    }

    private void copyWriteableImageWithUndo(WritableImage _writableImage) {
        PixelReader pixelReader = _writableImage.getPixelReader();
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int x = 0; x<_writableImage.getWidth(); x++) {
            for (int y = 0; y<_writableImage.getHeight(); y++) {
                pixelWriter.setColor(x, y, pixelReader.getColor(x, y));
            }
        }
    }

    public void decreaseBrush() {
        if (brushSize -1 > 1) {
            brushSize--;
            lblBrushSize.setText(String.valueOf(brushSize));
        }
    }

    public void increaseBrush() {
        if (brushSize +1 < 51) {
            brushSize++;
            lblBrushSize.setText(String.valueOf(brushSize));
        }
    }
}