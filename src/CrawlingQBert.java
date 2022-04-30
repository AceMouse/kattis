import java.io.*;

public class CrawlingQBert {
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        int pyramidHeight = reader.nextInt();
        int[] currentBottomLayerCosts = new int[pyramidHeight+1];//pyramidHeight+1 = platforms on bottom layer.
        for (int platformsInLayer = 1; platformsInLayer <= pyramidHeight; platformsInLayer++){
            int upperLeftPlatformCost = 100000, upperRightPlatformCost = currentBottomLayerCosts[0];
            int leftWeight = 1000000, rightWeight = 0;
            for (int currentPlatform = 0; currentPlatform < platformsInLayer; currentPlatform++) {
                rightWeight = reader.nextInt();
                currentBottomLayerCosts[currentPlatform] = Math.min(upperLeftPlatformCost+leftWeight, upperRightPlatformCost+rightWeight);
                leftWeight = reader.nextInt();
                upperLeftPlatformCost = upperRightPlatformCost;
                upperRightPlatformCost = currentBottomLayerCosts[currentPlatform+1];
            }
            currentBottomLayerCosts[platformsInLayer] = upperLeftPlatformCost + leftWeight;
        }
        int minDamage = Integer.MAX_VALUE;
        for (int i = 0; i < currentBottomLayerCosts.length; i++) {
            minDamage = Math.min(minDamage, currentBottomLayerCosts[i]);
        }
        System.out.println(minDamage);
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
            BUFFER_SIZE = 2500000;
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

        public int nextInt()
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
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
        private boolean isEOF() throws IOException {
            byte c, cnt = 0;
            while((c = read()) != 26 && cnt++<5);
            bufferPointer-=cnt;
            return c == 26;
        }
        private boolean hasNext() throws IOException {
            if (bufferPointer == bytesRead) fillBuffer();
            return buffer[bufferPointer] != -1;
        }
        private void fillBuffer() throws IOException
        {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read()
        {
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