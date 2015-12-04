package java;

import bvp.pipe.BufferedSyncPipe;
import bvp.util.ImageLoader;
import data.Coordinate;
import filter.AbstractFilter;
import interfaces.ImageEvent;
import interfaces.Readable;
import interfaces.Writeable;

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
    public  HashMap<Coordinate,Boolean> read() throws StreamCorruptedException {
        return process((ImageEvent) readInput());
    }

    @Override
    public void run() {
        /*Package input = null;
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
        }*/
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

    private HashMap<Coordinate,Boolean> process(ImageEvent event) {

        List<Coordinate> incomingCenter = event.getCenterCoordinates();
        HashMap<Coordinate, Boolean> map = new HashMap<>();
        for (Coordinate tovalcoordinate : incomingCenter) {
            boolean valid = validateCoordinate(tovalcoordinate, coordinates, tollerance);
            if (map.get(tovalcoordinate) == null) {
                map.put(tovalcoordinate, valid);
            }
        }
        return map;
    }



}
