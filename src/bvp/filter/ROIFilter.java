package bvp.filter;

import Catalano.Imaging.FastBitmap;
import bvp.data.Coordinate;
import bvp.data.PackageCoordinate;
import bvp.data.SourceFile;
import bvp.util.ImageLoader;
import bvp.util.ImageViewer;
import filter.AbstractFilter;
import interfaces.*;
import interfaces.Readable;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

/**
 * Created by mod on 11/9/15.
 * TODO: Größe und Position des Rechteckes angeben...
 */
public class ROIFilter<T> extends AbstractFilter {
    private Coordinate coordinate = new Coordinate(0, 50);
    private Rectangle rectangle = new Rectangle(448, 50);

    public ROIFilter(interfaces.Readable input, Coordinate coordinate, Rectangle rectangle) throws InvalidParameterException {
        super(input);
        this.coordinate = coordinate;
        this.rectangle = rectangle;
    }

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

    private PackageCoordinate getROI() throws StreamCorruptedException {
        FastBitmap temp = null;


        BufferedImage bufferedImage = ((FastBitmap) readInput()).toBufferedImage();
        temp = new FastBitmap(bufferedImage.getSubimage(coordinate.getX(), coordinate.getY(), rectangle.width, rectangle.height));
        return new PackageCoordinate(coordinate, temp);
    }

    public static void main(String[] args) {
        FastBitmap fastBitmap = ImageLoader.loadImage("loetstellen.jpg");
        SourceFile sourceFile = new SourceFile(fastBitmap);
        ROIFilter roiFilter = new ROIFilter(sourceFile, new Coordinate(0, 50), new Rectangle(448, 50));
        try {
            ImageViewer viewer = new ImageViewer((FastBitmap) ((PackageCoordinate) roiFilter.getROI()).getValue(), "roi");
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }

    }
}
