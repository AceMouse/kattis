import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;

public class MinkHeuristicGuided {
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        Writer writer = new Writer();
        int h= reader.nextInt();
        int b= reader.nextInt();
        int d= reader.nextInt();
        byte[][] area = new byte[h][];
        for (int i = 0; i < h; i++) {
            area[i] = reader.readLine();
        }
        int[][] minkNextDepth = new int[h][b];
        for (int i = 0; i < b; i++) {
            minkNextDepth[h-1][i] = 1000;
        }
        int mink = 0;
        int[] minkMaxDepth = new int[b];
        for (int i = h-2; i >= 0; i--) {
            for (int j = b-1; j >= 0; j--) {
                if (area[i+1][j] != '#') {
                    minkNextDepth[i][j] = 0;
                    minkMaxDepth[j] = Math.max(i+2, minkMaxDepth[j]);
                    mink++;
                } else {
                    minkNextDepth[i][j] = minkNextDepth[i + 1][j] + 1;
                }
            }
        }



        int sum = 0;
        for (int i = 0; i < minkMaxDepth.length; i++) {
            sum += minkMaxDepth[i];
        }
        sum-=mink;
        mink = (mink / 3);
        int[] dugCols = new int[b];
        if ( sum < mink *d )
            dugCols = minkMaxDepth;
        else
            dugCols = digBruteForce(d, area, dugCols, 0, minkNextDepth);

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < b; j++) {
                writer.write(i < dugCols[j]?' ':(char)area[i][j]);
            }
            writer.write('\n');
        }

        writer.flush();
    }

    /*private static byte[][] genArea(int h, int b, Random random) {
        byte[][] area = new byte[h][b];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < b; j++) {
                area[i][j] = '#';
            }
        }
        int mink = 0;
        for (int i = 1; i < h-1; i++) {
            for (int j = 1; j < b-3; j++) {
                if (area[i][j] == '#' && mink < 200 && random.nextInt(10) < 1) {
                    area[i][j] = '<';
                    area[i][++j] = '=';
                    area[i][++j] = '>';
                    mink++;
                }
            }
        }
        if (mink == 0){
            area[1][1] = '<';
            area[1][2] = '=';
            area[1][3] = '>';
        }
        return area;
    }*/

/*
4 8 3
########
#<=>####
#<=><=>#
########

 */
    private static HashSet<IntArr> mem = new HashSet<>();
    private static int[] digBruteForce(int d, byte[][] area, int[] dugCols, int curVal, int[][] minkNextDepth) {
        if (curVal > 0)
            return dugCols;
        IntArr key = new IntArr(dugCols);
        if (mem.contains(key))
            return null;
        int[] res;
        PriorityQueue<Pair> cols = new PriorityQueue<>();
        for (int i = 0; i < dugCols.length; i++) {
            if (minkNextDepth[dugCols[i]][i] < 1000)
                cols.add(new Pair(i, (byte) minkNextDepth[dugCols[i]][i]));
        }
        while (!cols.isEmpty()){
            int i = cols.remove().a;
            int[] newDugCols = Arrays.copyOf(dugCols, dugCols.length);
            //if (area[newDugCols[i]][i] != '#')
                //continue;
            int tilNextMink = digTilMink(area, newDugCols, i, d, minkNextDepth);
            if (tilNextMink == Integer.MIN_VALUE)
                continue;
            res = digBruteForce(d, area, newDugCols,curVal+tilNextMink, minkNextDepth);

            if  (res != null) {
                return res;
            }
        }
        mem.add(key);
        return null;
    }

    private static int digTilMink(byte[][] area, int[] dugCols, int i, int d, int[][] minkNextDepth) {
        int val = 0;
        if (area[dugCols[i]][i] == '#'){
            val--;
            dugCols[i]++;
        }
        else if ((area[dugCols[i]][i] == '>' && !(dugCols[i]<=dugCols[i-1] && dugCols[i]<=dugCols[i-2])) || (area[dugCols[i]][i] == '<' && !(dugCols[i]<=dugCols[i+1] && dugCols[i]<=dugCols[i+2]))||(area[dugCols[i]][i] == '=' && !(dugCols[i]<=dugCols[i+1] && dugCols[i]<=dugCols[i-1])))
            return Integer.MIN_VALUE;

        val-=minkNextDepth[dugCols[i]-1][i];
        dugCols[i]+=minkNextDepth[dugCols[i]-1][i];

        switch (area[dugCols[i]][i]){
            case '<':
                if (dugCols[i]<=dugCols[i+1] && dugCols[i]<=dugCols[i+2]) {
                    val += d;
                    dugCols[i]++;
                    dugCols[i+1]++;
                    dugCols[i+2]++;
                }
                break;
            case '>':
                if (dugCols[i]<=dugCols[i-1] && dugCols[i]<=dugCols[i-2]) {
                    val += d;
                    dugCols[i-2]++;
                    dugCols[i-1]++;
                    dugCols[i]++;
                }
                break;
            case '=':
                if (dugCols[i]<=dugCols[i+1] && dugCols[i]<=dugCols[i-1]) {
                    val += d;
                    dugCols[i-1]++;
                    dugCols[i]++;
                    dugCols[i+1]++;
                }
        }
        return val;
    }
    static class Pair implements Comparable<Pair>{
        final int a;
        final int b;
        public Pair(int a, int b){
            this.a = a;
            this.b = b;
        }

        @Override
        public int compareTo(Pair that) {
            return this.b - that.b;
        }
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

