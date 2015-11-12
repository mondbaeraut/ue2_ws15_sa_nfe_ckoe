package bvp.util;


import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Threshold;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.nio.file.Path;

/**
 * Created by mod on 11/9/15.
 */
public class ImageViewer{
    private FastBitmap image;
    String imageInfo;
    public ImageViewer(FastBitmap image,String savename) {
        saveImage(image,savename);
        JOptionPane.showMessageDialog(null, image.toIcon(), "Original image", JOptionPane.PLAIN_MESSAGE);
    }

    private void saveImage(FastBitmap image,String savename) {
        try {
            BufferedImage bi = image.toBufferedImage();
            File outputfile = new File("Documentation/Images/" + savename + ".png");
            ImageIO.write(bi, "png", outputfile);
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        ImageViewer imageViewer = new ImageViewer(ImageLoader.loadImage("loetstellen.jpg"),"Imageviewer");

    }
}
