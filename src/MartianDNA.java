import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class MartianDNA {
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        int minLength, N = reader.nextInt(), K = reader.nextInt(), R = reader.nextInt();
        int[] completeDNA = new int[minLength = N];
        int[] requirements = new int[K];
        int[] requirementsCopy = new int[K];
        boolean possible = false;
        for (int i = 0; i<N; i++){
            completeDNA[i] = reader.nextInt();
        }
        int totalRequirements = 0;
        for (int i = 0; i<R; i++){
            totalRequirements += (requirements[reader.nextInt()] = reader.nextInt());
        }

        for (int i = 0; i < N-totalRequirements; i++) {
            if(requirements[completeDNA[i]] == 0)
                continue;
            int totalRequirementsCopy = totalRequirements;
            System.arraycopy(requirements,0,requirementsCopy,0,K);
            for (int j = i; j < N && j<minLength+i; j++) {
                if(requirementsCopy[completeDNA[j]] > 0){
                    requirementsCopy[completeDNA[j]]--;

                    if(--totalRequirementsCopy == 0) {
                        possible = true;
                        minLength = Math.min(minLength, j-i+1);
                        break;
                    }
                }
            }
        }
        /*int start = 0, end = N;
        for (int i = 0; i < N-totalRequirements; i++) {
            int totalRequirementsCopy = totalRequirements;
            System.arraycopy(requirements,0,requirementsCopy,0,K);
            for (int j = i; j < N; j++) {
                if(requirementsCopy[completeDNA[j]] > 0){
                    requirementsCopy[completeDNA[j]]--;

                    if(--totalRequirementsCopy == 0) {
                        possible = true;
                        minLength = Math.min(minLength, j-i+1);
                        break;
                    }
                }
            }
        */
        if (!possible)
            System.out.println("impossible");
        else
            System.out.println(minLength);
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

        /*public ByteArrayList readLine() throws IOException
        {
            ByteArrayList buf = new ByteArrayList(1024); // line length
            byte c;
            while ((c = read()) != -1)
            {
                if (c == '\n')
                    break;
                buf.add(c);
            }
            return buf;
        }*/

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
