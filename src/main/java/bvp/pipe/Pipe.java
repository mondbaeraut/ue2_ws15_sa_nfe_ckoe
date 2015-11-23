package bvp.pipe;

/**
 * Created by mod on 11/9/15.
 */
public interface Pipe<T> {
    T read() throws InterruptedException;
    boolean write(T object);
}
