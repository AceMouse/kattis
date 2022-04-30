import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class DungeonMaster {
    private static int L, R, C;
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        Writer writer = new Writer();
        IntArrayQueue pos = new IntArrayQueue();
        IntArrayQueue min = new IntArrayQueue();
        int[] maze = new int[(32 * 32 * 32) + (32 * 32) + 32];
        while (true) {
            L = reader.nextUInt()+2;
            R = reader.nextUInt()+2;
            C = reader.nextUInt()+2;
            if (L == 2 && R == 2 && C == 2)
                break;

            int lo = toPos(1,0,0), ro = toPos(0,1,0), co = toPos(0,0,1);
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    maze[toPos(0,i,j)] = -'#';
                    maze[toPos(L-1,i,j)] = -'#';
                }
            }
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < L; j++) {
                    maze[toPos(j,i,0)] = -'#';
                    maze[toPos(j,i,C-1)] = -'#';
                }
            }
            for (int i = 0; i < L; i++) {
                for (int j = 0; j < C; j++) {
                    maze[toPos(i,0,j)] = -'#';
                    maze[toPos(i,R-1,j)] = -'#';
                }
            }
            int S = 0, E = 0;

            int sp;
            for (int l = 1; l < L - 1; l++) {
                for (int r = 1; r < R - 1; r++) {
                    for (int c = 1; c < C - 1; c++) {
                        maze[sp = l * lo + r * ro + c * co] = -reader.read();
                        if (maze[sp] == -'S') {
                            S = sp;
                            maze[sp] = -'.';
                        } else if (maze[sp] == -'E'){
                            E = sp;
                            maze[sp] = -'.';
                        }
                    }
                    reader.read();
                }
                reader.read();
            }
            int minutes = -1;
            pos.clear();
            min.clear();
            pos.enqueue(S);
            min.enqueue(0);
            while (!pos.isEmpty()){
                int p = pos.dequeue();
                int m = min.dequeue();
                if (p == E) {
                    minutes = m;
                    break;
                }
                if (maze[p] == -'.') {
                    pos.enqueue(p+lo);
                    min.enqueue(m+1);
                    pos.enqueue(p+ro);
                    min.enqueue(m+1);
                    pos.enqueue(p+co);
                    min.enqueue(m+1);
                    pos.enqueue(p-lo);
                    min.enqueue(m+1);
                    pos.enqueue(p-ro);
                    min.enqueue(m+1);
                    pos.enqueue(p-co);
                    min.enqueue(m+1);
                    maze[p] = 0;
                }
            }
            writer.write(minutes < 0? "Trapped!\n" : "Escaped in "+minutes+" minute(s).\n");
            writer.flush();
        }
    }
    private static int toPos(int l, int r, int c){
        return (c * L * R) + (r * L) + l;
    }
    static class Reader {
        final private int BUFFER_SIZE = 1 << 16, LINE_LENGTH = 1;
        private DataInputStream din;
        private byte[] buffer, auxBuf;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            auxBuf = new byte[LINE_LENGTH];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            auxBuf = new byte[LINE_LENGTH];
            bufferPointer = bytesRead = 0;
        }

        public boolean hasNext() throws IOException {
            if (bufferPointer == bytesRead) fillBuffer();
            return buffer[bufferPointer] != -1;
        }

        public String next() throws IOException {
            byte[] buf = auxBuf;
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
            byte[] buf = auxBuf;
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return Arrays.copyOf(buf, cnt);
        }

        public String nextLine() throws IOException {
            byte[] buf = auxBuf;
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

        public int nextUInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();

            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

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

        public long nextULong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();

            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

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

        public double nextUDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
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

