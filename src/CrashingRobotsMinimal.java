
import java.util.*;
import java.io.*;
public class CrashingRobotsMinimal{
    
    public static void main(String[] args) throws IOException{
        Reader r = new Reader();
        Writer w = new PrintWriter(System.out);
        int tests = r.nextInt();
        StringBuilder out = new StringBuilder();
        
        HashMap<Byte, Short> dirs = new HashMap<>();
        dirs.put((byte)'N', (short) 0);
        dirs.put((byte)'E', (short) 1);
        dirs.put((byte)'S', (short) 2);
        dirs.put((byte)'W', (short) 3);
        short[] newx;
        short[] newy;
        while(tests-- > 0){
            short A = r.nextShort();
            short B = r.nextShort();
            short robotAmount = (short) (r.nextShort()+1);
            short instructionAmount = r.nextShort();
            
            short[] x = new short[robotAmount];
            short[] y = new short[robotAmount];
            short[] dir = new short[robotAmount];
            
            for (int i = 1; i<robotAmount; i++){
                x[i] = r.nextShort();
                y[i] = r.nextShort();
                dir[i] = dirs.get(r.read());
            }
            
            boolean crash = false;
            while(instructionAmount-- > 0){
                short robot = r.nextShort();
                byte instruction = r.read();
                short repetitions = r.nextShort();
                
                if (crash) continue;
                
                if (instruction != (byte)'F') {
                    if (instruction == (byte)'R') 
                        dir[robot] = (short) ((dir[robot] + repetitions) % 4);
                    else                    
                        dir[robot] = (short) ((dir[robot] + repetitions*3) % 4);
                    continue;
                }
                /*
                int[] newx = new int[]{x[robot], x[robot] + repetitions, x[robot], x[robot] - repetitions};
                int[] newy = new int[]{y[robot] + repetitions, y[robot], y[robot] - repetitions, y[robot]};
                */
                short robotX, robotY;
                newx = new short[]{(robotX = x[robot]), (short) (robotX + repetitions), robotX, (short) (robotX - repetitions)};
                newy = new short[]{(short) ((robotY = y[robot]) + repetitions), robotY, (short) (robotY - repetitions), robotY};
                short dist = 10201;
                short robot2 = 0;
                /*
                for (short i = 1; i < robotAmount && dist > 1; i++){
                    if ((x[i] == newx[dir[robot]] && y[i] == newy[dir[robot]])
                     || (x[i] == newx[dir[robot]] && ((Math.min(y[robot], newy[dir[robot]]) < y[i] && y[i] < Math.max(y[robot], newy[dir[robot]]))) && Math.abs(y[robot] - y[i]) < dist )
                     || ((y[i] == newy[dir[robot]] && (Math.min(x[robot], newx[dir[robot]]) < x[i] && x[i] < Math.max(x[robot], newx[dir[robot]]))) && Math.abs(x[robot] - x[i]) < dist )){
                        dist = (short) (Math.abs(y[robot] - y[i]) + Math.abs(x[robot] - x[i]));
                        robot2 = i;
                        crash = true;
                    }
                }*/
                boolean destinationXOccupied, destinationYOccupied;
                short iX, iY,robotNewX = newx[dir[robot]], robotNewY = newy[dir[robot]], distX, distY;
                for (short i = 1; i < robotAmount /*&& dist > 1*/; i++){
                    destinationXOccupied = (iX = x[i]) == robotNewX;
                    destinationYOccupied = (iY = y[i]) == robotNewY;
                    distX = (short) Math.abs(robotX - iX);
                    distY = (short) Math.abs(robotY - iY);
                    if ((destinationXOccupied && destinationYOccupied)
                        || (destinationXOccupied && Math.min(robotY, robotNewY) < iY && iY < Math.max(robotY, robotNewY) && distY < dist )
                        || (destinationYOccupied && Math.min(robotX, robotNewX) < iX && iX < Math.max(robotX, robotNewX) && distX < dist )){
                        dist = (short) (distY + distX);
                        robot2 = i;
                        crash = true;
                    }
                }
                
                if (crash) {
                    out.append("Robot ").append(robot).append(" crashes into robot ").append(robot2).append("\n");
                }
                else if (robotNewX <= 0 || robotNewY <= 0 || robotNewX > A || robotNewY > B){
                    out.append("Robot ").append(robot).append(" crashes into the wall\n");
                    crash = true;
                }
                x[robot] = robotNewX;
                y[robot] = robotNewY;
            }
            if (!crash) out.append("OK\n");
        }
        w.write(new String(out));
        w.flush();
    }
    
    static class Reader 
    { 
        final private int BUFFER_SIZE = 1 << 6;
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

        public short nextShort() throws IOException
        {
            short ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            //boolean neg = (c == '-');
            //if (neg)
            //    c = read();
            do
            {
                ret = (short) (ret * 10 + c - '0');
            }  while ((c = read()) >= '0' && c <= '9');

            //if (neg)
            //    return (short) -ret;
            return ret;
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

