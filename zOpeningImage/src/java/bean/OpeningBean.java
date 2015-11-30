package bean;

import data.ImageEventReadable;
import interfaces.ImageEvent;
import interfaces.ImageEventHandlerImpl;
import interfaces.ImageListener;

import java.io.StreamCorruptedException;

/**
 * Created by mod on 11/23/15.
 */
public class OpeningBean extends ImageEventHandlerImpl implements ImageListener {
    private int radius = 0;

    public OpeningBean() {
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public void onImage(ImageEvent imageEvent) {
        OpeningFilter openingFilter = new OpeningFilter(new ImageEventReadable<ImageEvent>(imageEvent), radius);
        ImageEvent result = null;
        try {
            result = openingFilter.read();
            //ImageViewer imageViewer = new ImageViewer(e.getFastBitmap(),"Somename");
        } catch (StreamCorruptedException e1) {
            e1.printStackTrace();
        }
        notifyAllListener(result);
    }
}
