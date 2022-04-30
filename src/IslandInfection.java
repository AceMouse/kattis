import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class IslandInfection {
    private static byte[][] lines;
    private static int R, C;
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        R = reader.nextInt();
        C = reader.nextInt();
        int vi = -1, vj = -1;
        lines = new byte[R][];
        for (int i = 0; i < R; i++) {
            lines[i] = reader.readLine(C);
            for (int j = 0; j < C && vi == -1; j++) {
                if (lines[i][j] == '2'){
                    vi = i;
                    vj = j;
                    break;
                }
            }
        }
        System.out.println(find(vi, vj)?'1':'0');
    }

    private static boolean find(int vi, int vj) {
        if (lines[vi][vj] == '0'){
            return false;
        } else if (lines[vi][vj] == '3'){
            return true;
        }
        lines[vi][vj] = '0';
        return  (vi > 0 && find(vi-1,vj))||
                (vj > 0 && find(vi,vj-1))||
                (vi < R-1 && find(vi+1,vj))||
                (vj < C-1 && find(vi,vj+1));
    }

    static class Reader //copied from some website :D
    {
        final private int BUFFER_SIZE;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader(int s, DataInputStream din) throws IOException {
            BUFFER_SIZE = s;
            this.din = din;
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
            fillBuffer();
        }

        public Reader() throws IOException {
            BUFFER_SIZE = 1<<20;
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
            fillBuffer();
        }

        public Reader(String file_name) throws IOException
        {
            BUFFER_SIZE = 1<<20;
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

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
        public byte[] readLine(int length) throws IOException
        {
            byte[] buf = new byte[length]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1)
            {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return buf;
        }

        public int nextInt() throws IOException
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ' || c == ':')
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

        public long nextLong() throws IOException
        {
            long ret = 0;
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
            if (neg)
                return -ret;
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
        private void skip(int x) {
            bufferPointer += x;
        }
        private void skipToNextWord() throws IOException {
            byte c, cnt = 0;
            while ((c = read()) != -1 && c < 'a' && cnt++ < 5);
            if (--bufferPointer < 0) bufferPointer++;
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
