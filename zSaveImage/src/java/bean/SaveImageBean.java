package bean;

import interfaces.ImageEvent;
import interfaces.ImageListener;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StreamCorruptedException;

/**
 * Created by mod on 11/23/15.
 */
public class SaveImageBean implements ImageListener {
    private String savepath = "";


    public SaveImageBean(String savepath) {
        this.savepath = savepath;
    }

    public SaveImageBean() {
    }

    public String getsavepath() {
        return savepath;
    }

    public void setsavepath(String savepath) {
        this.savepath = savepath;
    }

    @Override
    public void onImage(ImageEvent e) {
        try {
            BufferedImage bi = e.getFastBitmap().toBufferedImage();
            File outputfile = new File(savepath + ".png");
            ImageIO.write(bi, "png", outputfile);
        } catch (StreamCorruptedException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
