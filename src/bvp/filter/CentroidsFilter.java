package bvp.filter;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.FastGraphics;
import Catalano.Imaging.Filters.Threshold;
import Catalano.Imaging.Tools.Blob;
import Catalano.Imaging.Tools.BlobDetection;
import bvp.data.*;
import bvp.data.Package;
import bvp.pipe.BufferedSyncPipe;
import bvp.pipe.PipeBufferImpl;
import bvp.pipe.PipeImpl;
import bvp.util.ImageLoader;
import bvp.util.ImageViewer;
import filter.AbstractFilter;
import interfaces.*;
import interfaces.Readable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mod on 11/10/15.
 */
public class CentroidsFilter extends AbstractFilter {
    private BlobDetection blob;

    public CentroidsFilter(interfaces.Readable input) throws InvalidParameterException {
        super(input);
    }

    public CentroidsFilter(Writeable output) throws InvalidParameterException {
        super(output);
    }

    public CentroidsFilter(Readable input, Writeable output) throws InvalidParameterException {
        super(input, output);
    }

    @Override
    public Object read() throws StreamCorruptedException{
        return process((FastBitmap) ((Package) readInput()).getValue(), (Coordinate) ((Package) readInput()).getID());
    }

    @Override
    public void run() {
        Package input = null;
        try {
            do {
                input = (Package)readInput();
                //System.out.println(input.toString());
                if (input != null) {
                    FastBitmap fastBitmap = (FastBitmap) input.getValue();
                    //fastBitmap.toGrayscale();
                    if (fastBitmap != null) {
                        writeOutput(process(fastBitmap, (Coordinate)input.getID()));
                    }
                }
            }while(input != null);
            sendEndSignal();
        } catch (StreamCorruptedException e) {
            // TODO Automatisch erstellter Catch-Block
            e.printStackTrace();
        }
    }

    private Package process(FastBitmap fastBitmap,Coordinate coordinate) throws StreamCorruptedException{
        ImageViewer viewer = new ImageViewer(fastBitmap,"save");
        if(fastBitmap != null) {
            blob = new BlobDetection();
            List<Blob> blobList = null;
            LinkedList<Coordinate> result = new LinkedList<>();
            fastBitmap.toGrayscale();
            blobList = blob.ProcessImage(fastBitmap);
            for (Blob b : blobList) {
           /* FastGraphics fastGraphics = new FastGraphics(fastBitmap);
            fastGraphics.setColor(255, 0, 0);
            for (int i = 0; i < 3; i++) {
                fastGraphics.DrawCircle(b.getCenter().x, b.getCenter().y, i);
            }*/
                /**
                 * CATALANO BUG X and Y
                 */
                result.add(new Coordinate(b.getCenter().y + coordinate.getX(), b.getCenter().x + coordinate.getY()));
                System.out.println(b.getCenter().y + coordinate.getX()+","+ b.getCenter().x + coordinate.getY());

            }
            //ImageViewer imageViewer = new ImageViewer(image,"centroids");
        }
        return new PackageCoordinate<>(coordinate, fastBitmap);
    }


    @Override
    public void write(Object value) throws StreamCorruptedException {

    }

    public static void main(String[] args) {
        FastBitmap fastBitmap = ImageLoader.loadImage("loetstellen.jpg");
        SourceFile sourceFile = new SourceFile(fastBitmap);

        BufferedSyncPipe pipe = new BufferedSyncPipe(100);
        BufferedSyncPipe pipe2 = new BufferedSyncPipe(100);
        BufferedSyncPipe pipe3 = new BufferedSyncPipe(100);
        BufferedSyncPipe pipe4 = new BufferedSyncPipe(100);

        ROIFilter roiFilter = new ROIFilter(sourceFile, (Writeable) pipe, new Coordinate(0, 50), new Rectangle(448, 5),4);
        ThresholdFilter thresholdFilter = new ThresholdFilter((Readable) pipe,(Writeable)pipe2);
        AntialasingFilter antialasingFilter = new AntialasingFilter((Readable)pipe2,(Writeable)pipe3);
        CentroidsFilter centroidsFilter = new CentroidsFilter((Readable)pipe3,(Writeable)pipe4);
        Consumer c = new Consumer(pipe4);

        new Thread(roiFilter).start();
        new Thread(thresholdFilter).start();
        new Thread(antialasingFilter).start();
        new Thread(centroidsFilter).start();
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
                    //System.out.println(input.toString());
                    if (input != null) {
                        consume(input.getValue());
                    }
                }while(input != null);
            } catch (StreamCorruptedException e) {
                // TODO Automatisch erstellter Catch-Block
                e.printStackTrace();
            }
        }

        void consume(Object x) {
             System.out.println("ANTI :" +x.toString());
        }
    }
}
