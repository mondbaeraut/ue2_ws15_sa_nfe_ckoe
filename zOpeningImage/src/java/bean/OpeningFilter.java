package bean;

import Catalano.Imaging.Concurrent.Filters.Opening;
import Catalano.Imaging.FastBitmap;
import filter.ForwardingFilter;
import interfaces.ImageEvent;

import java.awt.image.BufferedImage;
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

    private ImageEvent process(ImageEvent imageEvent) {
        Opening openingFilter = new Opening(radius);
        FastBitmap fastBitmap = imageEvent.getFastBitmap();
        BufferedImage image = fastBitmap.toBufferedImage();
        FastBitmap newFastBitmap = new FastBitmap(image);
        newFastBitmap.toGrayscale();
        ImageEvent result = null;
        if (fastBitmap != null) {
            openingFilter.applyInPlace(newFastBitmap);
            result = new ImageEvent(this, newFastBitmap);
        }
        return result;
    }
}
