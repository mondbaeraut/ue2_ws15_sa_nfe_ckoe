package bvp.filter;

import Catalano.Imaging.FastBitmap;
import bvp.data.*;
import bvp.data.Package;
import bvp.pipe.BufferedSyncPipe;
import bvp.util.ImageLoader;
import filter.AbstractFilter;
import interfaces.*;
import interfaces.Readable;

import java.awt.*;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mod on 11/10/15.
 */
public class ValidationFilter extends AbstractFilter {
    private List<Coordinate> coordinates;
    private int tollerance;

    public ValidationFilter(interfaces.Readable input, List<Coordinate> coordinates, int tollerance) {
        super(input);
        this.coordinates = coordinates;
        this.tollerance = tollerance;
    }

    public ValidationFilter(Readable input, Writeable output, List<Coordinate> coordinates, int tollerance) throws InvalidParameterException {
        super(input, output);
        this.coordinates = coordinates;
        this.tollerance = tollerance;
    }

    public ValidationFilter(interfaces.Readable input) throws InvalidParameterException {
        super(input);
    }

    public ValidationFilter(Writeable output) throws InvalidParameterException {
        super(output);
    }

    public ValidationFilter(Readable input, Writeable output) throws InvalidParameterException {
        super(input, output);
    }

    @Override
    public Object read() throws StreamCorruptedException {
        return process((List<Coordinate>) ((Package) readInput()).getValue());
    }

    @Override
    public void run() {
        Package input = null;
        int index = 0;
        try {
            do {
                input = (Package)readInput();
                if (input != null) {
                    LinkedList coordinateLinkedList = (LinkedList<Coordinate>) input.getValue();
                    if (coordinateLinkedList != null) {
                        Package pack = process(coordinateLinkedList) ;
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

    @Override
    public void write(Object value) throws StreamCorruptedException {
       // HashMap<Coordinate, Boolean> result = process((List<Coordinate>) ((Package) value).getValue());
       // writeOutput(new PackageCoordinate((Coordinate) ((Package) value).getID(), result));

    }

    private boolean validateCoordinate(Coordinate tovalcoordinate, List<Coordinate> coordinate, int tollerance) {
        double dotollerance = (double)tollerance / 100;
        for (Coordinate tocheckcoordinate : coordinate) {
            double tolleranceX = tocheckcoordinate.getX() * dotollerance;
            double tolleranceY = tocheckcoordinate.getY() * dotollerance;
            if (tovalcoordinate.getX() > tocheckcoordinate.getX() - tolleranceX && tovalcoordinate.getX() < tocheckcoordinate.getX() + tolleranceX) {
                if (tovalcoordinate.getY() > tocheckcoordinate.getY() - tolleranceY && tovalcoordinate.getY() < tocheckcoordinate.getY() + tolleranceY) {
                    return true;
                }
            }
        }
        return false;
    }

    private PackageCoordinate process(List<Coordinate> incomingCenter) {
        HashMap<Coordinate, Boolean> map = new HashMap<>();
        for (Coordinate tovalcoordinate : incomingCenter) {
            boolean valid = validateCoordinate(tovalcoordinate, coordinates, tollerance);
            if (map.get(tovalcoordinate) == null) {
                map.put(tovalcoordinate, valid);
            }
        }
        return new PackageCoordinate(new Coordinate(0,0),map);
    }

    public static void main(String[] args) {
        FastBitmap fastBitmap = ImageLoader.loadImage("loetstellen.jpg");
        SourceFile sourceFile = new SourceFile(fastBitmap);

        BufferedSyncPipe pipe = new BufferedSyncPipe(100);
        BufferedSyncPipe pipe2 = new BufferedSyncPipe(100);
        BufferedSyncPipe pipe3 = new BufferedSyncPipe(100);
        BufferedSyncPipe pipe4 = new BufferedSyncPipe(100);
        BufferedSyncPipe pipe5 = new BufferedSyncPipe(100);

        List<Coordinate> cordinates = new LinkedList<>();
        cordinates.add(new Coordinate(6, 74));
        cordinates.add(new Coordinate(396, 78));
        cordinates.add(new Coordinate(264, 78));
        cordinates.add(new Coordinate(201, 78));
        cordinates.add(new Coordinate(330, 78));
        cordinates.add(new Coordinate(134, 78));
        cordinates.add(new Coordinate(72, 78));


        ROIFilter roiFilter = new ROIFilter(sourceFile, (Writeable) pipe, new Coordinate(0, 50), new Rectangle(448, 50),5);
        ThresholdFilter thresholdFilter = new ThresholdFilter((Readable) pipe, (Writeable) pipe2);
        AntialasingFilter antialasingFilter = new AntialasingFilter((Readable) pipe2, (Writeable) pipe3);
        CentroidsFilter centroidsFilter = new CentroidsFilter((Readable) pipe3, (Writeable) pipe4);
        ValidationFilter validationFilter = new ValidationFilter((Readable) pipe4, (Writeable) pipe5, cordinates, 20);
        Consumer c = new Consumer(pipe5);

        new Thread(roiFilter).start();
        new Thread(thresholdFilter).start();
        new Thread(antialasingFilter).start();
        new Thread(centroidsFilter).start();
        new Thread(validationFilter).start();
        new Thread(c).start();
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
                      consume(input);
                    }
                }while(input != null);
            } catch (StreamCorruptedException e) {
                // TODO Automatisch erstellter Catch-Block
                e.printStackTrace();
            }
        }

        void consume(Object x) {
            System.out.println(x.toString());
        }
    }
}
