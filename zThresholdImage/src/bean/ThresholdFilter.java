package bean;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Threshold;
import data.ImageEventReadable;
import filter.AbstractFilter;
import interfaces.ImageEvent;
import interfaces.Readable;
import interfaces.Writeable;
import util.ImageViewer;

import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

/**
 * Created by mod on 11/9/15.
 */
public class ThresholdFilter<T> extends AbstractFilter {
    private int thresholdpercentage = 0;

    public ThresholdFilter(Readable input, int thresholdpercentage) throws InvalidParameterException {
        super(input);
        this.thresholdpercentage = thresholdpercentage;
    }

    public ThresholdFilter(Writeable output) throws InvalidParameterException {
        super(output);
    }

    public ThresholdFilter(Readable input, Writeable output) throws InvalidParameterException {
        super(input, output);
    }

    @Override
    public ImageEvent read() throws StreamCorruptedException {
        return process((ImageEvent) readInput());
    }

    @Override
    public void run() {
    /*    Package input = null;
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
        }*/
    }

    /*
        public void write(ImageEvent value) throws StreamCorruptedException {
            value = process((FastBitmap) ((Package) value).getValue(), ((Coordinate) (Package) value)).getID();
            this.writeOutput(value);

        }
    */
    private ImageEvent process(ImageEvent imageEvent) throws StreamCorruptedException {
        Threshold threshold = new Threshold(thresholdpercentage);
        FastBitmap fastBitmap = imageEvent.getFastBitmap();
        ImageEvent result = null;
        if (fastBitmap != null) {
            fastBitmap.toGrayscale();
            threshold.applyInPlace(fastBitmap);
            result = new ImageEvent(this, fastBitmap);
        }
        return result;
    }

    @Override
    public void write(Object value) throws StreamCorruptedException {

    }

    public static void main(String[] args) {
        FastBitmap fastBitmap = new FastBitmap("/home/mod/IdeaProjects/ue3_ws15_sa_nfe_ckoe/zErodeImage/result.png");
        ImageEventReadable readable = new ImageEventReadable(fastBitmap);
        ThresholdFilter erodeFilter = new ThresholdFilter(readable, 50);
        ImageEvent imageEvent = new ImageEvent(erodeFilter, fastBitmap);
        try {
            imageEvent = erodeFilter.process(imageEvent);
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
        ImageViewer imageViewer = new ImageViewer(imageEvent.getFastBitmap(), "Somename");
    }

}
/*
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
    */

