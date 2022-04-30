import java.io.*;
import java.util.*;

public class ReconstructingTapeArt {
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        int n = reader.nextInt()+1;
        int[] beginnings = new int[n];
        int[] endings = new int[n];
        Stack<Integer> openStack = new Stack<>();
        Set<Integer> closed = new HashSet<>();
        Set<Integer> open = new HashSet<>();
        int[] colours = new int[n];
        int colourAmount = 0;
        int lastColour = 0;
        int c;
        for (int i = 1; i<n; i++) {
            int currentColour = reader.nextInt();
            if (closed.contains(currentColour)){
                System.out.println("IMPOSSIBLE\n");
                return;
            } else if (currentColour != lastColour && open.contains(currentColour)) {
                while((c = openStack.pop()) != currentColour) {
                    closed.add(c);
                    open.remove(c);
                }
                openStack.push(c);
            } else if (beginnings[currentColour] == 0) {
                open.add(openStack.push(currentColour));
                beginnings[colours[colourAmount++] = currentColour] = endings[lastColour = currentColour] = i;
                continue;
            }
            endings[lastColour = currentColour] = i;
        }
        BufferedOutputStream writer = new BufferedOutputStream(System.out);
        StringBuilder out = new StringBuilder();
        out.append(colourAmount).append('\n');
        for (int i = 0; i<colourAmount;i++){
            out.append(beginnings[c = colours[i]]).append(' ').append(endings[c]).append(' ').append(c).append('\n');
        }

        writer.write(new String(out).getBytes());
        writer.flush();

    }
    static class Reader
    {
        final private int BUFFER_SIZE = 1 << 10;
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