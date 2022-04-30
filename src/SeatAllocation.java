import java.io.*;

public class SeatAllocation {
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
    } ;

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
    } ;

    public static void main(String[] args) throws IOException {
        int parties;
        DataInputStream din = new DataInputStream(System.in);
        int ret = 0;
        byte c = (byte)din.read();
        while (c <= ' ')
            c = (byte)din.read();
        do
        {
            ret = ret * 10 + c - '0';
        }  while ((c = (byte)din.read()) >= '0' && c <= '9');

        Reader reader = new Reader(parties = ret, din);
        int i = 0, seats;
        ModifiedMaxPQ<Vote> maxOrderedTopParties;
        Vote[] votes = new Vote[parties];
        seats = reader.nextInt();
        if ((seats = reader.nextInt()) < parties) {
            MinPQ<Vote> minOrderedTopParties = new MinPQ<>(seats + 1);
            while (i < seats) {
                minOrderedTopParties.insert(votes[i++] = new Vote(reader.nextInt()));
            }
            while (i < parties) {
                minOrderedTopParties.insert(votes[i++] = new Vote(reader.nextInt()));
                minOrderedTopParties.delMin();
            }
            maxOrderedTopParties = new ModifiedMaxPQ<>(minOrderedTopParties.getPQ());
        } else {
            while (i<parties){
                votes[i++] = new Vote(reader.nextInt());
            }
            //Vote[] votesCopy = new Vote[parties+1];
            //System.arraycopy(votes, 0, votesCopy, 1, votes.length);
            maxOrderedTopParties = new ModifiedMaxPQ<>(votes);
        }
        while(seats-->0){
            maxOrderedTopParties.max().wins();
            maxOrderedTopParties.sink(1);
        }
        BufferedOutputStream out = new BufferedOutputStream(System.out);
        i = 0;
        while (i < parties) {
            out.write(getBytes(votes[i++].seats));
            out.write('\n');
        }
        out.flush();
    }

    static byte[] getBytes(int i) { // copied from Java Integer class
        int x = -i, charPos=0, d = 0, p = -10;
        for (int j = 1; j < 10; j++) {
            if (x > p){
                charPos = j + d;
                break;
            }
            p = 10 * p;
        }
        if(charPos == 0)
            charPos = 10 + d;
        byte[] buf = new byte[charPos];
        int q, r;
        i = -i;

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
        buf[--charPos] = (byte)('0' + r);

        // Whatever left is the remaining digit.
        if (q < 0) {
            buf[--charPos] = (byte)('0' - q);
        };
        return buf;
    }

    static class Vote implements Comparable<Vote>{
        private final int votes;
        public short seats = 0;
        private double frac;
        public Vote(int votes){
            this.votes = votes;
            calcFrac();
        }
        public void wins(){
            seats++;
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
            BUFFER_SIZE = s*11;
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

