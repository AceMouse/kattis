import java.io.*;

public class Flights {
    private static byte[][] departures = new byte[24*60*60][7];

    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        BufferedOutputStream out = new BufferedOutputStream(System.out);
        int n = reader.nextInt(), m = reader.nextInt();
        while (n-- >0) {
            int s = (reader.nextInt()*60+reader.nextInt())*60+reader.nextInt();
            for (int j = 0; j < 7; j++) {
                departures[s][j] = reader.read();
            }
        }
        int shr, smi, sse, s, t, thr, tmi, tse;
        reader.skip(1);
        while (m-- > 0){
            reader.skipToNextWord();
            reader.skip(2);

            switch (reader.read()){
                case 'n': //cancel s
                    reader.skip(3);
                    shr = reader.nextInt();
                    smi = reader.nextInt();
                    sse = reader.nextInt();
                    departures[(shr*60+smi)*60+sse] = new byte[7];
                    break;
                case 'l': //delay s d
                    reader.skip(2);
                    shr = reader.nextInt();
                    smi = reader.nextInt();
                    sse = reader.nextInt();
                    byte[] temp = departures[s = (shr*60+smi)*60+sse];
                    departures[s] = new byte[7];
                    departures[s+reader.nextInt()] = temp;
                    break;
                case 'r': //reroute s c
                    reader.skip(4);
                    shr = reader.nextInt();
                    smi = reader.nextInt();
                    sse = reader.nextInt();
                    s = (shr*60+smi)*60+sse;
                    for (int j = 0; j < 7; j++) {
                        departures[s][j] = reader.read();
                    }
                    break;
                case 's': //destination s
                    reader.skip(8);
                    shr = reader.nextInt();
                    smi = reader.nextInt();
                    sse = reader.nextInt();
                    s = (shr*60+smi)*60+sse;
                    if (departures[s][0] != 0)
                        out.write(departures[s]);
                    else
                        out.write('-');
                    out.write('\n');
                    break;
                case 'x': //next s
                    reader.skip(1);
                    shr = reader.nextInt();
                    smi = reader.nextInt();
                    sse = reader.nextInt();
                    s = (shr*60+smi)*60+sse;
                    while (s < 24*60*60) {
                        if (departures[s++][0] != 0){
                            sse = --s%60;
                            smi = (s/60)%60;
                            shr = (s/60/60)%24;
                            out.write(new String(new StringBuilder().append(shr/10).append(shr%10).append(':').append(smi/10).append(smi%10).append(':').append(sse/10).append(sse%10).append(' ')).getBytes());
                            out.write(departures[s]);
                            out.write('\n');
                            break;
                        }
                    }
                    //reader.skipToNextWord();
                    break;
                case 'u': //count s t
                    reader.skip(2);
                    shr = reader.nextInt();
                    smi = reader.nextInt();
                    sse = reader.nextInt();
                    s = (shr*60+smi)*60+sse;

                    thr = reader.nextInt();
                    tmi = reader.nextInt();
                    tse = reader.nextInt();
                    t = (thr*60+tmi)*60+tse;

                    int cnt = 0;
                    while (s <= t) {
                        if (departures[s++][0] != 0)
                            cnt++;
                    }
                    out.write(String.valueOf(cnt).getBytes());
                    out.write('\n');
                    //reader.skipToNextWord();
                    break;
            }
        }
        out.flush();
    }
    static class Reader //copied from some website :D
    {
        final private int BUFFER_SIZE;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader(int s, DataInputStream din) throws IOException {
            BUFFER_SIZE = s*17;
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
            BUFFER_SIZE = 1<<16;
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
        private void skip(int x)
        {
            bufferPointer+=x;
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

