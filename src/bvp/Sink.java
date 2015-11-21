package bvp;

import Catalano.Imaging.FastBitmap;
import bvp.data.*;
import bvp.data.Package;
import bvp.filter.*;
import bvp.pipe.BufferedSyncPipe;
import bvp.pipe.PipeBufferImpl;
import bvp.pipe.PipeImpl;
import bvp.util.ImageLoader;
import filter.AbstractFilter;
import interfaces.*;
import interfaces.Readable;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by mod on 11/12/15.
 */
public class Sink extends AbstractFilter{
    private int counter=0;
    private String filesavename;

    public Sink(Readable input,String filesavename) throws InvalidParameterException {
        super(input);
        this.filesavename = filesavename;
    }
    @Override
    public void run() {
        Package input = null;
        try {
            do {
                input = (Package)readInput();
                System.out.println(input);
                if (input != null) {
                    HashMap<Coordinate,Boolean> map = (HashMap<Coordinate, Boolean>) ((Package) readInput()).getValue();
                    if (map != null) {
                        process(map, filesavename);
                    }
                }
            }while(input != null);
            sendEndSignal();
            System.out.println();
        } catch (StreamCorruptedException e) {
            // TODO Automatisch erstellter Catch-Block
            e.printStackTrace();
        }

    }
    private void process(HashMap<Coordinate,Boolean> coordinateBooleanHashMap, String filename){
        writeToFile(coordinateBooleanHashMap,filename);

    }
    private void writeToFile(HashMap<Coordinate,Boolean> map, String filename) {
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

    @Override
    public Object read() throws StreamCorruptedException {
        process((HashMap<Coordinate, Boolean>) ((Package) readInput()).getValue(),filesavename);
        return true;
    }

    @Override
    public void write(Object value) throws StreamCorruptedException {

    }
}
