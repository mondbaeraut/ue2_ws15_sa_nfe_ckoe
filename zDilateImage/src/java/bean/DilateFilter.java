package bean;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Dilatation;
import data.ImageEventReadable;
import filter.ForwardingFilter;
import interfaces.ImageEvent;
import interfaces.Readable;
import interfaces.Writeable;

import java.awt.image.BufferedImage;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

/**
 * Created by Christopher on 30.11.2015.
 */
public class DilateFilter<T> extends ForwardingFilter {
    private int radius = 0;

    public DilateFilter(interfaces.Readable input, Writeable output) throws InvalidParameterException {
        super(input, output);
    }

    public DilateFilter(Readable input) throws InvalidParameterException {
        super(input);
    }

    public DilateFilter(Writeable output) throws InvalidParameterException {
        super(output);
    }

    public DilateFilter(ImageEventReadable<ImageEvent> input, int radius) {
        super(input);
        this.radius = radius;
    }

    @Override
    public ImageEvent read() throws StreamCorruptedException {
        return process((ImageEvent) readInput());
    }

    private ImageEvent process(ImageEvent imageEvent) throws StreamCorruptedException {
        FastBitmap fastBitmap = imageEvent.getFastBitmap();
        BufferedImage image = fastBitmap.toBufferedImage();
        FastBitmap newFastBitmap = new FastBitmap(image);
        newFastBitmap.toGrayscale();
        Dilatation dilatation = new Dilatation(radius);
        dilatation.applyInPlace(newFastBitmap);
        imageEvent = new ImageEvent(this, newFastBitmap);
        return imageEvent;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
