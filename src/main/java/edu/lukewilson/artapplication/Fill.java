package edu.lukewilson.artapplication;

import javafx.scene.image.*;
import javafx.scene.paint.*;


import java.util.*;

public class Fill {
    private Color filledColor;

    HashSet<Integer[]> map = new HashSet<>();
    public void fill(WritableImage writableImage, int startX, int startY, ImageView imgCanvas, Color color) {
        PixelReader pixelReader = writableImage.getPixelReader();
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        Color targetColor = pixelReader.getColor(startX, startY);

        // if it's already the filled color, do nothing lmao
        if (targetColor.equals(color)) {
            return;
        }

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{startX, startY});

        while (!queue.isEmpty()) {
            int[] pixel = queue.poll();
            int x = pixel[0];
            int y = pixel[1];

            // make sure it's in the bounds
            if (x >= 0 && x < writableImage.getWidth() && y >= 0 && y < writableImage.getHeight()) {
                Color pixelColor = pixelReader.getColor(x, y);
                // If the pixel color matches the target color, fill it with the new color
                if (pixelColor.equals(targetColor)) {
                    pixelWriter.setColor(x, y, color);

                    // add pixels in cross shape to queue
                    queue.add(new int[]{x - 1, y}); // left
                    queue.add(new int[]{x + 1, y}); // right
                    queue.add(new int[]{x, y - 1}); // up
                    queue.add(new int[]{x, y + 1}); // down
                }
            }
        }

        imgCanvas.setImage(writableImage);

    }

    public void setFilledColor(Color color){
        filledColor = color;
    }

}
