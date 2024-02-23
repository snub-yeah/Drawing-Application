package edu.lukewilson.artapplication;

import java.util.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;

public class Undo {
    Deque<WritableImage> history = new ArrayDeque<>();
    private boolean firstUndo = true;


    public void addToUndo(WritableImage writableImage) {
        if (history.size() < 20) {
            history.add(copyImage(writableImage));
        } else {
            history.removeFirst();
            history.add(copyImage(writableImage));
        }
        setFirstUndo(true);


    }

    private WritableImage copyImage(WritableImage image) {
        int height=(int)image.getHeight();
        int width=(int)image.getWidth();
        PixelReader pixelReader=image.getPixelReader();
        WritableImage writableImage = new WritableImage(width,height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                Color color = pixelReader.getColor(x, y);
                pixelWriter.setColor(x, y, color);
            }
        }
        return writableImage;
    }

    public WritableImage getUndo() {
        if (firstUndo) {
            history.removeLast();
        }

        firstUndo = false;
        return history.getLast();

    }

    public void removeLast() {
        history.removeLast();
    }

    public void setFirstUndo(boolean bool) {
        firstUndo = bool;
    }

    /**
     * Method for getting size of queue. This is important so that you can't undo when there is nothing to undo
     * @return - size of queue
     */
    public int getSizeOfQueue() {
        return history.size();
    }

}
