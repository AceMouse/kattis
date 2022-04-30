public class Fenwick {
    private int[] prefixSums;
    private int size;
    public Fenwick(int[] numbers){
        size = numbers.length+1;
        size |= size >> 1;
        size |= size >> 2;
        size |= size >> 4;
        size |= size >> 8;
        size |= size >> 16;
        size++;
        prefixSums = new int[size];
        for (int i = 0; i < numbers.length; i++) {
            add(i+1, numbers[i]);
        }
    }
    public Fenwick(int sz){
        size = sz+1;
        size |= size >> 1;
        size |= size >> 2;
        size |= size >> 4;
        size |= size >> 8;
        size |= size >> 16;
        size++;
        prefixSums = new int[size];
    }
    public void add(int i, int x){
        while (i < size){
            prefixSums[i] += x;
            i += i&-i;
        }
    }
    public int get(int i){
        int sum = 0;
        while (i > 0){
            sum += prefixSums[i];
            i ^= i&-i;
        }
        return sum;
    }
    public void set(int i, int x){
        add(i, -getRange(i,i)+x);
    }
    public int getRange(int l, int r){
        return get(r)-get(l-1);
    }
}
