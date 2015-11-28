package interfaces;

import java.util.EventListener;

/**
 * Created by mod on 11/25/15.
 */
public interface ImageListener extends EventListener {
    void onImage(ImageEvent e);
}