package bean;


import Catalano.Imaging.FastBitmap;

/**
 * Created by mod on 11/9/15.
 */
public class SourceFile {
    private FastBitmap image;

    public SourceFile(String imagename) {
        try {
            this.image = new FastBitmap(imagename);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public FastBitmap getImage() {
        return image;
    }
}
