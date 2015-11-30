package bean;

import Catalano.Imaging.Concurrent.Filters.Opening;
import Catalano.Imaging.FastBitmap;
import filter.ForwardingFilter;
import interfaces.ImageEvent;

import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

/**
 * Created by mod on 11/30/15.
 */
public class OpeningFilter extends ForwardingFilter {
    private int radius = 0;

    public OpeningFilter(interfaces.Readable input, int radius) throws InvalidParameterException {
        super(input);
        this.radius = radius;
    }

    @Override
    public ImageEvent read() throws StreamCorruptedException {
        return process((ImageEvent) readInput());
    }

    @Override
    public void write(Object value) throws StreamCorruptedException {
        super.write(value);
    }

    private ImageEvent process(ImageEvent e) {
        Opening openingFilter = new Opening(radius);
        FastBitmap fastBitmap = e.getFastBitmap();
        openingFilter.applyInPlace(fastBitmap);
        ImageEvent result = new ImageEvent(this, fastBitmap);
        return result;
    }
}
