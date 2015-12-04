package bean;

import Catalano.Imaging.FastBitmap;
import interfaces.IOable;
import interfaces.ImageEvent;
import interfaces.ImageEventHandlerImpl;

import java.awt.event.ActionEvent;
import java.io.StreamCorruptedException;

/**
 * Created by mod on 11/23/15.
 */
public class LoadImageBean extends ImageEventHandlerImpl {

    private String filename = "";
    private int maxCalls = 1;
    private SourceFile sourceFile;
    private boolean started = false;
    // private SourceFile sourceFile = new SourceFile();
    public LoadImageBean() {

    }

    public void start(ActionEvent event) {
        //ImageEvent imageEvent = new ImageEvent(this, new FastBitmap(filename));
        if (filename.length() > 0) {
            started = true;
            sourceFile = new SourceFile(filename, maxCalls);
            new Thread(sourceFile).start();
        }
    }

    /*
    -------------------------- SETTER GETTER--------------------------------------------
     */
    public void setFilename(String value) {
        this.filename = value;
        //    sourceFile = new SourceFile(value);
    }

    public String getFilename() {
        return filename;
    }

    public int getMaxCalls() {
        return maxCalls;

    }

    public void setMaxCalls(int maxCalls) {
        this.maxCalls = maxCalls;
    }


    public class SourceFile implements IOable, Runnable {
        private FastBitmap image = null;
        private ImageEvent imageEvent = null;
        private int calls = 0;
        private int maxcalls = 0;

        public SourceFile() {

        }

        public SourceFile(String name, int maxcalls) {
            try {
                image = new FastBitmap(name);
                this.maxcalls = maxcalls;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public FastBitmap getImage() {
            return image;
        }

        @Override
        public Object read() throws StreamCorruptedException {
            return null;
        }

        @Override
        public void run() {
            boolean endreached = false;
            do {
                for (int i = 0; i < maxcalls; i++) {
                    ImageEvent imageEvent = new ImageEvent(this, image);
                    notifyAllListener(imageEvent);
                }
                endreached = true;
            } while (!endreached);
            started = false;
        }

        @Override
        public void write(Object value) throws StreamCorruptedException {

        }

        /*public FastBitmap getImage() {
            return image;
        }*/
    }


}
