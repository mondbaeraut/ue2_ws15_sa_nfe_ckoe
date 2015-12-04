package bean;


import data.Coordinate;
import data.ImageEventReadable;
import interfaces.ImageEvent;
import interfaces.ImageEventHandlerImpl;
import interfaces.ImageListener;

import java.awt.*;
import java.io.StreamCorruptedException;

/**
 * Created by mod on 11/23/15.
 */
public class ROIBean extends ImageEventHandlerImpl implements ImageListener {
    private int roiX = 0;
    private int roiY = 0;
    private int height = 0;
    private int width = 0;
    private ROIFilter roiFilter;
    private ImageEvent input = null;

    public ROIBean() {

    }

    /*
    -------------------------- SETTER GETTER--------------------------------------------
     */
    public int getRoiX() {
        return roiX;
    }

    public void setRoiX(int roiX) {
        this.roiX = roiX;
        update(input);
    }

    public int getRoiY() {
        return roiY;
    }

    public void setRoiY(int roiY) {
        this.roiY = roiY;
        update(input);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if (height <= 0) {
            height = 1;
        }
        this.height = height;
        update(input);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        if (width <= 0) {
            width = 1;
        }
        this.width = width;
        update(input);
    }

    private void update(ImageEvent imageEvent) {
        if (imageEvent != null) {
            onImage(imageEvent);
        }
    }

    @Override
    public void onImage(ImageEvent e) {
        if (e.getFastBitmap() != null) {
            roiFilter = new ROIFilter(new ImageEventReadable<ImageEvent>(e), new Coordinate(roiX, roiY), new Rectangle(width, height));
            input = e;
            ImageEvent result = null;
            try {
                result = roiFilter.read();
            } catch (StreamCorruptedException e1) {
                e1.printStackTrace();
            }
            notifyAllListener(result);
        }
    }
}
