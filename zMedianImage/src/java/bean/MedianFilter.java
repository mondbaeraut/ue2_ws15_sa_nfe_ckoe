package bean;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Median;
import data.ImageEventReadable;
import filter.ForwardingFilter;
import interfaces.ImageEvent;
import util.ImageViewer;

import java.awt.image.BufferedImage;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

/**
 * Created by mod on 11/29/15.
 */
public class MedianFilter extends ForwardingFilter {
    private int radius = 0;

    public MedianFilter(interfaces.Readable input, int radius) throws InvalidParameterException {
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
        Median medianCut = new Median(radius);
        FastBitmap fastBitmap = imageEvent.getFastBitmap();
        BufferedImage image = fastBitmap.toBufferedImage();
        FastBitmap newFastBitmap = new FastBitmap(image);
        newFastBitmap.toGrayscale();
        ImageEvent result = null;
        if (fastBitmap != null) {
            fastBitmap.toGrayscale();
            medianCut.applyInPlace(newFastBitmap);
            result = new ImageEvent(this, newFastBitmap);
        }
        return result;
    }

    public static void main(String[] args) {
        FastBitmap fastBitmap = new FastBitmap("/home/mod/IdeaProjects/ue3_ws15_sa_nfe_ckoe/zErodeImage/result.png");
        ImageEventReadable readable = new ImageEventReadable(fastBitmap);
        MedianFilter erodeFilter = new MedianFilter(readable, 12);
        ImageEvent imageEvent = new ImageEvent(erodeFilter, fastBitmap);
        erodeFilter.process(imageEvent);
        ImageViewer imageViewer = new ImageViewer(imageEvent.getFastBitmap(), "Somename");
    }
}
