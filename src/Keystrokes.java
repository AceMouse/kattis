import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class Keystrokes {
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        BufferedOutputStream out = new BufferedOutputStream(System.out);
        ByteStack beforePointer = new ByteStack(1000000);
        ByteStack afterPointer = new ByteStack(1000000);
        byte c;
        while ((c = reader.read()) != '\n'){
            switch (c){
                case 'L':
                    afterPointer.push(beforePointer.pop());
                    break;
                case 'R':
                    beforePointer.push(afterPointer.pop());
                    break;
                case 'B':
                    beforePointer.pop();
                    break;
                default:
                    beforePointer.push(c);
            }
        }
        byte[] outArr = new byte[beforePointer.size() + afterPointer.size()];
        for (int i = 0; i<beforePointer.size(); i++) {
            outArr[i] = beforePointer.get(i);
        }
        for (int i = beforePointer.size(); i<outArr.length; i++) {
            outArr[i] = afterPointer.pop();
        }
        out.write(outArr);
        out.flush();
    }
    static class Reader
    {
        final private int BUFFER_SIZE = 1 << 16;
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

        public boolean hasNext() throws IOException {
            if (bufferPointer == bytesRead) fillBuffer();
            return buffer[bufferPointer] != -1;
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
