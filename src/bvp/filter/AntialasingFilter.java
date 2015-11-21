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
import bvp.pipe.BufferedSyncPipe;
import bvp.pipe.Pipe;
import bvp.pipe.PipeBufferImpl;
import bvp.pipe.PipeImpl;
import bvp.util.ImageLoader;
import bvp.util.ImageViewer;
import filter.AbstractFilter;
import interfaces.*;
import interfaces.Readable;

import java.awt.*;
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
        return process((FastBitmap) ((Package) readInput()).getValue(), (Coordinate) ((Package) readInput()).getID());
    }

    @Override
    public void run() {
        Package input = null;
        try {
            do {
                input = (Package)readInput();
                if (input != null) {
                    FastBitmap fastBitmap = (FastBitmap) input.getValue();
                    if (fastBitmap != null) {
                        Package pack = process(fastBitmap, (Coordinate)input.getID()) ;
                        writeOutput(pack);
                    }
                }
            }while(input != null);
            sendEndSignal();
        } catch (StreamCorruptedException e) {
            // TODO Automatisch erstellter Catch-Block
            e.printStackTrace();
        }
    }

    private PackageCoordinate process(FastBitmap fastBitmap,Coordinate coordinate) throws StreamCorruptedException {
        Median medianCut = new Median(10);
        Package temp = null;
        temp = (Package) readInput();
        fastBitmap.toGrayscale();
        medianCut.applyInPlace(fastBitmap);
        Opening opening = new Opening(  4);
        opening.applyInPlace(fastBitmap);
        return new PackageCoordinate(coordinate, fastBitmap);
    }

    @Override
    public void write(Object value) throws StreamCorruptedException {
        value = process((FastBitmap)((Package) value).getValue(), ((Coordinate) (Package) value)).getID();
        this.writeOutput(value);
    }

    public static void main(String[] args) {
        FastBitmap fastBitmap = ImageLoader.loadImage("loetstellen.jpg");
        SourceFile sourceFile = new SourceFile(fastBitmap);

        BufferedSyncPipe pipe = new BufferedSyncPipe(100);
        BufferedSyncPipe pipe2 = new BufferedSyncPipe(100);
        BufferedSyncPipe pipe3 = new BufferedSyncPipe(100);

        ROIFilter roiFilter = new ROIFilter(sourceFile, (Writeable) pipe, new Coordinate(0, 50), new Rectangle(448, 50),4);
        ThresholdFilter thresholdFilter = new ThresholdFilter((Readable) pipe,(Writeable)pipe2);
        AntialasingFilter antialasingFilter = new AntialasingFilter((Readable)pipe2,(Writeable)pipe3);
        Consumer c = new Consumer(pipe3);

        new Thread(roiFilter).start();
        new Thread(thresholdFilter).start();
        new Thread(antialasingFilter).start();
        new Thread(c).start();

        //ImageViewer imageViewer = new ImageViewer((FastBitmap) ((PackageCoordinate) thresholdFilter.read()).getValue(), "threshold");
    }
    static class Consumer implements Runnable {
        private final BufferedSyncPipe queue;
        Consumer(BufferedSyncPipe q) {
            queue = q;
        }
        public void run() {
            Package input = null;
            try {
                do {
                    input = (Package) queue.read();
                    if (input != null) {
                        FastBitmap fastBitmap = (FastBitmap) input.getValue();
                        if (fastBitmap != null) {
                           consume(input);
                        }
                    }
                }while(input != null);
            } catch (StreamCorruptedException e) {
                // TODO Automatisch erstellter Catch-Block
                e.printStackTrace();
            }
        }

        void consume(Object x) {
            // ImageViewer viewer = new ImageViewer((FastBitmap) ((Package)x).getValue(),"save");
        }
    }
}
