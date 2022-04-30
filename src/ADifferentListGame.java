
import java.util.*;
import java.io.*;
public class ADifferentListGame{
    // for (long i = 1; i < X && 1<X; i++){
            // if (X%(i+1) == 0 && !used.contains(i+1)) {
                // X /= i+1;
                // used.add(i+1);
                // i = 1;
            // }
            // else if (i > Math.sqrt(X)+1) {
                // used.add(X);
                // break;
            // }
        // }
        // w.write(String.valueOf(used.size()));
        
    public static void main(String[] args) throws IOException{
        //long startTime = System.nanoTime();
        Reader r = new Reader();
        Writer w = new PrintWriter(System.out);
        
        // int max = 0;
        
        long X = r.nextLong();
        // long XCopy = X;
        // HashSet<Long> used = new HashSet<>(20);  
        // ArrayList<Long> usedList = new ArrayList<>(20);
        // for (long i = 1; i < X && 1<X; i++){
            // if (X%(i+1) == 0 && !used.contains(i+1)) {
                // X /= i+1;
                // used.add(i+1);
                // usedList.add(i+1);
                // i = 1;
            // }
            // else if (i > (long)Math.sqrt(X)+1) {
                // if (!used.contains(X)) {
                    // used.add(X);
                    // usedList.add(X);
                    // max = Math.max(max, used.size());
                    
                // } else {
                    // max = new Rec().rec(XCopy,new HashSet<Long>(20), 1, 0);
                    // // Long deadendFactor = usedList.get(usedList.size()-1);
                    // // used.remove(deadendFactor);
                    // // usedList.remove(deadendFactor);
                    // // X*=deadendFactor;
                    // // i = deadendFactor +1;
                // }
                // break;
            // }
        // }
        // w.write(String.valueOf(max));
        
        //w.write(String.valueOf(new Rec().rec(X, used, 1, 0)));
        w.write(String.valueOf(new Rec().findK(X)));
        w.flush();
        // long endTime = System.nanoTime();
        // long duration = (endTime-startTime)/1000000;
        // w.write(" " + String.valueOf(duration) + " ms");
        // w.flush();
        
    }
    
     
    static class Rec{
        private HashMap<String, Integer> combinations = new HashMap<>();
        private HashSet<Long> factors;
        private Long[] sortedFactors;
        private long target;
        Writer w = new PrintWriter(System.out);
        public int findK(long n) throws IOException{
            target = n;
            factors = new HashSet<>(20);
            factors.add(n);
            findFactors(n);
            
            sortedFactors = factors.toArray(new Long[factors.size()]);
            Arrays.sort(sortedFactors);
            // w.write(Arrays.toString(sortedFactors));
            // w.flush();
            return findLongestCombinationOfFactors();
        }
        
        private void findFactors(long n){
            long sqrt = (long)Math.sqrt(n)+1;
            for (long i = 2; i <= sqrt; i++){
                if (n%i == 0){
                    if (!factors.contains(i)) {
                        factors.add(i);
                        findFactors(i);
                    }
                    if (!factors.contains(n/i) && (n/i)>1) {
                        factors.add(n/i);
                        findFactors(n/i);
                    }
                } 
            }
        }
        
        private int findLongestCombinationOfFactors() throws IOException{
            return findLongestCombinationOfFactors(1,0,new boolean[sortedFactors.length]);
        }
        
        private int findLongestCombinationOfFactors(long product, long sum, boolean[] used) throws IOException{
            String key = new String(new StringBuilder(String.valueOf(product)).append(' ').append(String.valueOf(sum)).append(' ').append(String.valueOf(used.length)));
            if (combinations.get(key) != null) return combinations.get(key);
            
            if (product > target) return -1000;
            
             
            if (product == target) {
                // StringBuilder s = new StringBuilder('[');
                // for (int i = 0; i<used.length-1; i++){
                    // if (used[i]) s.append(sortedFactors[i]).append(',').append(' ');                    
                // }
                // if (used[used.length-1]) s.append(sortedFactors[used.length-1]);
                // s.append(']').append('\n');
                // w.write(new String(s));                
                // w.flush(); 
                return 0;
            }
            
            int max = -999;
            
            for (int i=0; i<sortedFactors.length;i++){
                if (!used[i]) {
                    boolean[] newUsed = new boolean[used.length];
                    System.arraycopy(used, 0, newUsed, 0, used.length);
                    newUsed[i] = true;
                    
                    // if (product*sortedFactors[i] > target) return -999;
            
             
                    // if (product*sortedFactors[i] == target) {
                        // StringBuilder s = new StringBuilder('[');
                        // for (int j = 0; j<newUsed.length-1; j++){
                            // if (newUsed[j]) s.append(sortedFactors[j]).append(',').append(' ');                    
                        // }
                        // if (used[newUsed.length-1]) s.append(sortedFactors[newUsed.length-1]);
                        // s.append(']').append('\n');
                        // w.write(new String(s));                
                        // w.flush(); 
                        // return 1;
                    // }
                    
                    int combinationLength = findLongestCombinationOfFactors(product*sortedFactors[i], product+sortedFactors[i], newUsed);
                    max = Math.max(max, combinationLength);
                    if (combinationLength == -1000) break;
                }
            }
            combinations.put(key, max+1);
            return max + 1;
        }
        
        public int rec(long X, HashSet<Long> used, long product, long sum){
            
            String usedStr = new String(new StringBuilder(String.valueOf(product)).append(' ').append(String.valueOf(sum)).append(' ').append(String.valueOf(used.size())));
            
            if (combinations.get(usedStr) != null) return combinations.get(usedStr);
            
            HashSet<Long> factors = new HashSet<>();
            long sqrt = (long)Math.sqrt(X)+1;
            for (long i = 2; i < sqrt; i++){
                if (X%i == 0){
                    if (!used.contains(i)) factors.add(i);
                    if (!used.contains(X/i)) factors.add(X/i);
                } 
            }
            
            int max = 0;
            for (Long factor:factors){
                int temp = rec(X/factor, used, product*factor, sum+factor, factor);
                if (max < temp) max = temp; 
            }
            if (used.contains(X)) return -1000;
            //if (max == 0) System.out.println(used.toString());
            combinations.put(usedStr, max+1);
            return max+1;
            
        } 
        
        public int rec(long X, HashSet<Long> used, long product, long sum, long factor){
            HashSet<Long> newUsed = new HashSet<>(20);
            newUsed.addAll(used);
            newUsed.add(factor);
            return rec(X, newUsed, product, sum);
        }
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
    }
    
    
}



