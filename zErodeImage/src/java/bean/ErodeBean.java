package bean;

import data.ImageEventReadable;
import interfaces.ImageEvent;
import interfaces.ImageEventHandlerImpl;
import interfaces.ImageListener;

import java.io.StreamCorruptedException;

/**
 * Created by mod on 11/23/15.
 */
public class ErodeBean extends ImageEventHandlerImpl implements ImageListener {
    private int radius = 0;
    private ErodeFilter erodeFilter = null;
    private ImageEvent input = null;
    public ErodeBean() {
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        if (input != null) {
            onImage(input);
        }
    }

    @Override
    public void onImage(ImageEvent e) {
        erodeFilter = new ErodeFilter(new ImageEventReadable<ImageEvent>(e), radius);
        ImageEvent result = null;
        input = e;
        try {
            result = erodeFilter.read();
        } catch (StreamCorruptedException e1) {
            e1.printStackTrace();
        }
        notifyAllListener(result);
    }
}
