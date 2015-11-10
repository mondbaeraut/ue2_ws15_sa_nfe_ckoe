package bvp.filter;


import Catalano.Core.IntRange;
import Catalano.Imaging.Concurrent.Filters.BernsenThreshold;
import Catalano.Imaging.Concurrent.Filters.HysteresisThreshold;
import Catalano.Imaging.Concurrent.Filters.MaximumEntropyThreshold;
import Catalano.Imaging.Concurrent.Filters.NiblackThreshold;
import Catalano.Imaging.FastBitmap;


import Catalano.Imaging.Filters.ColorFiltering;
import Catalano.Imaging.Filters.ReplaceColor;
import Catalano.Imaging.Filters.Threshold;
import bvp.data.SourceFile;
import bvp.pipe.PipeImpl;
import bvp.util.ImageLoader;
import bvp.util.ImageViewer;

import filter.AbstractFilter;
import interfaces.*;
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
    public Object read() throws StreamCorruptedException {
        return getTrashold();
    }

    @Override
    public void run() {

    }

    @Override
    public void write(Object value) throws StreamCorruptedException {

    }

    private FastBitmap getTrashold(){
        Threshold threshold = new Threshold(30,true);
        ReplaceColor colorFiltering = new ReplaceColor(new IntRange(0,36),new IntRange(0,36),new IntRange(0,36));

        FastBitmap result = null;

        try {
            result = (FastBitmap)readInput();
            result.toRGB();
            colorFiltering.ApplyInPlace(result,255,255,255);
            //invert.applyInPlace(result);

        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static void main(String[] args) {
        FastBitmap image = ImageLoader.loadImage("loetstellen.jpg");
        SourceFile sourceFilter = new SourceFile(image);
        ROIFilter roiFilter = new ROIFilter(sourceFilter);
        PipeImpl pipe = new PipeImpl(roiFilter);
        ThresholdFilter thresholdFilter = new ThresholdFilter((Readable)pipe);
        thresholdFilter.getTrashold();
        try {
            ImageViewer imageViewer = new ImageViewer((FastBitmap) thresholdFilter.read());
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }


    }
}
