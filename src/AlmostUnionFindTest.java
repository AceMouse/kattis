import java.io.*;
import java.util.Random;

public class AlmostUnionFindTest {
    private static final byte union = 49;
    private static final byte move = 50;
    private static final byte show = 51;
    public static void main(String[] args) throws IOException {
        Random random = new Random();
        BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream("out.txt"));
        AlmostUF uf;
        AlmostUnionFindSlow ufWorks;
        int i = 0;
        while (true) {
            System.out.println("try: " + i++ + '\n');
            StringBuilder out1 = new StringBuilder("\nout1:\n");
            StringBuilder out2 = new StringBuilder("\nout2:\n");
            StringBuilder in = new StringBuilder("\nin:\n");
            int bound = 100000;
            int n = random.nextInt(bound)+1;
            uf = new AlmostUF(n + 1);
            ufWorks = new AlmostUnionFindSlow(n + 1);
            int Q = random.nextInt(bound)+1;
            in.append(n).append(' ').append(Q).append('\n');
            while (Q-- > 0) {
                byte operation = (byte)(random.nextInt(3)+49);
                int s,t;
                switch (operation) {
                    case union:
                        s = random.nextInt(n)+1;
                        t = random.nextInt(n)+1;
                        in.append(operation).append(' ').append(s).append(' ').append(t).append('\n');
                        uf.union(s, t);
                        ufWorks.union(s, t);
                        break;
                    case move:
                        s = random.nextInt(n)+1;
                        t = random.nextInt(n)+1;
                        in.append(operation).append(' ').append(s).append(' ').append(t).append('\n');
                        uf.move(s, t);
                        ufWorks.move(s,t);
                        break;
                    case show:
                        s = random.nextInt(n)+1;
                        in.append(operation).append(' ').append(s).append('\n');
                        out1.append(uf.show(s));
                        out2.append(ufWorks.show(s));
                        break;
                }
            }
            writer.flush();
            String sout1, sout2;
            if ((sout1 = new String(out1)).substring(7).equals((sout2 = new String(out2)).substring(7)))
                continue;
            writer.write((new String(in) + '\n' + sout1 + '\n' + sout2).getBytes());
            writer.write("\n\n\n\n".getBytes());
            writer.flush();
            break;
        }
    }
}

