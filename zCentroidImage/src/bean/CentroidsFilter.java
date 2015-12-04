package bean;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.FastGraphics;
import Catalano.Imaging.Tools.Blob;
import Catalano.Imaging.Tools.BlobDetection;
import bvp.pipe.BufferedSyncPipe;
import data.Coordinate;
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
                result.add(new Coordinate(b.getCenter().y + imageEvent.getCoordinate().getX(), b.getCenter().x + imageEvent.getCoordinate().getY()));

            }


        }
        ImageEvent imageEvent1 = new ImageEvent(this,newFastBitmap);
        imageEvent1.setCenterCoordinates(result);
        return imageEvent1;
    }




    @Override
    public void write(Object value) throws StreamCorruptedException {
       super.write(value);

    }

    public static void main(String[] args) {



    }
}

