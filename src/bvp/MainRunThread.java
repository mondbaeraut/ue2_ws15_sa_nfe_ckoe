package bvp;

import Catalano.Imaging.FastBitmap;
import bvp.data.Coordinate;
import bvp.data.SourceFile;
import bvp.filter.*;
import bvp.pipe.BufferedSyncPipe;
import bvp.util.ImageLoader;
import com.sun.management.ThreadMXBean;
import interfaces.*;
import interfaces.Readable;
import sun.net.www.protocol.file.FileURLConnection;

import java.awt.*;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mod on 11/19/15.
 */
public class MainRunThread {
    public static void main(String[] args) {
        int rep = 50;
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


        ROIFilter roiFilter = new ROIFilter(sourceFile, (Writeable) pipe, new Coordinate(0, 50), new Rectangle(448, 50), rep);
        ThresholdFilter thresholdFilter = new ThresholdFilter((interfaces.Readable) pipe, (Writeable) pipe2);
        AntialasingFilter antialasingFilter = new AntialasingFilter((Readable) pipe2, (Writeable) pipe3);
        CentroidsFilter centroidsFilter = new CentroidsFilter((Readable) pipe3, (Writeable) pipe4);
        ValidationFilter validationFilter = new ValidationFilter((Readable) pipe4, (Writeable) pipe5, cordinates, 20);
        Sink sink = new Sink((Readable)pipe5, "Documentation/Result/result");
        boolean reached = false;
        int number = 0;
        File[] delFile = new File("./Documentation/Result").listFiles();
        for(File f:delFile){
            f.delete();
        }
        long startTime = System.nanoTime();

        new Thread(roiFilter).start();
        new Thread(thresholdFilter).start();
        new Thread(antialasingFilter).start();
        new Thread(centroidsFilter).start();
        new Thread(validationFilter).start();
        Thread sinkthred = new Thread(sink);
        sinkthred.start();
        while(!reached){
            number = new File("./Documentation/Result").listFiles().length;
            if(rep == number){
                reached = true;
            }
        }
        long stopTime = System.nanoTime();
        System.out.println("Time:" + ((double)stopTime - startTime) / 1000000000.0 + " sec");

    }

}
