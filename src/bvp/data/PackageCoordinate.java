package bvp.data;

/**
 * Created by mod on 11/13/15.
 */
public class PackageCoordinate<T> implements Package {
    private Coordinate corner;
    private T value;

    public PackageCoordinate(Coordinate corner, T value) {
        this.corner = corner;
        this.value = value;
    }

    @Override
    public T getID() {
        return (T)corner;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "PackageCoordinate{" +
                "corner=" + corner +
                ", value=" + value +
                '}';
    }
}
