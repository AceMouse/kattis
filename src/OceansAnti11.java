import java.io.*;

public class OceansAnti11 {
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        int[] fib = new int[10001];
        fib[0] = 1;
        fib[1] = 2;
        for(int i = 2; i<10001; i++){
            fib[i] = (fib[i - 1] +fib[i - 2])%1000000007;
        }
        int T = reader.nextInt();
        BufferedOutputStream out = new BufferedOutputStream(System.out);
        while (T-- >0){
            out.write((fib[reader.nextInt()] + "\n").getBytes());
        }
        out.flush();
    }
    /*private static int N;
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        int T = reader.nextInt();
        BufferedOutputStream out = new BufferedOutputStream(System.out);
        while (T-- >0){
            N = reader.nextInt();
            boolean[] isOne = new boolean[N];
            out.write((((rec(0,isOne,true) + rec(0,isOne,false))/2)%1000000007 + "\n").getBytes());
        }
        out.flush();
    }

    private static int rec(int it, boolean[] isOne, boolean nextOne){
        boolean[] isOneCopy = new boolean[it+1];
        System.arraycopy(isOne,0,isOneCopy,0,it);
        if (it >= N) return 1;
        isOneCopy[it] = nextOne;
        if (it != 0 && isOneCopy[it] && isOneCopy[it-1]) return 0;

        return rec(it+1,isOneCopy, true) + rec(it+1,isOneCopy, false);
    }*/
    static class Reader
    {
        final private int BUFFER_SIZE = 1 << 4;
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
