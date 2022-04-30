import java.util.Objects;

public class IntTriplet {
    final public int x;
    final public int y;
    final public int z;
    public IntTriplet(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        IntTriplet that = (IntTriplet) o;
        return x == that.x &&
                y == that.y &&
                z == that.z;
    }

    @Override
    public int hashCode() {
        return x<<20 + y<<10 + z;
    }
}
