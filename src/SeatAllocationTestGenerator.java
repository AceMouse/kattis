import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class SeatAllocationTestGenerator {
    public static void main(String[] args) throws IOException {
        Random random = new Random();
        int bound = 100-1;
        for (int t = 0; t < 100; t++) {
            Writer in = new BufferedWriter(new FileWriter(t+10 + "in.txt"));
            Writer out = new BufferedWriter(new FileWriter(t+10 + "out.txt"));

            int parties = random.nextInt(bound)+1;

            //Writer writer = new BufferedWriter(new PrintWriter(System.out));
            int i = 0, seats = random.nextInt(bound)+1;
            TestMaxPQ maxOrderedTopParties;
            SeatAllocation.Vote[] votes = new SeatAllocation.Vote[parties];
            StringBuilder instring = new StringBuilder(parties + " " + seats + "\n");
            /*if (seats < parties) {
                SeatAllocation.Vote[] initialOrder = new SeatAllocation.Vote[seats + 1];

                while (i < seats) {
                    int j = random.nextInt(50000000)+1;
                    instring.append(j).append('\n');
                    votes[i++] = initialOrder[i] = new SeatAllocation.Vote(j);
                }
                MinPQ<SeatAllocation.Vote> minOrderedTopParties = new MinPQ<>(initialOrder);
                while (i < parties) {
                    int j = random.nextInt(50000000)+1;
                    instring.append(j).append('\n');
                    minOrderedTopParties.insert(votes[i++] = new SeatAllocation.Vote(j));
                    minOrderedTopParties.delMin();
                }
                maxOrderedTopParties = new MaxPQ<>(minOrderedTopParties.getPQ(), true);
            *///} else {
                while (i<parties){
                    int j = random.nextInt(50000000)+1;
                    instring.append(j).append('\n');
                    votes[i++] = new SeatAllocation.Vote(j);
                }
                //Vote[] votesCopy = new Vote[parties+1];
                //System.arraycopy(votes, 0, votesCopy, 1, votes.length);
                maxOrderedTopParties = new TestMaxPQ<>(votes);
            //}
            while(seats-->0){
                SeatAllocation.Vote max = (SeatAllocation.Vote) maxOrderedTopParties.max();
                max.seats++;
                max.calcFrac();
                maxOrderedTopParties.sink(1);
            }
            StringBuilder outstring = new StringBuilder();
            i = 0;
            while (i < parties) {
                outstring.append(votes[i++].seats).append('\n');

            }
            out.write(new String(outstring));
            out.flush();
            in.write(new String(instring));
            in.flush();
        }
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
        final private int BUFFER_SIZE;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader(int s, DataInputStream din) throws IOException {
            BUFFER_SIZE = s*20;
            this.din = din;
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
            fillBuffer();
        }

        /*public Reader(String file_name) throws IOException
        {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }*/

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
            //boolean neg = (c == '-');
            //if (neg)
            //    c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            //if (neg)
            //    return -ret;
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
