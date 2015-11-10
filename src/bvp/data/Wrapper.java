package bvp.data;

import Catalano.Imaging.FastBitmap;

/**
 * Created by mod on 11/10/15.
 */
public class Wrapper{
    private FastBitmap image;
    private Coordinate coordinate;

    public FastBitmap getImage() {
        return image;
    }
    public Coordinate getCoordinate(){
        return coordinate;
    }
}
