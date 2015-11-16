package bvp.filter;


import Catalano.Core.IntRange;
import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.ReplaceColor;
import bvp.data.*;
import bvp.data.Package;
import bvp.pipe.PipeBufferImpl;
import bvp.pipe.PipeImpl;
import bvp.util.ImageLoader;
import bvp.util.ImageViewer;

import filter.AbstractFilter;
import interfaces.Readable;


import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

/**
 * Created by mod on 11/9/15.
 */
public class ThresholdFilter extends AbstractFilter {
    public ThresholdFilter(Readable input) throws InvalidParameterException {
        super(input);
    }

    @Override
    public Object read() throws StreamCorruptedException{
        return getTrashold((FastBitmap) readInput());
    }

    @Override
    public void run() {
        try {
            while (true) {
                FastBitmap fastBitmap = (FastBitmap) readInput();
                ((PipeBufferImpl) readOutput()).write(getTrashold(fastBitmap));
            }
        }catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Object value) throws StreamCorruptedException {

    }

    private PackageCoordinate getTrashold(FastBitmap fastBitmap) throws StreamCorruptedException{
        Package pack = null;
        FastBitmap result = fastBitmap;
        ReplaceColor colorFiltering = new ReplaceColor(new IntRange(0, 36), new IntRange(0, 36), new IntRange(0, 36));
        result.toRGB();
        colorFiltering.ApplyInPlace(result, 255, 255, 255);
        //invert.applyInPlace(result);

        return new PackageCoordinate((Coordinate) pack.getID(), result);
    }

    public static void main(String[] args) {
        try {
            FastBitmap image = ImageLoader.loadImage("loetstellen.jpg");
            SourceFile sourceFilter = new SourceFile(image);
            ROIFilter roiFilter = new ROIFilter(sourceFilter);
            PipeImpl pipe = new PipeImpl(roiFilter);
            ThresholdFilter thresholdFilter = new ThresholdFilter((Readable) pipe);

            ImageViewer imageViewer = new ImageViewer((FastBitmap) ((PackageCoordinate) thresholdFilter.read()).getValue(), "threshold");
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }


    }
}
