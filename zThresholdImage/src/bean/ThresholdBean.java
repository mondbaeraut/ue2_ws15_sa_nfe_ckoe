package bean;

import data.ImageEventReadable;
import interfaces.ImageEvent;
import interfaces.ImageEventHandlerImpl;
import interfaces.ImageListener;

import java.io.StreamCorruptedException;

/**
 * Created by mod on 11/23/15.
 */
public class ThresholdBean extends ImageEventHandlerImpl implements ImageListener {

    private ThresholdFilter thresholdFilter;
    private int percentage = 0;

    public ThresholdBean() {

    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    @Override
    public void onImage(ImageEvent e) {
        thresholdFilter = new ThresholdFilter(new ImageEventReadable<ImageEvent>(e), percentage);
        try {
            e = thresholdFilter.read();
        } catch (StreamCorruptedException e1) {
            e1.printStackTrace();
        }
        notifyAllListener(e);
    }
}
