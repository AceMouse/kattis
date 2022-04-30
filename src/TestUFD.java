import java.util.Random;

public class TestUFD {
    public static void main(String[] args){
        int seed = 0;
        while (true){
            UFDNN ufdnn = new UFDNN(10);
            UFD ufd = new UFD(10);
            Random random = new Random(2089);
            for (int i = 0; i < 10; i++) {
                switch (random.nextInt(3)) {
                    case 0 -> {
                        int p = random.nextInt(10);
                        if (ufd.find(p) != ufdnn.find(p)) {
                            System.out.println(seed);
                            return;
                        }
                    }
                    case 1 -> {
                        int t = random.nextInt(10);
                        int s = random.nextInt(10);
                        ufd.union(t, s);
                        ufdnn.union(t, s);
                    }
                    case 2 -> {
                        int q = random.nextInt(10);
                        ufd.delete(q);
                        ufdnn.delete(q);
                    }
                }
            }

        }
    }
}
