package data;

import java.io.StreamCorruptedException;

/**
 * Created by mod on 11/27/15.
 */
public class ImageEventReadable<ImageEvent> implements interfaces.Readable {
    ImageEvent imageEvent = null;

    public ImageEventReadable(ImageEvent imageEvent) {
        this.imageEvent = imageEvent;
    }

    @Override
    public ImageEvent read() throws StreamCorruptedException {
        return imageEvent;
    }
}
