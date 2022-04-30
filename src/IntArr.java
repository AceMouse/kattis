import java.util.Arrays;

public class IntArr{
    int[] arr;
    public IntArr(int[] arr) {
        this.arr = arr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntArr intArr = (IntArr) o;
        return Arrays.equals(arr, intArr.arr);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(arr);
    }
}
