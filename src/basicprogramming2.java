import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class basicprogramming2 {
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        Writer writer = new Writer();
        int N = reader.nextInt();
        int t = reader.nextInt();
        IntSet set;
        int[] numbers;
        switch (t){
            case 1:
                set = new IntSet(N/4, 4);
                for (int i = 0; i < N; i++) {
                    int nr = reader.nextInt();
                    if (set.contains(7777-nr)) {
                        writer.write("Yes");
                        writer.flush();
                        return;
                    }
                    set.add(nr);
                }
                writer.write("No");
                writer.flush();
                return;
            case 2:
                set = new IntSet(N/4, 4);
                for (int i = 0; i < N; i++) {
                    int nr = reader.nextInt();
                    if (set.contains(nr)) {
                        writer.write("Contains duplicate");
                        writer.flush();
                        return;
                    }
                    set.add(nr);
                }
                writer.write("Unique");
                writer.flush();
                return;
            case 3:
                HashMap<Integer, Integer> counts = new HashMap<>();
                for (int i = 0; i < N; i++) {
                    int nr = reader.nextInt();
                    int val = counts.getOrDefault(nr, 0)+1;
                    counts.put(nr, val);
                    if (val > N/2) {
                        writer.write(nr);
                        writer.flush();
                        return;
                    }
                }
                writer.write(-1);
                writer.flush();
                return;
            case 4:
                numbers = new int[N];
                for (int i = 0; i < N; i++) {
                    numbers[i] = reader.nextInt();
                }
                Arrays.sort(numbers);
                if ((N & 1) == 0) {
                    writer.write(numbers[N / 2 - 1]);
                    writer.write(' ');
                    writer.write(numbers[N / 2]);
                }else
                    writer.write(numbers[N / 2]);
                writer.flush();
                return;
            case 5:
                numbers = new int[N];
                for (int i = 0; i < N; i++) {
                    numbers[i] = reader.nextInt();
                }
                Arrays.sort(numbers);
                for (int i = 0; i < N; i++) {
                    if (numbers[i] >= 1000)
                        break;
                    if (numbers[i] < 100)
                        continue;
                    writer.write(numbers[i]);
                    writer.write(' ');
                }
                writer.flush();
        }
    }

    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public boolean hasNext() throws IOException {
            if (bufferPointer == bytesRead) fillBuffer();
            return buffer[bufferPointer] != -1;
        }

        public String next() throws IOException {
            byte[] buf = new byte[80]; // line length
            int cnt = 0, c;
            while ((c = read()) == ' ') ;
            do {
                if (c == ' ' || c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            } while ((c = read()) != -1);
            return new String(buf, 0, cnt);
        }

        public byte[] readLine() throws IOException {
            byte[] buf = new byte[80]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return Arrays.copyOf(buf, cnt);
        }

        public String nextLine() throws IOException {
            byte[] buf = new byte[80]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }


        public void skip(int x) {
            bufferPointer += x;
        }

        public void skipTil(char c) throws IOException {
            while (read() != c) ;
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException {
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

        public double nextDouble() throws IOException {
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

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }

    static class Writer {
        private final BufferedOutputStream outputStream = new BufferedOutputStream(System.out);
        static final byte[] DigitTens = {
                '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
                '1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
                '2', '2', '2', '2', '2', '2', '2', '2', '2', '2',
                '3', '3', '3', '3', '3', '3', '3', '3', '3', '3',
                '4', '4', '4', '4', '4', '4', '4', '4', '4', '4',
                '5', '5', '5', '5', '5', '5', '5', '5', '5', '5',
                '6', '6', '6', '6', '6', '6', '6', '6', '6', '6',
                '7', '7', '7', '7', '7', '7', '7', '7', '7', '7',
                '8', '8', '8', '8', '8', '8', '8', '8', '8', '8',
                '9', '9', '9', '9', '9', '9', '9', '9', '9', '9',
        };

        static final byte[] DigitOnes = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        };

        private static byte[] getBytes(int i) { // copied from Java Integer class
            boolean neg = i < 0;
            if (!neg)
                i = -i;
            int charPos = 0, p = -10;
            for (int j = 1; j < 10; j++) {
                if (i > p) {
                    charPos = j;
                    break;
                }
                p = 10 * p;
            }
            if (charPos == 0)
                charPos = 10;
            if (neg)
                charPos++;
            byte[] buf = new byte[charPos];
            buf[0] = '-';
            int q, r;

            // Generate two digits per iteration
            while (i <= -100) {
                q = i / 100;
                r = (q * 100) - i;
                i = q;
                buf[--charPos] = DigitOnes[r];
                buf[--charPos] = DigitTens[r];
            }

            // We know there are at most two digits left at this point.
            q = i / 10;
            r = (q * 10) - i;
            buf[--charPos] = (byte) ('0' + r);

            // Whatever left is the remaining digit.
            if (q < 0) {
                buf[--charPos] = (byte) ('0' - q);
            }
            return buf;
        }

        private static byte[] getBytes(long i) { // copied from Java Integer class
            boolean neg = i < 0;
            if (!neg)
                i = -i;
            int charPos = 0;
            long p = -10;
            for (int j = 1; j < 20; j++) {
                if (i > p) {
                    charPos = j;
                    break;
                }
                p = 10 * p;
            }
            if (charPos == 0)
                charPos = 20;
            if (neg)
                charPos++;
            byte[] buf = new byte[charPos];
            buf[0] = '-';
            int r;
            long q;

            // Generate two digits per iteration
            while (i <= -100) {
                q = i / 100;
                r = (int) ((q * 100) - i);
                i = q;
                buf[--charPos] = DigitOnes[r];
                buf[--charPos] = DigitTens[r];
            }

            // We know there are at most two digits left at this point.
            q = i / 10;
            r = (int) ((q * 10) - i);
            buf[--charPos] = (byte) ('0' + r);

            // Whatever left is the remaining digit.
            if (q < 0) {
                buf[--charPos] = (byte) ('0' - q);
            }
            return buf;
        }

        public void write(String s) throws IOException {
            outputStream.write(s.getBytes());
        }

        public void write(byte[] s) throws IOException {
            outputStream.write(s);
        }

        public void write(char c) throws IOException {
            outputStream.write(c);
        }

        public void write(int i) throws IOException {
            outputStream.write(getBytes(i));
        }

        public void write(long l) throws IOException {
            outputStream.write(getBytes(l));
        }

        public void write(short s) throws IOException { //to be improved
            outputStream.write(getBytes((int) s));
        }

        public void write(double d) throws IOException { //to be improved
            write(String.valueOf(d));
        }

        public void write(float f) throws IOException { //to be improved
            write(String.valueOf(f));
        }

        public void flush() throws IOException {
            outputStream.flush();
        }
    }
}

