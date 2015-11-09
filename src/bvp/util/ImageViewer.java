package bvp.util;


import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Threshold;

import javax.swing.*;

/**
 * Created by mod on 11/9/15.
 */
public class ImageViewer{
    private FastBitmap image;
    String imageInfo;
    public ImageViewer(FastBitmap image) {
        JOptionPane.showMessageDialog(null, image.toIcon(), "Original image", JOptionPane.PLAIN_MESSAGE);
    }


    public static void main(String[] args) {
        ImageViewer imageViewer = new ImageViewer(ImageLoader.loadImage("loetstellen.jpg"));

    }
}
