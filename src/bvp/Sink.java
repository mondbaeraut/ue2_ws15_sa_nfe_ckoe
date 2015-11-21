package bvp;

import Catalano.Imaging.FastBitmap;
import bvp.data.*;
import bvp.data.Package;
import bvp.filter.*;
import bvp.pipe.BufferedSyncPipe;
import bvp.pipe.PipeBufferImpl;
import bvp.pipe.PipeImpl;
import bvp.util.ImageLoader;
import interfaces.*;
import interfaces.Readable;

import java.awt.*;
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
public class Sink implements Runnable {
    private Readable readable;
    private Writeable writeable;
    private int counter=0;
    private String filesavename;
    public Sink(Readable readable,String filesavename){
        this.readable = readable;
        this.filesavename = filesavename;
    }
    public Sink(Writeable writeable,String filesavename){
        this.writeable = writeable;
        this.filesavename = filesavename;
    }
    public static void main(String[] args) {
        FastBitmap fastBitmap = ImageLoader.loadImage("loetstellen.jpg");
        SourceFile sourceFile = new SourceFile(fastBitmap);

        BufferedSyncPipe pipe = new BufferedSyncPipe(4);
        BufferedSyncPipe pipe2 = new BufferedSyncPipe(4);
        BufferedSyncPipe pipe3 = new BufferedSyncPipe(4);
        BufferedSyncPipe pipe4 = new BufferedSyncPipe(4);
        BufferedSyncPipe pipe5 = new BufferedSyncPipe(4);

        List<Coordinate> cordinates = new LinkedList<>();
        cordinates.add(new Coordinate(6, 74));
        cordinates.add(new Coordinate(396, 78));
        cordinates.add(new Coordinate(264, 78));
        cordinates.add(new Coordinate(201, 78));
        cordinates.add(new Coordinate(330, 78));
        cordinates.add(new Coordinate(134, 78));
        cordinates.add(new Coordinate(72, 78));


        ROIFilter roiFilter = new ROIFilter(sourceFile, (Writeable) pipe, new Coordinate(0, 50), new Rectangle(448, 50));
        ThresholdFilter thresholdFilter = new ThresholdFilter((Readable) pipe, (Writeable) pipe2);
        AntialasingFilter antialasingFilter = new AntialasingFilter((Readable) pipe2, (Writeable) pipe3);
        CentroidsFilter centroidsFilter = new CentroidsFilter((Readable) pipe3, (Writeable) pipe4);
        ValidationFilter validationFilter = new ValidationFilter((Readable) pipe4, (Writeable) pipe5, cordinates, 20);
        Sink sink = new Sink((Readable) pipe5,"Documentation/Result/result");

        new Thread(roiFilter).start();
        new Thread(thresholdFilter).start();
        new Thread(antialasingFilter).start();
        new Thread(centroidsFilter).start();
        new Thread(validationFilter).start();
        new Thread(sink).start();

    }

    @Override
    public void run() {
        boolean enfound = false;
        HashMap<Coordinate,Boolean> temp;
        while(!enfound){
            try {
                temp = (HashMap<Coordinate,Boolean>) readable.read();
                if(temp != null) {
                    writeToFile(temp, filesavename);
                }else{
                    enfound = true;
                }
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public void writeToFile(HashMap<Coordinate,Boolean> map, String filename) {
        File file = new File(filename+"_"+counter+".txt");
        counter++;
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
                int x =  entry.getKey().getX();
                int y =  entry.getKey().getY();
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
