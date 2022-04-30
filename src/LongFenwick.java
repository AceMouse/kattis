public class LongFenwick {
    private long[] prefixSums;
    private int size;
    public LongFenwick(long[] numbers){
        size = numbers.length+1;
        size |= size >> 1;
        size |= size >> 2;
        size |= size >> 4;
        size |= size >> 8;
        size |= size >> 16;
        size++;
        prefixSums = new long[size];
        for (int i = 0; i < numbers.length; i++) {
            add(i+1, numbers[i]);
        }
    }
    public LongFenwick(int sz){
        size = sz+1;
        size |= size >> 1;
        size |= size >> 2;
        size |= size >> 4;
        size |= size >> 8;
        size |= size >> 16;
        size++;
        prefixSums = new long[size];
    }
    public void add(int i, long x){
        while (i < size){
            prefixSums[i] += x;
            i += i&-i;
        }
    }
    public long get(int i){
        long sum = 0;
        while (i > 0){
            sum += prefixSums[i];
            i ^= i&-i;
        }
        return sum;
    }
    public void set(int i, long x){
        add(i, -getRange(i,i)+x);
    }
    public long getRange(int l, int r){
        return get(r)-get(l-1);
    }


}
