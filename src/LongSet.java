public class LongSet {
    private LongArrayList[] table;
    private IntArrayList[] hashes;
    private int size = 0;
    private int rehashSize;
    private int initialBucketSize;
    private final int rehashCutoff;
    private final int rehashBucketSizeReduction;
    private int mask;
    public LongSet(int initialSize, int initialBucketSize, int rehashCutoff, int rehashBucketSizeReduction){
        initialSize--;
        initialSize |= initialSize>>1;
        initialSize |= initialSize>>2;
        initialSize |= initialSize>>4;
        initialSize |= initialSize>>8;
        initialSize |= initialSize>>16;
        mask = initialSize++;
        //initialSize = (Integer.highestOneBit(initialSize)<<1)
        //inspiration: https://www.youtube.com/watch?v=ZusiKXcz_ac
        table = new LongArrayList[initialSize];
        hashes = new IntArrayList[initialSize];
        rehashSize = table.length*(this.rehashCutoff = rehashCutoff);
        this.initialBucketSize = initialBucketSize;
        this.rehashBucketSizeReduction = rehashBucketSizeReduction;
    }
    public LongSet(int initialSize, int initialBucketSize){
        this(initialSize,initialBucketSize,4, 1);
    }
    public void add(long i){
        LongArrayList[] table = this.table;
        int hash;
        int index = (hash = hash(i)) & mask;
        //int index = Long.hashCode(i) & 0x7FFFFFFF % table.length;
        if (table[index] == null){
            table[index] = new LongArrayList(initialBucketSize);
            hashes[index] = new IntArrayList(initialBucketSize);
        }
        table[index].add(i);
        hashes[index].add(hash);
        if (size++ >= rehashSize) rehash();
    }

    public boolean contains(long i){
        int index = hash(i) & mask;
        return table[index] != null && table[index].contains(i);
        //return table[index = Long.hashCode(i) & 0x7FFFFFFF % table.length] != null && table[index].contains(i);
    }

    private int hash(long i){
        //return Math.abs(Long.hashCode(i));
        return Long.hashCode(i);
    }

    private void rehash(){
        if (initialBucketSize>rehashBucketSizeReduction)
            initialBucketSize -= rehashBucketSizeReduction;
        mask = (mask<<1)+1;
        LongArrayList[] oldTable = table;
        IntArrayList[] oldHashes = this.hashes;
        LongArrayList[] newTable = new LongArrayList[table.length + table.length];
        IntArrayList[] newHashes = new IntArrayList[table.length + table.length];
        LongArrayList list;
        int hash;
        for (int i = 0; i < oldTable.length; i++){
            if((list = oldTable[i]) != null){
                int s = list.size();
                while (s-->0){
                    //int index = Math.abs(Long.hashCode(element = list.get(i))) % newTable.length;
                    int index = (hash = oldHashes[i].get(s)) & mask;
                    //int index = Long.hashCode(element = list.get(i)) & 0x7FFFFFFF % newTable.length;
                    if(newTable[index] == null) {
                        newTable[index] = new LongArrayList(initialBucketSize);
                        newHashes[index] = new IntArrayList(initialBucketSize);
                    }
                    newTable[index].add(list.get(s));
                    newHashes[index].add(hash);
                }
            }
        }
        table = newTable;
        hashes = newHashes;
        rehashSize = table.length*rehashCutoff;
    }

    public int size() {
        return size;
    }
}
