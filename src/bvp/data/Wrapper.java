package bvp.data;

import Catalano.Imaging.FastBitmap;

/**
 * Created by mod on 11/10/15.
 */
public class Wrapper{
    private FastBitmap image;
    private FastBitmap originImage;
    private Coordinate coordinate;


    public Wrapper(FastBitmap image, FastBitmap originImage, Coordinate coordinate) {
        this.image = image;
        this.originImage = originImage;
        this.coordinate = coordinate;
    }

    public FastBitmap getImage() {
        return image;
    }
    public Coordinate getCoordinate(){
        return coordinate;
    }

    public void setImage(FastBitmap image) {
        this.image = image;
    }

    public FastBitmap getOriginImage() {
        return originImage;
    }

    public void setOriginImage(FastBitmap originImage) {
        this.originImage = originImage;
    }
}
