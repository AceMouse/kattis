import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CatchThePlane {
    private static long deadline;
    private static ArrayList<Integer>[] indexes;
    private static int[] starts;
    private static int[] destinations;
    private static long[] departures;
    private static long[] arrivals;
    private static double[] probabilities;

    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        PrintWriter writer = new PrintWriter(System.out);

        int busAmount = reader.nextInt();
        int stopAmount = reader.nextInt();
        deadline = reader.nextLong();

        starts = new int[busAmount];
        destinations = new int[busAmount];
        departures = new long[busAmount];
        arrivals = new long[busAmount];
        probabilities = new double[busAmount];

        indexes = (ArrayList<Integer>[])new ArrayList[stopAmount];
        while (stopAmount-- > 0){
            indexes[stopAmount] = new ArrayList<>();
        }

        while (busAmount-- > 0) {
            starts[busAmount] = reader.nextInt();

            indexes[starts[busAmount]].add(busAmount);

            destinations[busAmount] = reader.nextInt();
            departures[busAmount] = reader.nextLong();
            arrivals[busAmount] = reader.nextLong();
            probabilities[busAmount] = reader.nextDouble();
        }

        writer.write(String.valueOf(probability(0,0,1)));
        writer.flush();

    }

    public static double probability(int start, long time, double probability) {
        if (time > deadline) return 0;
        if (start == 1) return probability;
        System.out.println("" + start + time + probability);
        double maxProbability = 0;
        for (int i = 0; i < indexes[start].size(); i++) {
            System.out.println(i+"\n");
            int index = indexes[start].get(i);
            if (departures[index] > time) {
                System.out.println(i);
                maxProbability = Math.max(maxProbability, probability(destinations[index], arrivals[index], probability * probabilities[index]));
            }
        }
        return maxProbability;
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
            byte[] buf = new byte[10]; // line length
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
