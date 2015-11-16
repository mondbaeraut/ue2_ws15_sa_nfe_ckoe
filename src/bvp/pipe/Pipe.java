package bvp.pipe;

/**
 * Created by mod on 11/9/15.
 */
public interface Pipe<T> {
    T read() throws InterruptedException;
    void write(T object) throws InterruptedException;
}
