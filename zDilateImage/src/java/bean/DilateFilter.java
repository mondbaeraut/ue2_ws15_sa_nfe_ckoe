package bean;

import Catalano.Imaging.Concurrent.Filters.MorphologicGradientImage;
import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Dilatation;
import filter.ForwardingFilter;
import interfaces.*;
import interfaces.Readable;
import data.Coordinate;

import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

/**
 * Created by Christopher on 30.11.2015.
 */
public class DilateFilter<T> extends ForwardingFilter {
    private int radius;

    public DilateFilter(interfaces.Readable input, Writeable output) throws InvalidParameterException {
        super(input, output);
    }

    public DilateFilter(Readable input) throws InvalidParameterException {
        super(input);
    }

    public DilateFilter(Writeable output) throws InvalidParameterException {
        super(output);
    }

    @Override
    public ImageEvent read() throws StreamCorruptedException {
        return process((ImageEvent) readInput());
    }

    private ImageEvent process(ImageEvent imageEvent) throws StreamCorruptedException {
        FastBitmap temp = imageEvent.getFastBitmap();
        Dilatation dilatation = new Dilatation(radius);
        dilatation.applyInPlace(temp);
        imageEvent = new ImageEvent(this, temp);
        return imageEvent;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
