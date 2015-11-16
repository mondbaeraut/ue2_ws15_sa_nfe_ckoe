package bvp.pipe;

import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by mod on 11/16/15.
 */
public class PipeBufferImpl<T> implements Pipe {
    ArrayBlockingQueue buffer;
    public PipeBufferImpl(int maxsize) {
        buffer = new ArrayBlockingQueue(maxsize);
    }

    @Override
    public synchronized Object read() throws InterruptedException {
        Object ob = buffer.take();
        return ob;

    }

    @Override
    public synchronized void write(Object object) throws InterruptedException {
        buffer.put(object);
    }
}
