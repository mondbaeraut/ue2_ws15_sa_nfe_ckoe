package bvp.pipe;

import filter.AbstractFilter;
import interfaces.IOable;

import java.io.StreamCorruptedException;

/**
 * Created by mod on 11/9/15.
 */
public class PipeImpl<T> implements IOable{
    AbstractFilter abstractFilter;

    public PipeImpl(AbstractFilter abstractFilter){
        this.abstractFilter = abstractFilter;
    }
    @Override
    public T read() {
        try {
            return (T)abstractFilter.read();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void write(Object value) throws StreamCorruptedException {
    }
}
