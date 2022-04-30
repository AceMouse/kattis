
import java.util.*;
import java.io.*;

public class Mravojed
{
    
    public static void main(String[] args) throws IOException
    {
        Writer w = new PrintWriter(System.out);
        FastReader reader = new FastReader();
        int R = reader.nextInt();
        int C = reader.nextInt();
        char[][] grid = new char[R][C];
        boolean[][] marked = new boolean[R][C];
        int[] rowLengths = new int[R];
        int[] columnLengths = new int[C];
        int firstr = -1;
        int firstc = -1;
        
        for (int r = 0; r<R; r++){
            grid[r] = reader.nextLine().toCharArray();
            for (int c = 0;c < C; c++){
                if (grid[r][c] == 'x'){
                    if (firstr == -1) {
                        firstr = r;
                        firstc = c;
                    } 
                    rowLengths[r]++;
                    columnLengths[c]++;
                }
            }
        }
        StringBuilder out = new StringBuilder(String.valueOf(firstr+1)).append(' ').append(String.valueOf(firstc+1)).append(' ').append(String.valueOf(Math.min(rowLengths[firstr], columnLengths[firstc])));
        
        for (int r = firstr; r< firstr+Math.min(rowLengths[firstr], columnLengths[firstc]); r++){
            for (int c = firstc;c < firstc + Math.min(rowLengths[firstr], columnLengths[firstc]); c++){
                grid[r][c] = ',';
            }
        }
        firstr = -1;
        firstc = -1;
        for (int r = 0; r<R; r++){
            for (int c = 0;c < C; c++){
                if (grid[r][c] == 'x'){
                    firstr = r;
                    firstc = c;
                }
            }
        }
        int x = firstr+1-Math.min(rowLengths[firstr], columnLengths[firstc]);
        int y = firstc+1-Math.min(rowLengths[firstr], columnLengths[firstc]);
        if (x >= 0){
            if (grid[x][y] == '.') x = x + Math.min(rowLengths[firstr], columnLengths[firstc])-1;
            
        } else 
        {
            x = x + Math.min(rowLengths[firstr], columnLengths[firstc])-1;
        }
        
        if (firstr == -1) out.append('\n').append(new String(out));
        else out.append('\n').append(String.valueOf(x+1)).append(' ').append(String.valueOf(y+1)).append(' ').append(String.valueOf(Math.min(rowLengths[firstr], columnLengths[firstc])));
        w.write(new String(out));
        w.flush();
    }
    /*static class Reader 
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
    }*/
    static class FastReader 
    { 
        BufferedReader br; 
        StringTokenizer st; 
  
        public FastReader() 
        { 
            br = new BufferedReader(new
                     InputStreamReader(System.in)); 
        } 
  
        String next() 
        { 
            while (st == null || !st.hasMoreElements()) 
            { 
                try
                { 
                    st = new StringTokenizer(br.readLine()); 
                } 
                catch (IOException  e) 
                { 
                    e.printStackTrace(); 
                } 
            } 
            return st.nextToken(); 
        } 
  
        int nextInt() 
        { 
            return Integer.parseInt(next()); 
        } 
  
        long nextLong() 
        { 
            return Long.parseLong(next()); 
        } 
  
        double nextDouble() 
        { 
            return Double.parseDouble(next()); 
        } 
  
        String nextLine() 
        { 
            String str = ""; 
            try
            { 
                str = br.readLine(); 
            } 
            catch (IOException e) 
            { 
                e.printStackTrace(); 
            } 
            return str; 
        } 
    } 
  
}
