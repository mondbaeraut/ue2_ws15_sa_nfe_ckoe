package bean;

import Catalano.Imaging.FastBitmap;
import interfaces.ImageEvent;
import interfaces.ImageEventHandler;
import interfaces.ImageEventPool;
import interfaces.ImageListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by mod on 11/23/15.
 */
public class DisplayimageBean extends Panel implements ImageEventHandler, ImageListener {

    private ImageEventPool imageEventPool;

    public DisplayimageBean() {
        imageEventPool = new ImageEventPool();
        setBackground(Color.DARK_GRAY);
    }

    @Override
    public void addImageListener(ImageListener listener) {
        imageEventPool.addImageEventHandler(listener);
    }

    @Override
    public void removeImageListener(ImageListener listener) {
        imageEventPool.removeImageEventHandler(listener);
    }

    @Override
    public void onImage(ImageEvent e) {
        FastBitmap fbimage = e.getFastBitmap();
        if (fbimage != null) {
            JPanel panel = new JPanel() {
                @Override
                public void paintComponent(Graphics g) {
                    BufferedImage image = fbimage.toBufferedImage();
                    Graphics2D graphics2d = (Graphics2D) g;
                    graphics2d.drawImage(image, 0, 0, null);
                    super.paintComponents(g);
                }
            };
            panel.setSize(fbimage.getWidth(), fbimage.getHeight());
            this.removeAll();
            add(panel);
            panel.repaint();
            this.repaint();
        } else {
            System.out.println("Image == null");
        }
    }

}
