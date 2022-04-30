import java.io.*;
import java.util.Iterator;
import java.util.stream.Stream;

public class GuessTheDataStructure {
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        final byte[] queueOutput = "queue\n".getBytes();
        final byte[] stackOutput = "stack\n".getBytes();
        final byte[] priorityQueueOutput = "priority queue\n".getBytes();
        final byte[] notSureOutput = "not sure\n".getBytes();
        final byte[] impossibleOutput = "impossible\n".getBytes();
        final byte[][] output = {impossibleOutput,
                                 priorityQueueOutput,
                                 stackOutput,
                                 notSureOutput,
                                 queueOutput,
                                 notSureOutput,
                                 notSureOutput,
                                 notSureOutput
        };
        final byte[] canNotBePQ =    {0, 0, 2, 2, 4, 4, 6, 6}; //maps i to i&0b110
        final byte[] canNotBeStack = {0, 1, 0, 1, 4, 5, 4, 5}; //maps i to i&0b101
        final byte[] canNotBeQueue = {0, 1, 2, 3, 0, 1, 2, 3}; //maps i to i&0b011
        BufferedOutputStream outputStream = new BufferedOutputStream(System.out);
        final boolean[] canBePQ =    {false,true,false,true,false,true,false,true};
        final boolean[] canBeStack = {false,false,true,true,false,false,true,true};
        final boolean[] canBeQueue = {false,false,false,false,true,true,true,true};
        final ByteMaxPQ pq =    new ByteMaxPQ(1000);
        final ByteStack stack = new ByteStack(1000);
        final ByteQueue queue = new ByteQueue(1000);
        Iterator<String> stringStream = new BufferedReader(new InputStreamReader(System.in)).lines().iterator();
        while(stringStream.hasNext()){
            int n = reader.nextInt(), s = 0;
            pq.reset();
            stack.reset();
            queue.reset();
            byte canBe = 7;
            while (n-- > 0) {
                if (reader.nextInt() == 1){
                    s++;
                    byte item = (byte)reader.nextInt();
                    pq.insert(item);
                    stack.push(item);
                    queue.enqueue(item);
                } else {
                    byte item = (byte)reader.nextInt();
                    if (--s<0 || canBe == 0) {
                        canBe = 0;
                        while (n-- > 0){
                            reader.nextInt();
                            reader.nextInt();
                        }
                        break;
                    }
                    if (canBePQ[canBe] && item != pq.delMax())
                        canBe = canNotBePQ[canBe];
                    if (canBeStack[canBe] && item != stack.pop())
                        canBe = canNotBeStack[canBe];
                    if (canBeQueue[canBe] && item != queue.dequeue())
                        canBe = canNotBeQueue[canBe];
                }
            }
            outputStream.write(output[canBe]);
            if (!reader.hasNext()) {
                outputStream.flush();
                return;
            }
        }
    }
    static class Reader //copied from some website :D
    {
        final private int BUFFER_SIZE;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader(int s, DataInputStream din) throws IOException {
            BUFFER_SIZE = s*17;
            this.din = din;
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
            fillBuffer();
        }

        public Reader() throws IOException {
            BUFFER_SIZE = 1<<10;
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
            fillBuffer();
        }

        public Reader(String file_name) throws IOException
        {
            BUFFER_SIZE = 1<<16;
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
        private void skip(int x)
        {
            bufferPointer+=x;
        }
        private void skipToNextWord() throws IOException {
            byte c, cnt = 0;
            while ((c = read()) != -1 && c < 'a' && cnt++ < 5);
            if (--bufferPointer < 0) bufferPointer++;
        }
        private boolean isEOF() throws IOException {
            byte c, cnt = 0;
            while((c = read()) != 26 && cnt++<5);
            bufferPointer-=cnt;
            return c == 26;
        }
        private boolean hasNext() throws IOException {
            if (bufferPointer == bytesRead) fillBuffer();
            return buffer[bufferPointer] != -1;
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
