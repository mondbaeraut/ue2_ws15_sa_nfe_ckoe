package interfaces;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mod on 11/26/15.
 */
public class ImageEventPool {

    private List<ImageListener> imageListeners;

    public ImageEventPool() {
        imageListeners = new LinkedList<>();
    }

    public void addImageEventHandler(ImageListener imageListener) {
        imageListeners.add(imageListener);
    }

    public void removeImageEventHandler(ImageListener imageListener) {
        imageListeners.remove(imageListener);
    }

    public void notifyAll(ImageEvent imageEvent) {
        System.out.println(imageListeners.size());
        for (ImageListener imageListener : imageListeners) {
            imageListener.onImage(imageEvent);
        }
    }

    public boolean notify(ImageListener imageEventHandler, ImageEvent imageEvent) {
        ImageListener imageEventHandlertemp = imageListeners.get(imageListeners.indexOf(imageEventHandler));
        if (imageEventHandlertemp != null) {
            imageEventHandlertemp.onImage(imageEvent);
            return true;
        }
        return false;
    }
}
