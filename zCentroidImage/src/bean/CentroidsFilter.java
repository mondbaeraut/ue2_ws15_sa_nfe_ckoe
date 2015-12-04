package bean;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.FastGraphics;
import Catalano.Imaging.Tools.Blob;
import Catalano.Imaging.Tools.BlobDetection;
import bvp.data.*;
import bvp.data.Package;
import bvp.filter.AntialasingFilter;
import bvp.filter.ROIFilter;
import bvp.filter.SourceFile;
import bvp.filter.ThresholdFilter;
import bvp.pipe.BufferedSyncPipe;
import bvp.util.ImageLoader;
import filter.AbstractFilter;
import filter.ForwardingFilter;
import interfaces.*;
import interfaces.Readable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mod on 11/10/15.
 */
public class CentroidsFilter extends ForwardingFilter {
    private BlobDetection blob;

    public CentroidsFilter(interfaces.Readable input) throws InvalidParameterException {
        super(input);
    }

    public CentroidsFilter(Writeable output) throws InvalidParameterException {
        super(output);
    }

    public CentroidsFilter(interfaces.Readable input, Writeable output) throws InvalidParameterException {
        super(input, output);
    }

    @Override
    public Object read() throws StreamCorruptedException {
        return process((ImageEvent) readInput());
    }

    @Override
    public void run() {
       /* Package input = null;
        try {
            do {
                input = (Package) readInput();
                if (input != null) {
                    FastBitmap fastBitmap = (FastBitmap) input.getValue();
                    //fastBitmap.toGrayscale();
                    if (fastBitmap != null) {
                        writeOutput(process(fastBitmap, (Coordinate) input.getID()));
                    }
                }
            } while (input != null);
            sendEndSignal();
        } catch (StreamCorruptedException e) {
            // TODO Automatisch erstellter Catch-Block
            e.printStackTrace();
        }*/
    }

    private ImageEvent process(ImageEvent imageEvent) throws StreamCorruptedException {
        LinkedList<Coordinate> result = new LinkedList<>();
        FastBitmap fastBitmap1 = imageEvent.getFastBitmap();
        BufferedImage image = fastBitmap1.toBufferedImage();
        FastBitmap newFastBitmap = new FastBitmap(image);
        if (imageEvent != null) {
            blob = new BlobDetection();
            List<Blob> blobList = null;
            try {
                //fastBitmap.toGrayscale();
                blobList = blob.ProcessImage(newFastBitmap);
            } catch (Exception e) {
                System.out.println("Trigger");
            }
            FastGraphics fastGraphics = new FastGraphics(newFastBitmap);
            for (Blob b : blobList) {

                fastGraphics.setColor(255, 0, 0);
                for (int i = 0; i < 3; i++) {
                    fastGraphics.DrawCircle(b.getCenter().x, b.getCenter().y, i);
                }

            }


        }
        return new ImageEvent(this,newFastBitmap);
    }




    @Override
    public void write(Object value) throws StreamCorruptedException {
       super.write(value);

    }

    public static void main(String[] args) {

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
                } while (input != null);
            } catch (StreamCorruptedException e) {
                // TODO Automatisch erstellter Catch-Block
                e.printStackTrace();
            }
        }

        void consume(Object x) {
            System.out.println("ANTI :" + x.toString());
        }
    }
}

