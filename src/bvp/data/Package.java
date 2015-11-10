package bvp.data;

/**
 * Created by mod on 11/9/15.
 */
public interface Package<T> {
    T read();
    void write(T object);

}
