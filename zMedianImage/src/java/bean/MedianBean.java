package bean;

import data.ImageEventReadable;
import interfaces.ImageEvent;
import interfaces.ImageEventHandlerImpl;
import interfaces.ImageListener;

import java.io.StreamCorruptedException;

/**
 * Created by mod on 11/23/15.
 */
public class MedianBean extends ImageEventHandlerImpl implements ImageListener {
    private int radius = 0;
    private MedianFilter medianFilter = null;

    public MedianBean() {
    }

    /*
     -------------------------- SETTER GETTER--------------------------------------------
      */
    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public void onImage(ImageEvent e) {
        medianFilter = new MedianFilter(new ImageEventReadable<ImageEvent>(e), radius);
        try {
            e = medianFilter.read();
        } catch (StreamCorruptedException e1) {
            e1.printStackTrace();
        }
        notifyAllListener(e);
    }
}
