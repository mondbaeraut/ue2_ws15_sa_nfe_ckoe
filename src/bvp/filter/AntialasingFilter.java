package bvp.filter;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Median;
import Catalano.Imaging.Filters.MedianCut;
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
public class AntialasingFilter extends AbstractFilter{
    public AntialasingFilter(interfaces.Readable input) throws InvalidParameterException {
        super(input);
    }

    public AntialasingFilter(Writeable output) throws InvalidParameterException {
        super(output);
    }

    public AntialasingFilter(Readable input, Writeable output) throws InvalidParameterException {
        super(input, output);
    }

    @Override
    public Object read() throws StreamCorruptedException {
        return antialasing();
    }

    @Override
    public void run() {

    }
    private FastBitmap antialasing(){
        Median medianCut = new Median(4);
        FastBitmap result = null;
        try {
            result = (FastBitmap)readInput();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }

        medianCut.applyInPlace(result);

        return result;
    }
    @Override
    public void write(Object value) throws StreamCorruptedException {

    }

    public static void main(String[] args) {
        FastBitmap image = ImageLoader.loadImage("loetstellen.jpg");
        SourceFile sourceFilter = new SourceFile(image);
        ROIFilter roiFilter = new ROIFilter(sourceFilter);
        PipeImpl pipe = new PipeImpl(roiFilter);
        ThresholdFilter thresholdFilter = new ThresholdFilter((Readable)pipe);
        PipeImpl pipe2 = new PipeImpl(thresholdFilter);
        AntialasingFilter antialasingFilter = new AntialasingFilter((Readable)pipe2);
        antialasingFilter.antialasing();
        ImageViewer imageViewer = new ImageViewer((FastBitmap) antialasingFilter.antialasing());

    }
}
