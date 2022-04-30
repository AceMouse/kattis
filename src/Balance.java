import java.io.*;

public class Balance {
    private static boolean[] stack;
    private static int stackPointer = 0;
    private static int stackSize = 64;
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        BufferedOutputStream writer = new BufferedOutputStream(System.out);
        stack = new boolean[stackSize];
        while (true){
            byte c = reader.read();
            if (c == '\n') break;
            switch (c){
                case '(':
                    if (stackPointer == stackSize){
                        boolean[] newStack = new boolean[stackSize + stackSize];
                        System.arraycopy(stack,0,newStack,0,stackSize);
                        stackSize += stackSize;
                        stack = newStack;
                    }
                    stack[stackPointer++] = true;
                    break;
                case ')':
                    if (stackPointer == 0 || !stack[--stackPointer]){
                        writer.write('0');
                        writer.flush();
                        return;
                    }
                    break;
                case '[':
                    if (stackPointer == stackSize){
                        boolean[] newStack = new boolean[stackSize + stackSize];
                        System.arraycopy(stack,0,newStack,0,stackSize);
                        stackSize += stackSize;
                        stack = newStack;
                    }
                    stack[stackPointer++] = false;
                    break;
                case ']':
                    if (stackPointer == 0 || stack[--stackPointer]){
                        writer.write('0');
                        writer.flush();
                        return;
                    }
                    break;

            }
        }
        writer.write(stackPointer == 0?'1':'0');
        writer.flush();

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
            //if (bufferPointer == bytesRead)
            //    fillBuffer();
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
