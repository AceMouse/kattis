import java.io.*;

public class AlmostUnionFind {
    private static final byte union = 49;
    private static final byte move = 50;
    private static final byte show = 51;
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        BufferedOutputStream writer = new BufferedOutputStream(System.out);
        AlmostUF uf;
        int i = 0;
        while (reader.hasNext()) {
            uf = new AlmostUF(reader.nextInt() + 1);
            int Q = reader.nextInt();
            while (Q-- > 0) {
                byte operation = reader.read();
                switch (operation) {
                    case union:
                        uf.union(reader.nextInt(), reader.nextInt());
                        break;
                    case move:
                        uf.move(reader.nextInt(), reader.nextInt());
                        break;
                    case show:
                        writer.write(uf.show(reader.nextInt()).getBytes());
                        break;
                }
            }
        }
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
        private boolean hasNext() throws IOException {
            if (bufferPointer == bytesRead) fillBuffer();
            return buffer[bufferPointer] != -1;
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

