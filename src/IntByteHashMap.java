public class IntByteHashMap {
    private Node[] table;
    private int initialSize;
    private int size = 0, ghostKeys = 0;
    private final int MAX_LOAD, RESIZE_FACTOR;
    public IntByteHashMap(int initialSize, int MAX_LOAD, int RESIZE_FACTOR){
        this.table = new Node[this.initialSize = initialSize];
        this.MAX_LOAD = MAX_LOAD;
        this.RESIZE_FACTOR = RESIZE_FACTOR;
    }

    public IntByteHashMap(){
        this(11, 4, 2);
    }

    private int hash(int i, int max){
        return i % max;
    }

    public int size(){ return size-ghostKeys; }

    public void put(int key, byte value){
        int hash = hash(key, table.length);
        Node node = table[hash];
        while (node != null){
            if (node.key == key) {
                node.value = value;
                return;
            }
            node = node.next;
        }
        Node newEntry = new Node(key, value);
        newEntry.next = table[hash];
        table[hash] = newEntry;
        if (size++ >= table.length * MAX_LOAD) rehash(table.length * MAX_LOAD * RESIZE_FACTOR);
    }

    public void remove(int key){
        put(key, Byte.MIN_VALUE);
        ghostKeys++;
    }

    public byte get(int i){
        int hash = hash(i, table.length);
        Node node = table[hash];
        while(node != null){
            if (node.key == i) return node.value;
            node = node.next;
        }
        return Byte.MIN_VALUE;
    }

    public void clear(){
        table = new Node[initialSize];
        size = ghostKeys = 0;
    }

    private void rehash(int newSize){
        Node[] oldTable = table;
        Node[] newTable = new Node[newSize];
        for (int i = 0; i < oldTable.length; i++) {
            Node node = oldTable[i];
            while (node != null){
                int hash = hash(node.key, newSize);
                node.next = newTable[hash];
                newTable[hash] = node;
                node = node.next;
            }
        }
        table = newTable;
    }

    private class Node{
        public final int key;
        public byte value;
        public Node next = null;
        public Node(int key, byte value){
            this.key = key;
            this.value = value;
        }
    }
}

