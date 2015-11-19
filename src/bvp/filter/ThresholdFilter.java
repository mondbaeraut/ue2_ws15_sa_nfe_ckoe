package bvp.filter;


import Catalano.Core.IntRange;
import Catalano.Imaging.Concurrent.Filters.ColorFiltering;
import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.ReplaceColor;
import Catalano.Imaging.Filters.Threshold;
import bvp.data.*;
import bvp.data.Package;
import bvp.pipe.BufferedSyncPipe;
import bvp.pipe.PipeBufferImpl;
import bvp.util.ImageLoader;
import bvp.util.ImageViewer;
import filter.AbstractFilter;
import interfaces.Readable;
import interfaces.Writeable;

import java.awt.*;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

/**
 * Created by mod on 11/9/15.
 */
public class ThresholdFilter<T> extends AbstractFilter {
    public ThresholdFilter(Readable input) throws InvalidParameterException {
        super(input);
    }

    public ThresholdFilter(Readable input, Writeable output) throws InvalidParameterException {
        super(input, output);
    }

    @Override
    public Object read() throws StreamCorruptedException{
        return process((FastBitmap) ((Package) readInput()).getValue(),(Coordinate)((Package) readInput()).getID());
    }

    @Override
    public void run() {
        Package input = null;
        try {
            do {
                input = (Package)readInput();
                System.out.println(input);
                if (input != null) {
                    FastBitmap fastBitmap = (FastBitmap) input.getValue();
                    if (fastBitmap != null) {
                        writeOutput(process(fastBitmap, (Coordinate)input.getID()));

                    }
                }
            }while(input != null);
            sendEndSignal();
            System.out.println();
        } catch (StreamCorruptedException e) {
            // TODO Automatisch erstellter Catch-Block
            e.printStackTrace();
        }
    }

    @Override
    public void write(Object value) throws StreamCorruptedException {

    }

    private PackageCoordinate process(FastBitmap fastBitmap,Coordinate coordinate) throws StreamCorruptedException{
        FastBitmap result = fastBitmap;
        ReplaceColor colorFiltering = new ReplaceColor(new IntRange(0,37),new IntRange(0,37),new IntRange(0,37));
        result.toRGB();
        colorFiltering.ApplyInPlace(fastBitmap, 255, 255, 255);
        //invert.applyInPlace(result);
       // ImageViewer imageViewer = new ImageViewer(result,"resultofThresh");

        return new PackageCoordinate(coordinate, result);
    }

    public static void main(String[] args) {
        FastBitmap fastBitmap = ImageLoader.loadImage("loetstellen.jpg");
        SourceFile sourceFile = new SourceFile(fastBitmap);

        BufferedSyncPipe pipe = new BufferedSyncPipe(100);
        BufferedSyncPipe pipe2 = new BufferedSyncPipe(100);

        ROIFilter roiFilter = new ROIFilter(sourceFile, (Writeable) pipe, new Coordinate(0, 50), new Rectangle(448, 50),4);
        ThresholdFilter thresholdFilter = new ThresholdFilter((Readable) pipe,(Writeable)pipe2);

        Consumer c = new Consumer(pipe2);

        new Thread(roiFilter).start();
        new Thread(thresholdFilter).start();
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
               //    System.out.println(input.toString());
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
            //System.out.println("threshold :" +x.toString());
        }
    }
}
