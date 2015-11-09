import Catalano.Imaging.Concurrent.Filters.MaximumEntropyThreshold;
import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Intersect;
import Catalano.Imaging.Filters.Threshold;

import javax.swing.*;

/**
 * Created by mod on 11/9/15.
 */
public class Main2 {
    public static void main(String[] args) {
        FastBitmap fastBitmap = new FastBitmap("loetstellen.jpg");
        FastBitmap fastBitmap2 = new FastBitmap("loetstellen.jpg");
        fastBitmap.toGrayscale();
        MaximumEntropyThreshold threshold = new MaximumEntropyThreshold();
        threshold.applyInPlace(fastBitmap);
        fastBitmap.toRGB();
        fastBitmap2.toRGB();
      //  Intersect intersect = new Intersect(fastBitmap);
        // intersect.setOverlayImage(fastBitmap2);
        JOptionPane.showMessageDialog(null, fastBitmap.toIcon(), "Original image", JOptionPane.PLAIN_MESSAGE);
    }
}
