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
    private ImageEvent input = null;

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
        update(input);
    }

    public int getroiY() {
        return roiY;
    }

    public void setroiY(int roiY) {
        this.roiY = roiY;
        update(input);
    }

    public int getheight() {
        return height;
    }

    public void setheight(int height) {
        this.height = height;
        update(input);
    }

    public int getweight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
        update(input);
    }

    private void update(ImageEvent imageEvent) {
        System.out.println("check if null?");
        if (imageEvent != null) {
            System.out.println("update called");
            onImage(imageEvent);
        }
    }

    @Override
    public void onImage(ImageEvent e) {
        roiFilter = new ROIFilter(new ImageEventReadable<ImageEvent>(e));
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
