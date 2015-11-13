package bvp.filter;

import Catalano.Imaging.Concurrent.Filters.Opening;
import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Median;

import Catalano.Imaging.Filters.Threshold;
import Catalano.Imaging.Tools.Blob;
import Catalano.Imaging.Tools.BlobDetection;
import Catalano.Imaging.Tools.BlobDetection.Algorithm;
import bvp.data.*;
import bvp.data.Package;
import bvp.pipe.Pipe;
import bvp.pipe.PipeImpl;
import bvp.util.ImageLoader;
import bvp.util.ImageViewer;
import filter.AbstractFilter;
import interfaces.*;
import interfaces.Readable;

import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mod on 11/9/15.
 */
public class AntialasingFilter extends AbstractFilter {
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

    private PackageCoordinate antialasing() throws StreamCorruptedException {
        Median medianCut = new Median(10);
        Package temp = null;
        FastBitmap result = null;
        temp = (Package) readInput();
        result = (FastBitmap) temp.getValue();
        result.toGrayscale();
        medianCut.applyInPlace(result);
        // result.toGrayscale();
        Opening opening = new Opening(4);
        opening.applyInPlace(result);
        return new PackageCoordinate((Coordinate) temp.getID(), result);
    }

    @Override
    public void write(Object value) throws StreamCorruptedException {

    }

    public static void main(String[] args) {
        try {
            FastBitmap image = ImageLoader.loadImage("loetstellen.jpg");
            SourceFile sourceFilter = new SourceFile(image);
            ROIFilter roiFilter = new ROIFilter(sourceFilter);
            PipeImpl pipe = new PipeImpl(roiFilter);
            ThresholdFilter thresholdFilter = new ThresholdFilter((Readable) pipe);
            PipeImpl pipe2 = new PipeImpl(thresholdFilter);
            AntialasingFilter antialasingFilter = new AntialasingFilter((Readable) pipe2);
            FastBitmap temp = null;
            temp = (FastBitmap) ((PackageCoordinate) antialasingFilter.read()).getValue();
            PipeImpl pipe3 = new PipeImpl(antialasingFilter);
            ImageViewer imageViewer = new ImageViewer(temp, "antialasing");
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }
}
