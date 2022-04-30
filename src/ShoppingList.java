import java.io.*;
import java.util.*;

public class ShoppingList {
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        Writer writer = new PrintWriter(System.out);
        StringBuilder out = new StringBuilder();
        int n = reader.nextInt();
        reader.readLine();
        TreeSet<String> set1 = new TreeSet<>(Arrays.asList(reader.readLine().split(" ")));
        while (n-- > 1){
            Iterator<String> iterator1 = set1.iterator();
            if (!iterator1.hasNext()) break;
            Iterator<String> iterator2 = new TreeSet<>(Arrays.asList(reader.readLine().split(" "))).iterator();
            TreeSet<String> result = new TreeSet<>();
            String next1 = iterator1.next();
            String next2 = iterator2.next();
            while (true){
                if (next1.compareToIgnoreCase(next2) == 0) {
                    result.add(next1);
                    if (!(iterator1.hasNext() && iterator2.hasNext())) break;
                    next1 = iterator1.next();
                    next2 = iterator2.next();
                } else if (next1.compareToIgnoreCase(next2) < 0) {
                    if (!iterator1.hasNext()) break;
                    next1 = iterator1.next();
                } else {
                    if (!iterator2.hasNext()) break;
                    next2 = iterator2.next();
                }
            }
            set1 = result;
        }
        out.append(set1.size()).append('\n');
        for (String item : set1) {
            out.append(item).append('\n');
        }
        writer.write(new String(out));
        writer.flush();

    }
    static class Reader
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

        public Reader(String file_name) throws IOException
        {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException
        {
            byte[] buf = new byte[50000]; // line length
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
