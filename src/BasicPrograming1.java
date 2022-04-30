import java.io.*;
import java.util.*;
public class BasicPrograming1
{
    public static void main(String[] args) throws IOException {
        Reader sc = new Reader();
        BufferedOutputStream writer;
        int N = sc.nextInt(), a, b, c;
        long sum = 0;
        switch (sc.nextInt()) {
            case 1:
                System.out.println(7);
                break;
            case 2:
                int a0;
                int a1;
                if ((a0 = sc.nextInt()) > (a1 = sc.nextInt()))
                    System.out.println("Bigger");
                else if (a0 == a1)
                    System.out.println("Equal");
                else
                    System.out.println("Smaller");
                break;
            case 3:
                a = sc.nextInt(); b = sc.nextInt(); c = sc.nextInt();
                if (a < b) {
                    if (a < c){
                        System.out.println(Math.min(b, c));
                    } else System.out.println(a);
                } else if (c < b)
                    System.out.println(b);
                else System.out.println(Math.min(a, c));
                /*
                int[] arr = {sc.nextInt(), sc.nextInt(), sc.nextInt()};
                Arrays.sort(arr);
                System.out.println(arr[1]);*/
                break;
            case 4:
                while (N-- > 0) {
                    sum += sc.nextInt();
                }
                System.out.println(sum);
                break;
            case 5:
                while (N-- > 0) {
                    if (((a = sc.nextInt()) & 1) == 0)
                        sum += a;
                }
                System.out.println(sum);
                break;
            case 6:
                writer = new BufferedOutputStream(System.out);
                byte[] out = new byte[N];
                for (int i = 0; i<N; i++) {
                    out[i] = (byte)((sc.nextInt() % 26) + 'a');
                }
                writer.write(out);
                writer.flush();
                break;
            case 7:
                //writer = new PrintWriter(System.out);
                int[] A = new int[N];
                int bound = --N;
                for (int i = 0; i < N;i++) {
                    A[i] = sc.nextInt();
                }
                int i = 0;
                boolean[] is = new boolean[200000];
                while (!is[i = A[i]]) {
                    if (i >= bound) {
                        System.out.println(i == bound?"Done":"Out");
                        return;
                    }
                    is[i] = true;
                }
                System.out.println("Cyclic");
        }
    }
    static class Reader //copied from some website :D
    {
        final private int BUFFER_SIZE = 1 << 17;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader()
        {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public int nextInt() throws IOException
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