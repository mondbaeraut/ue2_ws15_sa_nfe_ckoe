package bean;

import data.ImageEventReadable;
import interfaces.ImageEvent;
import interfaces.ImageEventHandlerImpl;
import interfaces.ImageListener;

import java.io.StreamCorruptedException;

/**
 * Created by mod on 11/23/15.
 */
public class DilateBean extends ImageEventHandlerImpl implements ImageListener {
    private int radius = 0;
    private DilateFilter dilateFilter = null;
    private ImageEvent input = null;
    public DilateBean(){
    }

    @Override
    public void onImage(ImageEvent e) {
        input = e;
        dilateFilter = new DilateFilter(new ImageEventReadable<ImageEvent>(e), radius);
        try {
            e = dilateFilter.read();
        } catch (StreamCorruptedException e1) {
            e1.printStackTrace();
        }
        notifyAllListener(e);

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


}
