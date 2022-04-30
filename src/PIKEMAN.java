import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class PIKEMAN {
    public static void main(String[] args) throws IOException {
        /*boolean test = true;
        boolean easy = false;
        if (test) {
            System.out.println("testing...");
            Random rand = new Random();
            while (true){
                int N, a, b, c, t;
                long T;
                if (easy){
                    N = rand.nextInt((int) Math.pow(10, 4)) + 1;
                    T = rand.nextLong((long) Math.pow(10, 9)) + 1;
                } else {
                    N = rand.nextInt((int) Math.pow(10, 9)) + 1;
                    T = rand.nextLong((long) Math.pow(10, 18)) + 1;
                }
                a = rand.nextInt((int) Math.pow(10, 6)) + 1;
                b = rand.nextInt((int) Math.pow(10, 6)) + 1;
                c = rand.nextInt((int) Math.pow(10, 6)) + 1;
                t = rand.nextInt(c) + 1;

                int[] count1 = genCountSlow(N, a, b, c, t);
                int[] count2 = genCountFast(N, a, b, c, t);
                if (!Arrays.equals(count1, count2)) {
                    System.out.println("------------------------------------");
                    System.out.println("count diff");
                    System.out.println(N + " " + a + " " + b + " " + c + " " + t);
                    System.out.println(count1.length + " " + count2.length);
                    for (int j = 0; j < count1.length; j++) {
                        if (count1[j] == count2[j])
                            continue;
                        System.out.print(j);
                        System.out.print("\t\t\t");
                        System.out.print(count1[j]);
                        System.out.print("\t\t\t");
                        System.out.println(count2[j]);
                    }

                    return;
                }

                int problemsSolved1 = genProblemsSolved(count1, N, T);
                int problemsSolved2 = genProblemsSolved(count2, N, T);
                if (problemsSolved1 != problemsSolved2) {
                    System.out.println("------------------------------------");
                    System.out.println("problem solved diff");
                    System.out.println(N + " " + a + " " + b + " " + c + " " + t);
                    System.out.println(problemsSolved1);
                    System.out.println(problemsSolved2);
                    return;
                }
                long penaltyTime1 = genPenaltyTime(count1, problemsSolved1);
                long penaltyTime2 = genPenaltyTime(count2, problemsSolved2);
                if (penaltyTime1 != penaltyTime2) {
                    System.out.println("------------------------------------");
                    System.out.println("penalty time diff");
                    System.out.println(N + " " + a + " " + b + " " + c + " " + t);
                    System.out.println(penaltyTime1);
                    System.out.println(penaltyTime2);
                    return;
                }
            }
        } else {*/
            Reader reader = new Reader();
            Writer writer = new Writer();
            int N = reader.nextUInt();
            long T = reader.nextULong();
            int a = reader.nextUInt();
            int b = reader.nextUInt();
            int c = reader.nextUInt();
            int t = reader.nextUInt();
            int[] count = genCountSlow(N,a,b,c,t);
            int problemsSolved = genProblemsSolved(count, N, T);
            long penaltyTime = genPenaltyTime(count, problemsSolved);
            writer.write(problemsSolved);
            writer.write(' ');
            writer.write(penaltyTime);
            writer.flush();

        //}

    }

    private static int genProblemsSolved(int[] count, int n, long T) {
        long totalTime = 0;
        int solved = 0;
        for (long time = 0; time < count.length; time++) {
            long canSolveTimeWise, canSolveProblemWise;
            if (totalTime + count[(int)time]*time <= T){
                canSolveTimeWise = count[(int)time];
            } else {
                long timeLeft = T-totalTime;
                canSolveTimeWise = timeLeft / time;
            }
            if (solved + count[(int)time] <= n){
                canSolveProblemWise = count[(int)time];
            } else {
                int problemsLeft = n-solved;
                canSolveProblemWise = problemsLeft;
            }
            int canSolve = (int)Math.min(canSolveProblemWise,canSolveTimeWise);
            solved += canSolve;
            totalTime += canSolve*time;
        }
        return solved;
    }
    private static long genPenaltyTime(int[] count, int problemsSolved) {
        long totalTime = 0;
        long penaltyTime = 0;
        int solved = 0;
        for (long time = 0; time < count.length; time++) {
            long canSolve;
            if (solved + count[(int)time] <= problemsSolved){
                canSolve = count[(int)time];
            } else {
                int problemsLeft = problemsSolved-solved;
                canSolve = problemsLeft;
            }
            solved += canSolve;
            penaltyTime += totalTime*canSolve%1000000007;
            penaltyTime += ((canSolve+1)*canSolve/2 * time)%1000000007;
            totalTime += canSolve*time;
        }
        return penaltyTime%1000000007;
    }

    private static int[] genCountSlow(int n, long a, int b, int c, int t) {
        int[] counts = new int[c+1];
        for (int i = 0; i < n; i++) {
            counts[t]++;
            t = (int) (((a*t+b)%c)+1);
        }
        return counts;
    }
    private static int[] genCountFast(int n, long a, int b, int c, int t){
        int[] counts = new int[c+1];
        long[] encounters = new long[c+1];
        if (n == 0)
            return counts;
        encounters[t] = 0;
        counts[t] = 1;
        long cnt = 1;
        boolean circle = false;
        while (cnt < n) {
            if (counts[t = (int) ((a*t+b)%c) +1] == 1){
                circle = true;
                break;
            }
            encounters[t] = cnt++;
            counts[t]++;
        }
        if (circle) {
            long circleLength = (cnt ) - encounters[t];
            //System.out.println(circleLength);
            long wholeadd = (n - (cnt )) / circleLength;
            long leftadd = (n - (cnt )) % circleLength;

            for (long i = 0; i < leftadd; i++) {
                counts[t] += wholeadd +1;
                t = (int) ((a * t + b) % c) + 1;
            }
            for (long i = leftadd; i < circleLength; i++) {
                counts[t] += wholeadd ;
                t = (int) ((a * t + b) % c) + 1;
            }
        }

        return counts;
    }

    static class Reader {
        final private int BUFFER_SIZE = 1 << 16, LINE_LENGTH = 80;
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

