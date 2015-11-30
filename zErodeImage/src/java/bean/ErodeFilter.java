package bean;

import Catalano.Imaging.Concurrent.Filters.Erosion;
import Catalano.Imaging.FastBitmap;
import data.ImageEventReadable;
import filter.ForwardingFilter;
import interfaces.ImageEvent;
import util.ImageViewer;

import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

/**
 * Created by mod on 11/29/15.
 */
public class ErodeFilter extends ForwardingFilter {
    private int radius = 0;

    public ErodeFilter(interfaces.Readable input, int radius) throws InvalidParameterException {
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
        FastBitmap fastBitmap = imageEvent.getFastBitmap();
        Erosion erosion = new Erosion(radius);
        erosion.applyInPlace(fastBitmap);
        imageEvent.setFastBitmap(fastBitmap);
        return imageEvent;
    }

    public static void main(String[] args) {
        FastBitmap fastBitmap = new FastBitmap("/home/mod/IdeaProjects/ue3_ws15_sa_nfe_ckoe/zErodeImage/result.png");
        ImageEventReadable readable = new ImageEventReadable(fastBitmap);
        ErodeFilter erodeFilter = new ErodeFilter(readable, 10);
        ImageEvent imageEvent = new ImageEvent(erodeFilter, fastBitmap);
        erodeFilter.process(imageEvent);
        ImageViewer imageViewer = new ImageViewer(imageEvent.getFastBitmap(), "Somename");
    }
}
