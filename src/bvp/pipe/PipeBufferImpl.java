package bvp.pipe;

import interfaces.IOable;

import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by mod on 11/16/15.
 */
public class PipeBufferImpl<T> implements IOable {
    ArrayBlockingQueue buffer;
    public PipeBufferImpl(int maxsize) {
        buffer = new ArrayBlockingQueue(maxsize);
    }

    @Override
    public Object read(){
        Object ob = null;
        try {
            ob = buffer.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ob;

    }

    @Override
    public void write(Object object){
        try {
            buffer.put(object);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
