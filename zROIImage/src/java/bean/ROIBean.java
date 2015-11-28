package bean;


import data.ImageEventReadable;
import interfaces.ImageEvent;
import interfaces.ImageEventHandlerImpl;
import interfaces.ImageListener;

import java.io.StreamCorruptedException;

/**
 * Created by mod on 11/23/15.
 */
public class ROIBean extends ImageEventHandlerImpl implements ImageListener {
    private int roiX = 0;
    private int roiY = 0;
    private int height = 0;
    private int weight = 0;
    private ROIFilter roiFilter;


    public ROIBean() {

    }

    /*
    -------------------------- SETTER GETTER--------------------------------------------
     */
    public int getroiX() {
        return roiX;
    }

    public void setroiX(int roiX) {
        this.roiX = roiX;
    }

    public int getroiY() {
        return roiY;
    }

    public void setroiY(int roiY) {
        this.roiY = roiY;
    }

    public int getheight() {
        return height;
    }

    public void setheight(int height) {
        this.height = height;
    }

    public int getweight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    @Override
    public void onImage(ImageEvent e) {
        roiFilter = new ROIFilter(new ImageEventReadable<ImageEvent>(e));
        try {
            e = roiFilter.read();
        } catch (StreamCorruptedException e1) {
            e1.printStackTrace();
        }
        notifyAllListener(e);
    }
}
