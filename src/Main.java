/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Artistic.HeatMap;
import Catalano.Imaging.Filters.DistanceTransform;
import Catalano.Imaging.Filters.FillHoles;
import Catalano.Imaging.Filters.Invert;
import Catalano.Imaging.Filters.Threshold;

import javax.swing.*;

/**
 *
 * @author Diego
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Original image
        //http://pixabay.com/pt/brown-passeio-cavalo-animal-r%C3%A1pido-48394/
        FastBitmap fb = new FastBitmap("horse.png");
        JOptionPane.showMessageDialog(null, fb.toIcon(), "Original image", JOptionPane.PLAIN_MESSAGE);
        
        //Pre-process
        fb.toGrayscale();
        Threshold t = new Threshold(1);
        t.applyInPlace(fb);
        
        FillHoles fh = new FillHoles(0, 10);
        fh.applyInPlace(fb);
        
        JOptionPane.showMessageDialog(null, fb.toIcon(), "Pre-process", JOptionPane.PLAIN_MESSAGE);

        Invert invert = new Invert();
        invert.applyInPlace(fb);
        JOptionPane.showMessageDialog(null, fb.toIcon(), "Invert", JOptionPane.PLAIN_MESSAGE);
    }
    
}
