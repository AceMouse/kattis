import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RightOfWay {
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        Writer writer = new PrintWriter(System.out);
        String[] input = reader.readLine().toLowerCase().split(" ");
        Map<String, String> opposites = new HashMap<>(4);
        opposites.put("south", "north");
        opposites.put("east", "west");
        opposites.put("west", "east");
        opposites.put("north", "south");
        Map<String, String> lefts = new HashMap<>(4);
        lefts.put("south", "west");
        lefts.put("east", "south");
        lefts.put("west", "north");
        lefts.put("north", "east");
        Map<String, String> rights = new HashMap<>(4);
        rights.put("south", "east");
        rights.put("east", "north");
        rights.put("west", "south");
        rights.put("north", "west");
        boolean strait = input[1].equals(opposites.get(input[0]));
        boolean left = input[1].equals(lefts.get(input[0]));
        boolean incomingStraight = input[2].equals(opposites.get(input[0]));
        boolean incomingRight = input[2].equals(rights.get(input[0]));
        if ((strait && incomingRight) || (left && (incomingStraight || incomingRight))) writer.write("yes");
        else writer.write("no");
        writer.flush();

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
