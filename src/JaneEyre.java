import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class JaneEyre {
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        Scanner scanner = new Scanner(System.in);
        //long n = reader.nextInt(), m = reader.nextInt(), k = reader.nextInt(), pagesBeforeJaneEyre = 0;
        long n = scanner.nextLong(), m = scanner.nextLong(), k = scanner.nextLong(), pagesBeforeJaneEyre = 0;
        String janeEyre = "Jane Eyre";
        scanner.nextLine();
        while (n-->0){
            String[] line = scanner.nextLine().split("\"");
            //String next = reader.next();
            if (janeEyre.compareTo(line[1]) > 0)
                pagesBeforeJaneEyre += Integer.parseInt(line[2].trim());
            //else
                //reader.nextInt();
        }

        while (m-->0){
            String[] line = scanner.nextLine().split("\"");
            if(pagesBeforeJaneEyre >= Integer.parseInt(line[0].trim())) {
                if (janeEyre.compareTo(line[1]) > 0) {
                    pagesBeforeJaneEyre += Integer.parseInt(line[2].trim());
                }
            } else {
                break;
            }
        }
        System.out.println((pagesBeforeJaneEyre+k));
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

        public String next() throws IOException
        {
            byte c = read();
            while (c != '\"')
                c = read();
            ByteArrayList byteArrayList = new ByteArrayList(20);
            while ((c = read()) != '\"') {
                byteArrayList.add(c);
            }
            return byteArrayList.toString();
        }

        public ByteArrayList readLine() throws IOException
        {
            ByteArrayList buf = new ByteArrayList(100); // line length
            int c;
            while ((c = read()) != -1)
            {
                if (c == '\n')
                    break;
                buf.add((byte) c);
            }
            return buf;
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
