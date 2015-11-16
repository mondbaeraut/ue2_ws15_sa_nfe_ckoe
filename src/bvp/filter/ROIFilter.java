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
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by mod on 11/9/15.
 * TODO: Größe und Position des Rechteckes angeben...
 */
public class ROIFilter<T> extends AbstractFilter {
    private Coordinate coordinate = new Coordinate(0, 50);
    private Rectangle rectangle = new Rectangle(448, 50);
    /*
    *b)
     */
    private T input;

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

    public ROIFilter(Readable input, Writeable output, Coordinate coordinate, Rectangle rectangle) throws InvalidParameterException {
        super(input, output);
        this.coordinate = coordinate;
        this.rectangle = rectangle;
    }

    @Override
    public Object read() throws StreamCorruptedException {
        return getROI((FastBitmap)readInput());
    }

    @Override
    public void run() {
        try {
            FastBitmap fastBitmap = (FastBitmap)readInput();
            BlockingQueue blockingQueueout = (BlockingQueue)readOutput();
            blockingQueueout.put(getROI(fastBitmap));
            System.out.println("read Picture!!");
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Object value) throws StreamCorruptedException {

    }

    private PackageCoordinate getROI(FastBitmap bufferedImage) throws StreamCorruptedException {
        FastBitmap temp = null;

        temp = new FastBitmap((bufferedImage.toBufferedImage()).getSubimage(coordinate.getX(), coordinate.getY(), rectangle.width, rectangle.height));
        return new PackageCoordinate(coordinate, temp);
    }

    public static void main(String[] args) {
        FastBitmap fastBitmap = ImageLoader.loadImage("loetstellen.jpg");
        SourceFile sourceFile = new SourceFile(fastBitmap);
        ROIFilter roiFilter = new ROIFilter(sourceFile, new Coordinate(0, 50), new Rectangle(448, 50));
    }
}
