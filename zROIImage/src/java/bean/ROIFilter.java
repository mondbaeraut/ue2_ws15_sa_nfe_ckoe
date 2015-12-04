package bean;

import Catalano.Imaging.FastBitmap;
import data.Coordinate;
import filter.ForwardingFilter;
import interfaces.ImageEvent;
import interfaces.Readable;
import interfaces.Writeable;
import pipes.BufferedSyncPipe;

import java.awt.*;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

/**
 * Created by mod on 11/9/15.
 */
public class ROIFilter<T> extends ForwardingFilter {

    private data.Coordinate coordinate = new Coordinate(0, 0);
    private Rectangle rectangle = new Rectangle(0, 0);

    public ROIFilter(interfaces.Readable input, Coordinate coordinate, Rectangle rectangle) throws InvalidParameterException {
        super(input);
        this.coordinate = coordinate;
        this.rectangle = rectangle;
    }

    public ROIFilter(interfaces.Readable input) throws InvalidParameterException {
        super(input);
    }

    public ROIFilter(Writeable output) throws InvalidParameterException {
        super(output);
    }

    public ROIFilter(Readable input, Writeable output, Coordinate coordinate, Rectangle rectangle) throws InvalidParameterException {
        super(input, output);
        this.coordinate = coordinate;
        this.rectangle = rectangle;
    }

    public ROIFilter(Readable input, Writeable output, Coordinate coordinate, Rectangle rectangle, int maxrepetitions) throws InvalidParameterException {
        super(input, output);
        this.coordinate = coordinate;
        this.rectangle = rectangle;
    }

    public ROIFilter(Writeable output, Coordinate coordinate, Rectangle rectangle) {
        super(output);
        this.coordinate = coordinate;
        this.rectangle = rectangle;
    }

    @Override
    public ImageEvent read() throws StreamCorruptedException {
        return process((ImageEvent) readInput());
    }

    @Override
    public void run() {
      /*  try {
            boolean endfound = false;
            while (!endfound) {
                if(currrepetetions < maxrepetitions ) {
                    FastBitmap fastBitmap = (FastBitmap) readInput();
                    if (fastBitmap != null) {
                        bvp.data.Package pack = process(fastBitmap);
                        writeOutput(pack);
                        currrepetetions++;
                    }
                }else{
                    currrepetetions = 0;
                    System.out.println("ROI end found with "+ maxrepetitions);
                    endfound = true;
                    sendEndSignal();
                }
            }
        }catch (StreamCorruptedException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void write(Object value) throws StreamCorruptedException {
        //   value = process((FastBitmap) value);
        this.writeOutput(value);
    }

    private ImageEvent process(ImageEvent imageEvent) throws StreamCorruptedException {
        FastBitmap temp = imageEvent.getFastBitmap();
        temp = new FastBitmap((temp.toBufferedImage()).getSubimage(coordinate.getX(), coordinate.getY(), rectangle.width, rectangle.height));
        imageEvent = new ImageEvent(this, temp);
        //imageEvent.setCoordinate(coordinate);
        return imageEvent;
    }

    public static void main(String[] args) {
   /*     FastBitmap fastBitmap = ImageLoader.loadImage("loetstellen.jpg");
        SourceFile sourceFile = new SourceFile(fastBitmap);

        BufferedSyncPipe pipeBuffer = new BufferedSyncPipe(4);
        ROIFilter roiFilter = new ROIFilter(sourceFile, (Writeable) pipeBuffer, new Coordinate(0, 50), new Rectangle(448, 50),10);
        new Thread(roiFilter).start();
        Consumer c = new Consumer(pipeBuffer);
        new Thread(c).start();*/


    }

    static class Consumer implements Runnable {
        private final BufferedSyncPipe queue;

        Consumer(BufferedSyncPipe q) {
            queue = q;
        }

        public void run() {
            boolean endfound = true;
            while (endfound) {
                Package pack = null;
                try {
                    pack = (Package) queue.read();
                } catch (StreamCorruptedException e) {
                    e.printStackTrace();
                }
                if (pack == null) {
                    endfound = false;
                    consume("end");
                } else {
                    consume(pack.toString());
                }

            }
        }

        void consume(Object x) {
            System.out.println(x.toString());
        }
    }
}
