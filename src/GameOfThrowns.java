import java.io.DataInputStream;
import java.io.IOException;

public class GameOfThrowns {
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        int n = reader.nextInt(), k = reader.nextInt();
        //String[] commands = reader.readLine().split(" ");
        int currentChild;
        int[] previousChildren = new int[k+1];
        int stackPointer = 1;
        for (int i = 0;i<k;){
            switch (reader.read()) {
                case ' ':
                    break;
                case 'u':
                    reader.skipread();
                    stackPointer -= reader.nextInt();
                    i++;
                    break;
                default:
                    reader.unread();
                    previousChildren[stackPointer] = previousChildren[stackPointer++-1] + reader.nextInt();
                    i++;
            }
        }
        currentChild = previousChildren[--stackPointer];
        while ((currentChild += n) < 0);
        System.out.println(currentChild%n);
    }
    static class Reader //copied from some website :D
    {
        final private int BUFFER_SIZE = 700;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader()
        {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException
        {
            byte[] buf = new byte[700]; // line length
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
            //boolean neg = (c == '-');
            //if (neg)
            //    c = read();
            do
            {
                ret = (short) (ret * 10 + c - '0');
            }  while ((c = read()) >= '0' && c <= '9');

            //if (neg)
            //    return -ret;
            return ret;
        }
        public short nextShort(byte firstnum) throws IOException
        {
            short ret = firstnum;
            byte c;
            //boolean neg = (c == '-');
            //if (neg)
            //    c = read();
            while ((c = read()) >= '0' && c <= '9'){
                ret = (short) (ret * 10 + c - '0');
            }

            //if (neg)
            //    return -ret;
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

        private void unread()
        {
            bufferPointer--;
        }

        private void skipread()
        {
            bufferPointer+=4;
        }

        public void close() throws IOException
        {
            if (din == null)
                return;
            din.close();
        }
    }
}

