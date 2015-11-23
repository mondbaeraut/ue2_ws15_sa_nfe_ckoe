package bvp.filter;

import Catalano.Imaging.FastBitmap;

import filter.AbstractFilter;
import interfaces.IOable;

import java.io.StreamCorruptedException;

/**
 * Created by mod on 11/9/15.
 */
public class SourceFile implements IOable{
    FastBitmap image;
    AbstractFilter abstractFilter;



    public SourceFile(AbstractFilter abstractFilter){
        this.abstractFilter = abstractFilter;
    }
    public SourceFile(FastBitmap image) {
        this.image = image;

    }

    @Override
    public Object read() throws StreamCorruptedException {
        return image;
    }

    public static void main(String[] args) {
        SourceFile s = new SourceFile(new FastBitmap("loetstellen.jpg"));
    }

    @Override
    public void write(Object value) throws StreamCorruptedException {
        abstractFilter.write(process((String) value));
    }
    private FastBitmap process(String name){
        return new FastBitmap(name);
    }
}
