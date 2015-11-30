package bean;

import data.ImageEventReadable;
import interfaces.ImageEvent;
import interfaces.ImageEventHandlerImpl;
import interfaces.ImageListener;
import util.ImageViewer;

import java.io.StreamCorruptedException;

/**
 * Created by mod on 11/23/15.
 */
public class ThresholdBean extends ImageEventHandlerImpl implements ImageListener {

    private ThresholdFilter thresholdFilter;
    private int percentage = 0;
    private ImageEvent input = null;
    public ThresholdBean() {

    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
        if (input != null) {
            onImage(input);
        }
    }

    @Override
    public void onImage(ImageEvent e) {
        thresholdFilter = new ThresholdFilter(new ImageEventReadable<ImageEvent>(e), percentage);
        try {
            e = thresholdFilter.read();
            ImageViewer imageViewer = new ImageViewer(e.getFastBitmap(), "Somename");
        } catch (StreamCorruptedException e1) {
            e1.printStackTrace();
        }
        notifyAllListener(e);
    }
}
