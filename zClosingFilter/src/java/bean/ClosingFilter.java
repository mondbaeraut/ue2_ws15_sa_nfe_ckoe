package bean;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Closing;
import filter.ForwardingFilter;
import interfaces.ImageEvent;
import interfaces.Readable;

import java.awt.image.BufferedImage;
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
        BufferedImage image = fastBitmap.toBufferedImage();
        FastBitmap newFastBitmap = new FastBitmap(image);
        newFastBitmap.toGrayscale();
        Closing closing = new Closing(radius);
        closing.applyInPlace(newFastBitmap);
        result = new ImageEvent(this, newFastBitmap);
        return result;
    }
}
