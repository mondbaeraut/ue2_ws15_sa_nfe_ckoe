package bvp.filter;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.FastGraphics;
import Catalano.Imaging.Filters.Threshold;
import Catalano.Imaging.Tools.Blob;
import Catalano.Imaging.Tools.BlobDetection;
import bvp.data.Coordinate;
import bvp.data.SourceFile;
import bvp.pipe.PipeImpl;
import bvp.util.ImageLoader;
import bvp.util.ImageViewer;
import filter.AbstractFilter;
import interfaces.*;
import interfaces.Readable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mod on 11/10/15.
 */
public class CentroidsFilter extends AbstractFilter {
    private BlobDetection blob;

    public CentroidsFilter(interfaces.Readable input) throws InvalidParameterException {
        super(input);
    }

    public CentroidsFilter(Writeable output) throws InvalidParameterException {
        super(output);
    }

    public CentroidsFilter(Readable input, Writeable output) throws InvalidParameterException {
        super(input, output);
    }

    @Override
    public Object read() throws StreamCorruptedException {
        return getBlob();
    }

    @Override
    public void run() {

    }

    private LinkedList<Coordinate> getBlob() {
        blob = new BlobDetection();
        List<Blob> blobList = null;
        LinkedList<Coordinate> result = new LinkedList<>();
        FastBitmap image = null;
        try {
            image = (FastBitmap) readInput();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }


         blobList = blob.ProcessImage(image);
        image.toRGB();
        for (Blob b : blobList) {
            FastGraphics fastGraphics = new FastGraphics(image);
            fastGraphics.setColor(255,0,0);
            for(int i = 0; i < 3; i++) {
                fastGraphics.DrawCircle(b.getCenter().x, b.getCenter().y, i);
            }
            result.add(new Coordinate(b.getCenter().x, b.getCenter().y));
            //ImageViewer imageViewer = new ImageViewer(image);
        }
        saveImage(image);
        return result;
    }

    private void saveImage(FastBitmap image) {
        try {
            BufferedImage bi = image.toBufferedImage();
            File outputfile = new File("saved.png");
            ImageIO.write(bi, "png", outputfile);
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Object value) throws StreamCorruptedException {

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
        List<Coordinate> list = centroidsFilter.getBlob();
        for(Coordinate coordinate : list){
            System.out.println(coordinate.toString());
        }
    }
}
