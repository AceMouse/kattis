import java.util.HashMap;
import java.util.Random;

public class LongIntHashMap {
    public static void main(String[] args){
        boolean broken = false;
        long[] keys = new long[10];
        int[] ops = new int[10], vals = new int[10];
        Random random = new Random();
        int j = 0;
        while (!broken){
            System.out.println(j++);
            LongIntHashMap test = new LongIntHashMap();
            HashMap<Long, Integer> control = new HashMap<>();
            keys = new long[10];
            ops = new int[10];
            vals = new int[10];
            for (int i = 0; i < 10; i++) {
                int op = random.nextInt(3);
                ops[i] = op;
                if (op == 0){
                    long key = random.nextInt(10)+1;
                    keys[i] = key;
                    broken = test.containsKey(key) != control.containsKey(key);
                }
                if (op == 1){
                    long key = random.nextInt(10)+1;
                    keys[i] = key;
                    if ((test.containsKey(key) && control.containsKey(key)))
                        if (broken = test.get(key) != control.get(key))
                            System.out.println(test.get(key) + " != " + test.get(key));
                }
                if (op == 2){
                    long key = random.nextInt(10)+1;
                    keys[i] = key;
                    int val = random.nextInt(10)+1;
                    vals[i] = val;
                    test.put(key, val);
                    control.put(key,val);
                    broken = (test.get(key) != control.get(key));
                }
            }

        }
        for (int i = 0; i < 10; i++) {
            System.out.println("op: " + ops[i] + " key: " + keys[i] + " val: " + vals[i]);
        }
    }
    private Node[] table;
    private final int initialSize;
    private int size = 0, ghostKeys = 0;
    private final int MAX_LOAD, RESIZE_FACTOR;
    public LongIntHashMap(int initialSize, int MAX_LOAD, int RESIZE_FACTOR){
        this.table = new Node[this.initialSize = initialSize];
        this.MAX_LOAD = MAX_LOAD;
        this.RESIZE_FACTOR = RESIZE_FACTOR;
    }

    public LongIntHashMap(){
        this(11, 4, 2);
    }

    private int hash(long i, int max){
        return (((int)(i^(i<<32)))>>>1) % max;
    }

    public int size(){ return size-ghostKeys; }

    public void put(long key, int value){
        if (value == 48549)
            System.out.println("put: "+key + ", " + value);
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
        if (size++ >= table.length*MAX_LOAD)
            rehash(table.length*MAX_LOAD*RESIZE_FACTOR);
    }

    public void remove(long key){
        System.out.println("remove");
        put(key, Integer.MIN_VALUE);
        ghostKeys++;
    }

    public int get(long key){
        int hash = hash(key, table.length);
        //if (hash == hash(32464818L, table.length))
        //  System.out.println("key: " + key);
        Node node = table[hash];
        while(node != null){
            if (node.key == key) return node.value;
            node = node.next;
        }
        return Integer.MIN_VALUE;
    }

    public boolean containsKey(long key){
        return get(key) > -1;
    }

    public void clear(){
        System.out.println("clear");
        table = new Node[initialSize];
        size = ghostKeys = 0;
    }

    private void rehash(int newSize){
        System.out.println("rehash!");
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
        public final long key;
        public int value;
        public Node next = null;
        public Node(long key, int value){
            this.key = key;
            this.value = value;
        }
    }

    public Enumeration keys(){
        return new Enumeration();
    }

    public class Enumeration{
        private final Node[] table = LongIntHashMap.this.table;
        private int currentIndex = 0;
        private Node currentNode = table[0];
        public long next(){
            while (currentNode == null && currentIndex < table.length-1){
                currentNode = table[++currentIndex];
            }
            Node nextNode = currentNode;
            currentNode = currentNode.next;
            return nextNode.key;
        }
    }
}

