import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class FlightsRedBlackBST {
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
        Reader reader = new Reader();
        BufferedOutputStream out = new BufferedOutputStream(System.out);
        final byte[] dash = new byte[]{'-'};
        int n = reader.nextInt(), m = reader.nextInt();
        IntBytearrRedBlackBST departures = new IntBytearrRedBlackBST();
        while (n-- > 0) {
            departures.put(calcSeconds(reader.nextInt(),reader.nextInt(),reader.nextInt()), new byte[]{reader.read(), reader.read(), reader.read(), reader.read(), reader.read(), reader.read(), reader.read()});
        }
        IntBytearrRedBlackBST.Node node;
        int shr, smi, sse, s;
        reader.skip(1);
        while (m-- > 0){
            reader.skipToNextWord();
            reader.skip(2);
            switch (reader.read()){
                case 'n': //cancel s
                    reader.skip(3);
                    departures.delete(calcSeconds(reader.nextInt(),reader.nextInt(),reader.nextInt()));
                    break;
                case 'l': //delay s d
                    reader.skip(2);
                    byte[] temp = departures.get(s = calcSeconds(reader.nextInt(),reader.nextInt(),reader.nextInt()));
                    departures.delete(s);
                    departures.put(s+reader.nextInt(), temp);
                    break;
                case 'r': //reroute s c
                    reader.skip(4);
                    departures.put(calcSeconds(reader.nextInt(),reader.nextInt(),reader.nextInt()), new byte[]{reader.read(), reader.read(), reader.read(), reader.read(), reader.read(), reader.read(), reader.read()});
                    break;
                case 's': //destination s
                    reader.skip(8);
                    out.write(departures.contains(s = calcSeconds(reader.nextInt(),reader.nextInt(),reader.nextInt()))?departures.get(s):dash);
                    out.write('\n');
                    break;
                case 'x': //next s
                    reader.skip(1);
                    out.write((byte)('0'+((shr = (((((s = (node = departures.ceiling(calcSeconds(reader.nextInt(),reader.nextInt(),reader.nextInt()))).key) >> 2) /15) >> 2) /15) %24)/10)));
                    out.write((byte)('0'+(shr%10)));
                    out.write(':');
                    out.write((byte)('0'+((smi = (((s >> 2) /15)) %60)/10)));
                    out.write((byte)('0'+(smi%10)));
                    out.write(':');
                    out.write((byte)('0'+((sse = s%60)/10)));
                    out.write((byte)('0'+(sse%10)));
                    out.write(' ');
                    out.write(node.val);
                    out.write('\n');
                    break;
                case 'u': //count s t
                    reader.skip(2);
                    out.write(getBytes(departures.size(
                                    calcSeconds(reader.nextInt(),reader.nextInt(),reader.nextInt()),
                                    calcSeconds(reader.nextInt(),reader.nextInt(),reader.nextInt()))
                            )
                    );
                    out.write('\n');
                    break;
            }
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
    private static int calcSeconds(int hr, int mi, int se){
        return (((mi = ((hr<<6) - (hr<<2)) + mi)<<6) - (mi<<2)) + se;
    }

    static class Reader //copied from some website :D
    {
        final private int BUFFER_SIZE;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader(int s, DataInputStream din) throws IOException {
            BUFFER_SIZE = s;
            this.din = din;
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
            fillBuffer();
        }

        public Reader() throws IOException {
            BUFFER_SIZE = 1<<20;
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
            fillBuffer();
        }

        public Reader(String file_name) throws IOException
        {
            BUFFER_SIZE = 1<<20;
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
            while (c <= ' ' || c == ':')
                c = read();
            //boolean neg = (c == '-');
            //if (neg)
            //    c = read();
            do
            {
                ret = ret * 10 + c - '0';
            }  while ((c = read()) >= '0' && c <= '9');

            //if (neg)
            //    return -ret;
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
        private void skip(int x) {
            bufferPointer += x;
        }
        private void skipToNextWord() throws IOException {
            byte c, cnt = 0;
            while ((c = read()) != -1 && c < 'a' && cnt++ < 5);
            if (--bufferPointer < 0) bufferPointer++;
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
