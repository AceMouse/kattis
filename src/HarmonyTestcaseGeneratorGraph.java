import java.io.*;
import java.util.HashMap;
import java.util.Random;


public class HarmonyTestcaseGeneratorGraph {
    private Random random = new Random();
    //public final Reader reader = new Reader();
    private final Writer in;
    public final int V;
    private final IntArrayList[] adj;
    private final LongSet harmonyEdges;
    public HarmonyTestcaseGeneratorGraph(int t) throws IOException {
        int bound = t;
        in = new BufferedWriter(new FileWriter("itu.harmony/testcases/" + t + ".in"));
        //adj = new IntArrayList[V = reader.nextInt()];
        adj = new IntArrayList[V = random.nextInt(bound/2+1)+bound/2+1];
        int i = -1;
        while (++i < V) {
            adj[i] = new IntArrayList(3);
        }
        //int E = reader.nextInt();
        int E = random.nextInt(bound/2+1)+bound/2+1;
        in.write(V + " " + E + "\n");
        harmonyEdges = new LongSet(E+E+1,4);
        i = -1;
        while (++i < E) {
            addEdge();
        }
        in.flush();
        in.close();
    }

    public LongSet getHarmonyEdges(){
        return harmonyEdges;
    }
    private void addEdge() throws IOException {
        long lv, lw;
        //adj[(int)(lv=reader.nextLong())].add((int)(lw = reader.nextLong()));
        adj[(int)(lv = (long)random.nextInt(V))].add((int)(lw = (long)random.nextInt(V)));
        int x = random.nextInt(2);
        in.write(lv + " " + lw + " " + x + '\n' );
        adj[(int)lw].add((int)lv);
        //if (reader.nextInt() == 0) {
        if (x == 0) {
            harmonyEdges.add((lv << 18) + lw);
            harmonyEdges.add((lw << 18) + lv);
        }
    }

    public IntArrayList adj(int v){
        return adj[v];
    }

    static class Reader //copied from some website :D
    {
        final private int BUFFER_SIZE = 200001;//1 << 18;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() throws IOException {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
            fillBuffer();
        }

        /*public Reader(String file_name) throws IOException
        {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }*/

        public String readLine() throws IOException
        {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1)
            {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            //boolean neg = (c == '-');
            //if (neg)
            //    c = read();
            do
            {
                ret = ret * 10 + c - '0';
            }  while ((c = read()) >= '0' && c <= '9');

            //if (neg)
            //    return -ret;
            return ret;
        }
        public short nextShort() throws IOException
        {
            short ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            do
            {
                ret = (short) (ret * 10 + c - '0');
            }  while ((c = read()) >= '0' && c <= '9');
            return ret;
        }

        public long nextLong() throws IOException
        {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            //boolean neg = (c == '-');
            //if (neg)
            //    c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            //if (neg)
            //    return -ret;
            return ret;
        }

        public double nextDouble() throws IOException
        {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');

            if (c == '.')
            {
                while ((c = read()) >= '0' && c <= '9')
                {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException
        {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException
        {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException
        {
            if (din == null)
                return;
            din.close();
        }
    }
}