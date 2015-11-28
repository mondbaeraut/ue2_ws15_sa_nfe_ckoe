package interfaces;

import Catalano.Imaging.FastBitmap;
import data.Coordinate;

import java.util.EventObject;

/**
 * Created by mod on 11/25/15.
 */
public class ImageEvent extends EventObject {
    private FastBitmap fastBitmap;
    private Coordinate coordinate;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ImageEvent(Object source, FastBitmap fastBitmap) {
        super(source);

        this.fastBitmap = fastBitmap;
    }

    public FastBitmap getFastBitmap() {
        return fastBitmap;
    }

    public void setFastBitmap(FastBitmap fastBitmap) {
        this.fastBitmap = fastBitmap;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
