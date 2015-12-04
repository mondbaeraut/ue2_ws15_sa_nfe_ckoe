package bean;

import bvp.data.Coordinate;
import bvp.filter.CentroidsFilter;
import data.ImageEventReadable;
import interfaces.ImageEvent;
import interfaces.ImageEventHandlerImpl;
import interfaces.ImageListener;
import util.ImageViewer;

import java.io.StreamCorruptedException;
import java.util.List;

/**
 * Created by Christopher on 04.12.2015.
 */
public class CentroidBean extends ImageEventHandlerImpl implements ImageListener {

    private CentroidsFilter thresholdFilter;
    private ImageEvent input = null;
    public CentroidBean() {

    }


    @Override
    public void onImage(ImageEvent e) {
        thresholdFilter = new CentroidsFilter(new ImageEventReadable<ImageEvent>(e));
        try {
            e = (ImageEvent) thresholdFilter.read();
            ImageViewer imageViewer = new ImageViewer(e.getFastBitmap(), "Somename");
        } catch (StreamCorruptedException e1) {
            e1.printStackTrace();
        }
        notifyAllListener(e);
    }
}
