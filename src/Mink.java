import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;

public class Mink {
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        Writer writer = new Writer();
        int h = reader.nextInt();
        int b = reader.nextInt();
        int d = reader.nextInt();
        byte[][] area = new byte[h][];
        byte[] minkMaxDepths = new byte[b];
        for (int i = 0; i < h; i++) {
            area[i] = reader.readLine();
            for (int j = 0; j < b; j++) {
                if (area[i][j] != '#')
                    minkMaxDepths[j] =  (byte) i;
            }
        }

        byte[] dugCols = new byte[b];
        dugCols = digBruteForce(d, area, dugCols, 0, minkMaxDepths);

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < b; j++) {
                writer.write(i < dugCols[j]?' ':(char)area[i][j]);
            }
            writer.write('\n');
        }
        writer.flush();
    }
    private static HashSet<String> mem = new HashSet<>();
    private static byte[] digBruteForce(int d, byte[][] area, byte[] dugCols, int curVal, byte[] maxDepths) {
        if (curVal > 0)
            return dugCols;
        if (mem.contains(Base64.getEncoder().encodeToString(dugCols)))
            return null;
        byte[] res;
        for (int i = 0; i < dugCols.length; i++) {
            if (dugCols[i] >= maxDepths[i])
                continue;
            byte[] newDugCols = Arrays.copyOf(dugCols, dugCols.length);
            int tilNextMink = digTilMink(area, newDugCols, i, d);
            if (tilNextMink == Integer.MIN_VALUE)
                continue;
            res = digBruteForce(d, area, newDugCols,curVal+tilNextMink , maxDepths);
            if  (res != null)
                return res;
        }
        mem.add(Base64.getEncoder().encodeToString(dugCols));
        return null;
    }

    private static int digTilMink(byte[][] area, byte[] dugCols, int i, int d) {
        int val = 0;
        if (area[dugCols[i]][i] == '#'){
            val--;
            dugCols[i]++;
        }
        else if (area[dugCols[i]][i] == '>' && !(dugCols[i]<=dugCols[i-1] && dugCols[i]<=dugCols[i-2]))
            return Integer.MIN_VALUE;
        else if (area[dugCols[i]][i] == '<' && !(dugCols[i]<=dugCols[i+1] && dugCols[i]<=dugCols[i+2]))
            return Integer.MIN_VALUE;
        else if (area[dugCols[i]][i] == '=' && !(dugCols[i]<=dugCols[i+1] && dugCols[i]<=dugCols[i-1]))
            return Integer.MIN_VALUE;

        while (area[dugCols[i]++][i] == '#')
            val--;
        switch (area[dugCols[i]-1][i]){
            case '<':
                val += dugCols[i]<=dugCols[i+1] && dugCols[i]<=dugCols[i+2]? d : 0;
                break;
            case '>':
                val += dugCols[i]<=dugCols[i-1] && dugCols[i]<=dugCols[i-2]? d : 0;
                break;
            case '=':
                val += dugCols[i]<=dugCols[i+1] && dugCols[i]<=dugCols[i-1]? d : 0;
        }
        return val;
    }

    static class Reader {
        final private int BUFFER_SIZE = 200*200;
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
            byte[] buf = new byte[201]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return Arrays.copyOf(buf, cnt);
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

        public void write(boolean[][] matrix) throws IOException {
            for (boolean[] barr : matrix){
                for (boolean b : barr){
                    write(b);
                    write(' ');
                }
                write('\n');
            }
        }

        private void write(boolean b) throws IOException {
            write(b?'T':'F');
        }
    }
}

