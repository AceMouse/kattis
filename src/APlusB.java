import java.io.*;
import java.util.Arrays;

public class APlusB {
    final static int OFFSET = 50000;
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        Writer writer = new PrintWriter(System.out);
        int n, N;
        int[] input = new int[n = N = reader.nextInt()];
        int[] occurrences = new int[100001];

        while (n-->0) occurrences[(input[n] = reader.nextInt())+OFFSET]++;
        //Arrays.sort(input);
        long cnt = 0;
        int hi;
        int lo;
        for (int k = 0; k < N; k++){
            if (input[k] > 0) {lo = input[k]/2;hi = lo + input[k]%2;}
            else if (input[k] == 0) hi = lo = 0;
            else {hi = input[k]/2; lo = hi + input[k]%2;}
            hi += OFFSET;
            lo += OFFSET;
            while (hi<100001 && lo >=0){
                if (hi != lo) {cnt += occurrences[hi]*occurrences[lo]*2;}
                else if (input[k]+OFFSET == lo) cnt += (occurrences[hi]-1)*(occurrences[lo]-2);
                else cnt += occurrences[hi]*(occurrences[lo]-1);
                hi++;
                lo--;
            }
        }

        writer.write(String.valueOf(cnt));
        writer.flush();
    }
    static class Reader
    {
        final private int BUFFER_SIZE = 1 << 6;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader()
        {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException
        {
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

        public short nextShort() throws IOException
        {
            short ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do
            {
                ret = (short) (ret * 10 + c - '0');
            }  while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return (short) -ret;
            return ret;
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
