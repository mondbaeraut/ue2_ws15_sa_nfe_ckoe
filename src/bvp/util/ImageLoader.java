package bvp.util;

import Catalano.Imaging.FastBitmap;


/**
 * Created by mod on 11/9/15.
 */
public class ImageLoader {

    public ImageLoader() {

    }

    public static FastBitmap loadImage(String filepath){
        FastBitmap fb = new FastBitmap(filepath);
        fb.toGrayscale();
        return fb;
    }

    public static void main(String[] args) {
        FastBitmap img = ImageLoader.loadImage("loetstellen.jpg");
        System.out.println();
    }
}
