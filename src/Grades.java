import java.io.*;
import java.util.Arrays;

public class Grades {
    public static void main(String[] args) throws IOException {
        DataInputStream din = new DataInputStream(System.in);
        int N = 0;
        byte c = (byte)din.read();
        while (c <= ' ')
            c = (byte)din.read();
        do
        {
            N = N * 10 + c - '0';
        }  while ((c = (byte)din.read()) >= '0' && c <= '9');
        Reader reader = new Reader(N, din);
        //Reader reader = new Reader();
        BufferedOutputStream writer = new BufferedOutputStream(System.out);
        Entry[] entries = new Entry[N];
        byte grade, mod;
        while (N-- > 0){
            while ((c = reader.read())<=' ');
            byte[] name = new byte[20];
            int i = 0;
            do
            {
                name[i++] = c;
            } while ((c=reader.read()) != ' ');
            grade = (byte) ((reader.read()-'A')*2);
            if ((c = reader.read()) == 'X') {
                grade--;
                c = reader.read();
            }
            mod = 0;
            while (c == '+' || c == '-'){
                mod += 44-c;
                c = reader.read();
            }
            entries[N] = new Entry(name, grade, mod);
        }
        Arrays.sort(entries);
        while (N++<entries.length-1){
            writer.write(entries[N].name);
            writer.write('\n');
        }
        writer.flush();
    }

    static class Entry implements Comparable<Entry> {
        private final byte grade;
        private final byte mod;
        public final byte[] name;

        public Entry(byte[] name, byte grade, byte mod) {
            this.grade = grade;
            this.mod = mod;
            this.name = name;
        }

        @Override
        public int compareTo(Entry that) {
            if (this.grade != that.grade) return this.grade - that.grade;
            if (this.mod != that.mod) return that.mod-this.mod;
            for (int i = 0; i<20;i++){
                if (this.name[i] != that.name[i]) return this.name[i] - that.name[i];
                else if (this.name[i] == 0) return 0;
            }
            return 0;
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
            BUFFER_SIZE = 1<<16;
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

