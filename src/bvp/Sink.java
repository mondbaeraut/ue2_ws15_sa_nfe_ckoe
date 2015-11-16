package bvp;

import Catalano.Imaging.FastBitmap;
import bvp.data.*;
import bvp.data.Package;
import bvp.filter.*;
import bvp.pipe.PipeImpl;
import bvp.util.ImageLoader;
import interfaces.*;
import interfaces.Readable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by mod on 11/12/15.
 */
public class Sink {
    public static void main(String[] args) {
        try {
        FastBitmap image = ImageLoader.loadImage("loetstellen.jpg");
        SourceFile sourceFilter = new SourceFile(image);
        ROIFilter roiFilter = new ROIFilter(sourceFilter);
        PipeImpl pipe = new PipeImpl(roiFilter);
        ThresholdFilter thresholdFilter = new ThresholdFilter((interfaces.Readable) pipe);
        PipeImpl pipe2 = new PipeImpl(thresholdFilter);
        AntialasingFilter antialasingFilter = new AntialasingFilter((Readable) pipe2);
        PipeImpl pipe3 = new PipeImpl(antialasingFilter);
        CentroidsFilter centroidsFilter = new CentroidsFilter((Readable) pipe3);
        PipeImpl pipe4 = new PipeImpl(centroidsFilter);
        Package list = null;
        ValidationFilter validationFilter = null;
        list = (Package) centroidsFilter.read();
        validationFilter = new ValidationFilter((Readable)pipe4,list,5);
        Sink sink = new Sink();
        sink.writeToFile(((Package)validationFilter.read()),"Documentation/result.txt");
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(Package pack, String filename) {
        File file = new File(filename);
        Coordinate roiPoint = (Coordinate) pack.getID();
        HashMap<Coordinate,Boolean> map = (HashMap<Coordinate, Boolean>) pack.getValue();
        // creates the file
        try {
            file.createNewFile();
            // creates a FileWriter Object
            FileWriter writer = new FileWriter(file);
            // Writes the content to the file

            writer.write(String.format("|%15s|%20s|%20s|%20s| \r\n","nodenumber", "x", "y","status"));
            writer.write(String.format("|%15s|%20s|%20s|%20s| \r\n","---------------", "--------------------", "--------------------","--------------------"));
            int i = 1;
            int passed = 0;
            int failed = 0;

            for (Map.Entry<Coordinate, Boolean> entry : map.entrySet())
            {
                boolean test = entry.getValue();
                if(test){
                    passed++;
                }else{
                    failed++;
                }
                int x =  entry.getKey().getX() + roiPoint.getX();
                int y =  entry.getKey().getY()+roiPoint.getY();
                writer.write(String.format("|%15s|%20s|%20s|%20s| \r\n", i,x,y, test));
                i++;
            }
            writer.write("Total amount of ball boundings: " + map.size()+ "\r\n");
            writer.write("Passed:" +passed +"\r\n");
            writer.write("Failed:" + failed +"\r\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
