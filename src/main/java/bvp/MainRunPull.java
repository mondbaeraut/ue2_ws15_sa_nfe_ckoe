package bvp;

import Catalano.Imaging.FastBitmap;
import bvp.data.Coordinate;
import bvp.filter.SourceFile;
import bvp.filter.*;
import bvp.pipe.PipeImpl;
import bvp.util.ImageLoader;
import interfaces.Readable;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mod on 11/19/15.
 */
public class MainRunPull {
    public static void main(String[] args) {
        int repetetion = 1;
        long startTime = System.nanoTime();

        for(int i = 0; i < repetetion; i++){
            try {
                FastBitmap fastBitmap = ImageLoader.loadImage("loetstellen.jpg");
                SourceFile sourceFile = new SourceFile(fastBitmap);

                List<Coordinate> cordinates = new LinkedList<>();
                cordinates.add(new Coordinate(6, 74));
                cordinates.add(new Coordinate(396, 78));
                cordinates.add(new Coordinate(264, 78));
                cordinates.add(new Coordinate(201, 78));
                cordinates.add(new Coordinate(330, 78));
                cordinates.add(new Coordinate(134, 78));
                cordinates.add(new Coordinate(72, 78));

                ROIFilter roiFilter = new ROIFilter((Readable) sourceFile, new Coordinate(0, 50), new Rectangle(448, 50));
                PipeImpl pipe = new PipeImpl(roiFilter);
                ThresholdFilter thresholdFilter = new ThresholdFilter((interfaces.Readable) pipe);
                PipeImpl pipe2 = new PipeImpl(thresholdFilter);
                AntialasingFilter antialasingFilter = new AntialasingFilter((interfaces.Readable) pipe2);
                PipeImpl pipe3 = new PipeImpl(antialasingFilter);
                CentroidsFilter centroidsFilter = new CentroidsFilter((Readable) pipe3);
                PipeImpl pipe4 = new PipeImpl(centroidsFilter);
                ValidationFilter validationFilter = new ValidationFilter((Readable) pipe4, cordinates, 20);
                PipeImpl pipe5 = new PipeImpl(validationFilter);
                Sink sink = new Sink((Readable)pipe5, "Documentation/Result/result");
                sink.read();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        long stopTime = System.nanoTime();
        System.out.println("Time:" + ((double)stopTime - startTime) / 1000000000.0 + " sec");
    }
}
