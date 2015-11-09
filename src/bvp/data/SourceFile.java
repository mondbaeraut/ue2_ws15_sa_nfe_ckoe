package bvp.data;

import Catalano.Imaging.Concurrent.Filters.Threshold;
import Catalano.Imaging.FastBitmap;

import bvp.util.ImageViewer;

import java.io.StreamCorruptedException;

/**
 * Created by mod on 11/9/15.
 */
public class SourceFile implements interfaces.Readable{
    FastBitmap image;
    public SourceFile(FastBitmap image) {
        this.image = image;

    }

    @Override
    public Object read() throws StreamCorruptedException {
        return image;
    }

    public static void main(String[] args) {
        SourceFile s = new SourceFile(new FastBitmap("loetstellen.jpg"));
    }
}
