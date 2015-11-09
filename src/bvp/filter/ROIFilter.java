package bvp.filter;

import Catalano.Imaging.FastBitmap;
import bvp.data.SourceFile;
import bvp.util.ImageLoader;
import bvp.util.ImageViewer;
import filter.AbstractFilter;
import interfaces.*;
import interfaces.Readable;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

/**
 * Created by mod on 11/9/15.
 * TODO: Größe und Position des Rechteckes angeben...
 */
public class ROIFilter<T>extends AbstractFilter{



    public ROIFilter(interfaces.Readable input) throws InvalidParameterException {
        super(input);
    }

    public ROIFilter(Writeable output) throws InvalidParameterException {
        super(output);
    }

    public ROIFilter(Readable input, Writeable output) throws InvalidParameterException {
        super(input, output);
    }

    @Override
    public Object read() throws StreamCorruptedException {
        return getROI();
    }

    @Override
    public void run() {

    }

    @Override
    public void write(Object value) throws StreamCorruptedException {

    }

   private FastBitmap getROI(){
       FastBitmap temp = null;
       try {
           BufferedImage bufferedImage = ((FastBitmap)readInput()).toBufferedImage();
           temp = new FastBitmap(bufferedImage.getSubimage(0,60,448,50));
       } catch (StreamCorruptedException e) {
           e.printStackTrace();
       }
       return temp;
   }

    public static void main(String[] args) {
        FastBitmap fastBitmap = ImageLoader.loadImage("loetstellen.jpg");
        SourceFile sourceFile = new SourceFile(fastBitmap);
        ROIFilter roiFilter = new ROIFilter(sourceFile);
        ImageViewer viewer = new ImageViewer(roiFilter.getROI());

    }
}
