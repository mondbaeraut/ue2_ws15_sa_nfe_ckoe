package bvp.filter;

import Catalano.Imaging.FastBitmap;
import bvp.data.*;
import bvp.data.Package;
import bvp.pipe.PipeImpl;
import bvp.util.ImageLoader;
import filter.AbstractFilter;
import interfaces.*;
import interfaces.Readable;

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

    public ValidationFilter(interfaces.Readable input, Package coordinates, int tollerance){
        super(input);
        this.coordinates = (List<Coordinate>) coordinates.getValue();
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
        HashMap<Coordinate,Boolean> result = validate((List<Coordinate>)((Package)readInput()).getValue());
        return new PackageCoordinate((Coordinate)((Package)readInput()).getID(),result);
    }

    @Override
    public void run() {

    }

    @Override
    public void write(Object value) throws StreamCorruptedException {

    }

    private HashMap validate(List<Coordinate> incomingCenter){
        HashMap<Coordinate,Boolean> map = new HashMap<>();
        boolean validationOk = false;
        double tollerancepercent = tollerance/100;
        for(Coordinate coordinate:incomingCenter){
            for(Coordinate coordinate2:coordinates){
                validationOk = false;
                if(coordinate2.getX() <= coordinate.getX()+(coordinate.getX()*tollerancepercent)&& coordinate2.getX() >= coordinate.getX()-(coordinate.getX()*tollerancepercent)){
                    if(coordinate2.getY() <= coordinate.getY()+(coordinate.getY()*tollerancepercent)&& coordinate2.getY() >= coordinate.getY()-(coordinate.getY()*tollerancepercent)){
                        if(map.get(coordinate) == null){
                            map.put(coordinate,true);
                        }
                    }
                }

            }
        }
        return map;
    }
    public static void main(String[] args) {
        FastBitmap image = ImageLoader.loadImage("loetstellen.jpg");
        SourceFile sourceFilter = new SourceFile(image);
        ROIFilter roiFilter = new ROIFilter(sourceFilter);
        PipeImpl pipe = new PipeImpl(roiFilter);
        ThresholdFilter thresholdFilter = new ThresholdFilter((Readable)pipe);
        PipeImpl pipe2 = new PipeImpl(thresholdFilter);
        AntialasingFilter antialasingFilter = new AntialasingFilter((Readable)pipe2);
        PipeImpl pipe3 = new PipeImpl(antialasingFilter);
        CentroidsFilter centroidsFilter = new CentroidsFilter((Readable) pipe3);
        PipeImpl pipe4 = new PipeImpl(centroidsFilter);
         Package list = null;
        try {
            list = (Package) centroidsFilter.read();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
        ValidationFilter validationFilter = new ValidationFilter((Readable)pipe4,list,5);
        try {
            System.out.println(validationFilter.read());
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }
}
