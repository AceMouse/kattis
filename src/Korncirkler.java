import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;


public class Korncirkler {
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        Writer writer = new Writer();
        int width = reader.nextInt()+2;
        int height = reader.nextInt()+2;
        byte[][] field = new byte[height][];
        // save the input in field patted by a layer of bytes not equal to '#'
        // in this case 0, to avoid bounds checking later
        field[0] = new byte[width];
        for (int i = 1; i < field.length-1; i++) {
            // reader.readLine(n) reads a line and returns it as a byte[]
            // with n extra 0's appended in both ends
            field[i] = reader.readLine(1);
        }
        field[field.length-1] = new byte[width];
        writer.writeChars(field);
        writer.write('\n');
        // create map of how far from coordinate (i, j) to nearest byte that is not equal to '#',
        // 0 if field[i][j] is not equal to '#', in 4 passes
        int[][] center = new int[field.length][field[0].length];
        writer.write("pass from left to right\n".getBytes());
        int cnt = 0;
        for (int i = 1; i < field.length-1; i++) {
            for (int j = 1; j < field[0].length-1; j++) {
                if (field[i][j] == '#') {
                    cnt++;
                    center[i][j] = cnt;
                } else cnt = 0;
            }
        }

        writer.write(center);
        writer.write('\n');
        writer.write("pass from right to left\n".getBytes());
        cnt = 0;
        for (int i = field.length-2; i > 0; i--) {
            for (int j = field[0].length-2; j > 0; j--) {
                if (field[i][j] == '#') {
                    cnt++;
                    center[i][j] = Math.min(cnt, center[i][j]);
                } else cnt = 0;
            }
        }
        writer.write(center);
        writer.write('\n');
        writer.write("pass from top to bottom\n".getBytes());
        cnt = 0;
        for (int j = 1; j < field[0].length-1; j++) {
            for (int i = 1; i < field.length-1; i++) {
                if (field[i][j] == '#') {
                    cnt++;
                    center[i][j] = Math.min(cnt, center[i][j]);
                } else cnt = 0;
            }
        }
        writer.write(center);
        writer.write('\n');
        writer.write("pass from bottom to top\n".getBytes());
        cnt = 0;
        for (int j = field[0].length-2; j > 0; j--) {
            for (int i = field.length-2; i > 0; i--) {
                if (field[i][j] == '#') {
                    cnt++;
                    center[i][j] = Math.min(cnt, center[i][j]);
                } else cnt = 0;
            }
        }
        writer.write(center);
        writer.write('\n');
        for (int i = 1; i < center.length-1; i++) {
            for (int j = 1; j < center[0].length-1; j++) {
                if (center[i][j] > center[i-1][j] && center[i][j] > center[i+1][j] && center[i][j] > center[i][j+1] && center[i][j] > center[i][j-1]){
                    writer.write(j - 1);
                    writer.write(' ');
                    writer.write(i - 1);
                    writer.write('\n');
                }
            }
        }
        writer.flush();
    }

    static class Reader {
        final private int BUFFER_SIZE = 11000;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() throws IOException {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
            fillBuffer();
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
            while ((c = read()) != -1) {
                if (c == ' ' || c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public byte[] readLine(int padding) throws IOException {
            byte[] buf = new byte[103]; // line length
            int cnt = padding, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return Arrays.copyOf(buf, cnt+padding);
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
            i = -i;
            int charPos = 0, d = 0, p = -10;
            for (int j = 1; j < 10; j++) {
                if (i > p) {
                    charPos = j + d;
                    break;
                }
                p = 10 * p;
            }
            if (charPos == 0)
                charPos = 10 + d;
            byte[] buf = new byte[charPos];
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
            i = -i;
            int charPos = 0, d = 0, p = -10;
            for (int j = 1; j < 10; j++) {
                if (i > p) {
                    charPos = j + d;
                    break;
                }
                p = 10 * p;
            }
            if (charPos == 0)
                charPos = 10 + d;
            byte[] buf = new byte[charPos];
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

        public void write(int[][] s) throws IOException {
            for (int[] bytes : s) {
                for (int b : bytes) {
                    write(b);
                    write(' ');
                }
                write('\n');
            }
        }
        public void writeChars(byte[][] s) throws IOException {
            for (byte[] bytes : s) {
                for (byte b : bytes) {
                    write((char)(b==0?'0':b));
                    write(' ');
                }
                write('\n');
            }
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

