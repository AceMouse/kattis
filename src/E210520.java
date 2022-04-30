import java.util.Stack;

public class E210520 {
    public static void main(String[] args) {

    }
    // java
    private static class Mystery {
        boolean[] A;
        int s;

        public Mystery(int N) {
            A = new boolean[N];
            s = 0;
        }

        void add(int i) {
            if (!contains(i)) {
                A[i] = true;
                s += 1;
            }
        }

        void remove(int i) {
            if (contains(i)) {
                A[i] = false;
                s -= 1;
            }
        }
        int some() {
            for (int i = 0; i < A.length; i += 1)
                if (!contains(i))
                    return i;
            return -1;
        }

        int size() {
            return s;
        }

        boolean contains(int i) {
            return A[i];
        }
    }
}
