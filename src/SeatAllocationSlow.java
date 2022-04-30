import java.io.*;
import java.util.Arrays;
//import java.util.PriorityQueue;

public class SeatAllocationSlow {
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        short p = reader.nextShort(), seats = reader.nextShort();
        Party[] parties = new Party[p];
        Vote[] votes = new Vote[p];
        while (p-->0){
            parties[p] = new Party(votes[p] = new Vote(reader.nextLong()));
        }
        p = (short) (parties.length-1);
        Arrays.sort(votes);
        for (int i = 0; i<seats; i++) {
            votes[0].seats++;
            votes[0].calcFrac();
            for (int j = 0; j < p && votes[j].compareTo(votes[j+1]) > 0; ) {
                Vote temp = votes[j];
                votes[j] = votes[++j];
                votes[j] = temp;
            }
        }
        p++;
        Writer writer = new PrintWriter(System.out);
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < p; i++) {
            out.append(parties[i].vote.seats).append('\n');
        }
        writer.write(new String(out));
        writer.flush();
    }
    static class Party{
        public Vote vote;
        public Party(Vote vote){
            this.vote = vote;
        }
    }
    static class Vote implements Comparable<Vote>{
        private final long votes;
        public short seats;
        private double frac;
        public Vote(long votes){
            this.votes = votes;
            seats = 0;
            calcFrac();
        }
        public void calcFrac(){
            frac = votes/(double)(seats+1);
        }
        @Override
        public int compareTo(Vote that) {
            return this.frac > that.frac?1:-1;
        }
    }
    static class Reader //copied from some website :D
    {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() throws IOException {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
            fillBuffer();
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
        public short nextShort() throws IOException
        {
            short ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            do
            {
                ret = (short) (ret * 10 + c - '0');
            }  while ((c = read()) >= '0' && c <= '9');
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
