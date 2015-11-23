package bvp;

/**
 * Created by mod on 11/19/15.
 */
public class MainRunPush {
/*    public static void main(String[] args) {
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
                //Sink sink = new Sink("savedit");
                PipeImpl pipe = new PipeImpl(sink);
                ValidationFilter validationFilter = new ValidationFilter((Writeable) pipe);
                PipeImpl pipe2 = new PipeImpl(validationFilter);
                CentroidsFilter centroidsFilter = new CentroidsFilter((Writeable) pipe2);
                PipeImpl pipe3 = new PipeImpl(centroidsFilter);
                AntialasingFilter antialasingFilter = new AntialasingFilter((Writeable) pipe3);
                PipeImpl pipe4 = new PipeImpl(antialasingFilter);
                ThresholdFilter thresholdFilter = new ThresholdFilter((Writeable) pipe4);
                PipeImpl pipe5 = new PipeImpl(thresholdFilter);
                ROIFilter roiFilter = new ROIFilter((Writeable) pipe5, new Coordinate(0, 50), new Rectangle(448, 50));
                SourceFile file = new SourceFile(roiFilter);
                file.write("loetstellen.jpg");
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        long stopTime = System.nanoTime();
        System.out.println("Time:" + ((double)stopTime - startTime) / 1000000000.0 + " sec");
    }*/
}
