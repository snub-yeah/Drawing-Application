package edu.lukewilson.artapplication;

import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.paint.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;

public class ImageUtils {

    public Image convertToImage(WritableImage writableImage) {
        PixelReader pixelReader = writableImage.getPixelReader();
        int width = (int) writableImage.getWidth();
        int height = (int) writableImage.getHeight();

        WritableImage newImage = new WritableImage(width, height);
        PixelWriter pixelWriter = newImage.getPixelWriter();

        for (int y = 0; y<height;y++) {
            for (int x = 0; x<width;x++) {
                Color color = pixelReader.getColor(x,y);
                pixelWriter.setColor(x, y, color);
            }
        }
        return newImage;

    }

    public BufferedImage convertToBufferedImage(Image fxImage) {
        int width = (int) fxImage.getWidth();
        int height = (int) fxImage.getHeight();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        WritableRaster raster = bufferedImage.getRaster();
        DataBufferInt dataBuffer = (DataBufferInt) raster.getDataBuffer();
        int[] data = dataBuffer.getData();

        PixelReader pixelReader = fxImage.getPixelReader();
        pixelReader.getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), data, 0, width);

        return bufferedImage;
    }
/*
    public Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }

    */
}


