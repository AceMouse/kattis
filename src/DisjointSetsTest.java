
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Random;

public class DisjointSetsTest {
    private static final int BOUND = 4;
    public static void main(String[] args) throws IOException {
        Writer writer = new PrintWriter(System.out);
        while (true) {
            StringBuilder out1 = new StringBuilder("\nGout:\n");
            StringBuilder out2 = new StringBuilder("\nBout:\n");
            StringBuilder in = new StringBuilder("\nin:\n");
            int n;
            Random random = new Random();
            UFWorks uf1 = new UFWorks(n = (random.nextInt(BOUND + 1) + 1));
            UF uf2 = new UF(n);
            int m = random.nextInt(BOUND + 1) + 1;
            in.append(n).append(' ').append(m).append('\n');
            while (m-- > 0) {
                int operation = random.nextInt(3);
                int s = random.nextInt(n);
                int t = random.nextInt(n);
                in.append(operation).append(' ').append(s).append(' ').append(t).append('\n');
                int sRoot, tRoot;
                if ((sRoot = uf1.find(s)) == (tRoot = uf1.find(t))) {
                    if (operation == 0) out1.append(1).append('\n');
                } else if (operation == 0) out1.append(0).append('\n');
                else if (operation == 1) uf1.union(sRoot, tRoot);
                else uf1.move(s, tRoot, sRoot);
                if ((sRoot = uf2.find(s)) == (tRoot = uf2.find(t))) {
                    if (operation == 0) out2.append(1).append('\n');
                } else if (operation == 0) out2.append(0).append('\n');
                else if (operation == 1) uf2.union(sRoot, tRoot);
                //else uf2.move(s, tRoot, sRoot);
            }
            String sout1, sout2;
            if ((sout1 = new String(out1)).substring(7).equals((sout2 = new String(out2)).substring(7)))
                continue;
            writer.write(new String(in) + '\n' + sout1 + '\n' + sout2);
            writer.flush();
            break;
        }
    }
}
