import java.io.*;

public class Tildes {
    private static final byte query = 115; //'s'
    public static void main(String[] args) throws IOException {
        byte query = Tildes.query;
        Reader reader = new Reader();
        BufferedOutputStream writer = new BufferedOutputStream(System.out);
        StringBuilder out = new StringBuilder();
        UFTildes uf = new UFTildes(reader.nextInt()+1);
        int Q = reader.nextInt();
        while (Q-- > 0) {
            if (reader.read() == query) {
                out.append(uf.size(reader.nextInt())).append('\n');
            }
            else uf.union(reader.nextInt(),reader.nextInt());
        }
        writer.write(new String(out).getBytes());
        writer.flush();
    }

    static class Reader //copied from some website :D
    {
        final private int BUFFER_SIZE = 1 << 25;
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

