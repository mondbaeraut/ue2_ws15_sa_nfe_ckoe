package bean;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Closing;
import filter.ForwardingFilter;
import interfaces.ImageEvent;
import interfaces.Readable;

import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

/**
 * Created by mod on 11/29/15.
 */
public class ClosingFilter extends ForwardingFilter {
    private int radius;

    public ClosingFilter(Readable input, int radius) throws InvalidParameterException {
        super(input);
        this.radius = radius;
    }

    @Override
    public ImageEvent read() throws StreamCorruptedException {
        return process((ImageEvent) readInput());
    }

    private ImageEvent process(ImageEvent imageEvent) {
        ImageEvent result = null;
        FastBitmap fastBitmap = imageEvent.getFastBitmap();
        Closing closing = new Closing(radius);
        closing.applyInPlace(fastBitmap);
        result = new ImageEvent(this, fastBitmap);
        return result;
    }
}
