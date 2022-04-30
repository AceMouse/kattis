import java.util.Arrays;

public class IntSet {
    private IntArrayList[] table;
    //private IntArrayList[] hashes;
    private int size = 0;
    private int rehashSize;
    private int initialBucketSize;
    private final int rehashCutoff;
    private final int rehashBucketSizeReduction;
    public IntSet(int initialSize, int initialBucketSize, int rehashCutoff, int rehashBucketSizeReduction){
        table = new IntArrayList[initialSize];
        //hashes = new IntArrayList[initialSize];
        rehashSize = table.length*(this.rehashCutoff = rehashCutoff);
        this.initialBucketSize = initialBucketSize;
        this.rehashBucketSizeReduction = rehashBucketSizeReduction;
    }

    public IntSet(int initialSize, int initialBucketSize){
        this(initialSize,initialBucketSize,4, 1);
    }

    public int Size(){
        return size;
    }

    public void add(int i){
        if (contains(i))
            return;
        IntArrayList[] table = this.table;
        //int hash;
        int index = i % table.length;
        //int index = Long.hashCode(i) & 0x7FFFFFFF % table.length;
        if (table[index] == null){
            table[index] = new IntArrayList(initialBucketSize);
            //hashes[index] = new IntArrayList(initialBucketSize);
        }
        table[index].add(i);
        //hashes[index].add(hash);
        if (size++ >= rehashSize) rehash();
    }

    public boolean contains(int i){
        int index;
        return table[index = Math.abs(i) % table.length] != null && table[index].contains(i) != -1;
        //return table[index = Long.hashCode(i) & 0x7FFFFFFF % table.length] != null && table[index].contains(i);
    }

    public IntArrayList elements(){
        IntArrayList elements = new IntArrayList(size);
        for (IntArrayList bucket : table) {
            if (bucket == null)
                continue;
            for (int j = 0; j < bucket.size(); j++) {
                elements.add(bucket.get(j));
            }
        }
        return elements;
    }

    private void rehash(){
        if (initialBucketSize>rehashBucketSizeReduction)
            initialBucketSize -= rehashBucketSizeReduction;
        IntArrayList[] oldTable = table;
        //IntArrayList[] oldHashes = this.hashes;
        IntArrayList[] newTable = new IntArrayList[table.length + table.length];
        //IntArrayList[] newHashes = new IntArrayList[table.length + table.length];
        IntArrayList list;
        //int hash;
        for (int i = 0; i < oldTable.length; i++){
            if((list = oldTable[i]) != null){
                int s = list.size();
                while (s-->0){
                    //int index = Math.abs(Long.hashCode(element = list.get(i))) % newTable.length;
                    int index = oldTable[i].get(s) % newTable.length;
                    //int index = Long.hashCode(element = list.get(i)) & 0x7FFFFFFF % newTable.length;
                    if(newTable[index] == null) {
                        newTable[index] = new IntArrayList(initialBucketSize);
                        //newHashes[index] = new IntArrayList(initialBucketSize);
                    }
                    newTable[index].add(list.get(s));
                    //newHashes[index].add(hash);
                }
            }
        }
        table = newTable;
        //hashes = newHashes;
        rehashSize = table.length*rehashCutoff;
    }

    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }
}
