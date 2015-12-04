package java;

import data.Coordinate;
import data.ImageEventReadable;
import interfaces.ImageEvent;
import interfaces.ImageEventHandlerImpl;
import interfaces.ImageListener;
import util.ImageViewer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Christopher on 04.12.2015.
 */
public class ValidationBean extends ImageEventHandlerImpl implements ImageListener {

    private ValidationFilter validationFilter;
    private String path = "";
    private  int tolerance = 1;
    private List<Coordinate> coordinates;

    public ValidationBean() {

    }

    private void writeToFile(HashMap<Coordinate,Boolean> map, String filename) {
        File file = new File(filename+"_"+".txt");
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

    public ValidationFilter getValidationFilter() {
        return validationFilter;
    }

    public void setValidationFilter(ValidationFilter validationFilter) {
        this.validationFilter = validationFilter;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getTolerance() {
        return tolerance;
    }

    public void setTolerance(int tolerance) {
        this.tolerance = tolerance;
    }

    public void writeToFile(String path){

    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinate) {
        String[] values= coordinate.replaceAll("\\(","").replaceAll("\\)","").split(",");
        for(int i = 0; i < values.length;i++ ){
            coordinates.add(new Coordinate(Integer.parseInt(values[i]),Integer.parseInt(values[i+1])));
            i++;
        }
    }

    @Override
    public void onImage(ImageEvent e) {
        ValidationFilter validationFilter = new ValidationFilter(new ImageEventReadable<ImageEvent>(e),coordinates,tolerance);
        try {
            writeToFile(validationFilter.read(),path);
        } catch (StreamCorruptedException e1) {
            e1.printStackTrace();
        }
    }
}
