import java.io.*;

public class UnionFind {
    private static final byte query = 63;
    private static final byte union = 61;
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        BufferedOutputStream writer = new BufferedOutputStream(System.out);
        //StringBuilder out = new StringBuilder();
        byte[] no = "no\n".getBytes();
        byte[] yes = "yes\n".getBytes();
        UF2 uf = new UF2(reader.nextInt());
        int Q = reader.nextInt();
        while (Q-- > 0) {
            byte operation = reader.read();
            int s = reader.nextInt();
            int t = reader.nextInt();
            /*switch(operation) {
                case query:
                    if (uf.find(s) == uf.find(t)) out.append("yes\n");
                    else out.append("no\n");
                    break;
                case union:
                    uf.union(s,t);

            }*/
            /*int sRoot,tRoot;
            if ((sRoot=uf.find(s)) == (tRoot = uf.find(t))) {if (operation == query) out.append("yes\n");}
            else if (operation == query) out.append("no\n");
            else if (operation == union) uf.union(sRoot,tRoot);*/
            int sRoot,tRoot;
            if ((sRoot=uf.find(s)) == (tRoot = uf.find(t))) {if (operation == query) writer.write(yes);}
            else if (operation == query) writer.write(no);
            else if (operation == union) uf.union(sRoot,tRoot);
        }
        //writer.write(new String(out));
        writer.flush();
    }

    static class Reader //copied from some website :D
    {
        final private int BUFFER_SIZE = 1 << 20;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader()
        {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public int nextInt() throws IOException
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do
            {
                ret = ret * 10 + c - '0';
            }  while ((c = read()) >= '0' && c <= '9');

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

