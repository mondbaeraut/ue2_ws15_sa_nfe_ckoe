package bean;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Morph;
import Catalano.Imaging.Filters.Threshold;
import filter.ForwardingFilter;
import util.ImageViewer;

import java.security.InvalidParameterException;

/**
 * Created by mod on 11/30/15.
 */
public class OverlapFilter extends ForwardingFilter {
    public OverlapFilter(interfaces.Readable input) throws InvalidParameterException {
        super(input);
    }

    public static void main(String[] args) {

        FastBitmap fastBitmap1 = new FastBitmap("loetstellen.jpg");
        Threshold threshold = new Threshold(50);
        fastBitmap1.toGrayscale();
        threshold.applyInPlace(fastBitmap1);
        FastBitmap fastBitmap2 = new FastBitmap("loetstellen.jpg");
        Morph morph = new Morph(fastBitmap1);
        morph.setSourcePercent(0.0);
        morph.applyInPlace(fastBitmap2);
        ImageViewer imageViewer1 = new ImageViewer(fastBitmap1, "name");
        ImageViewer imageViewer2 = new ImageViewer(fastBitmap2, "name");
    }
}
