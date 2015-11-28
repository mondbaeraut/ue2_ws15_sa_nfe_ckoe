package interfaces;

/**
 * Created by mod on 11/28/15.
 */
public class ImageEventHandlerImpl implements ImageEventHandler {
    private ImageEventPool imageEventPool = new ImageEventPool();
    ;

    public ImageEventHandlerImpl() {
    }

    @Override
    public void addImageListener(ImageListener listener) {
        imageEventPool.addImageEventHandler(listener);
    }

    @Override
    public void removeImageListener(ImageListener listener) {
        imageEventPool.removeImageEventHandler(listener);
    }

    public void notifyAllListener(ImageEvent imageEvent) {
        imageEventPool.notifyAll(imageEvent);
    }
}
