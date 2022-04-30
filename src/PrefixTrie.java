import java.util.HashMap;

public class PrefixTrie {
    HashMap<Long, Node> nodes;
    int nodeNumber;
    public static class Node {
        int cnt = 0;
        final long number;
        public Node(long number){
            this.number = number;
        }
    }
    public PrefixTrie(int initialNodeNumber) {
        nodeNumber = 0;
        nodes = new HashMap<>(initialNodeNumber);
    }
    public PrefixTrie() {
        nodeNumber = 0;
        nodes = new HashMap<>();
    }
    public int add(byte[] bytes){
        long prevnumber = 0, previd;
        Node node = null;
        for (int i = 0; i < bytes.length; i++) {
            if (!nodes.containsKey(previd = (((bytes[i]-'a')<<1) | (prevnumber<<9) | 1)))
                nodes.put(previd, node = new Node(nodeNumber++));
            else
                node = nodes.get(previd);
            node.cnt++;
            prevnumber = node.number;
        }
        return node.cnt;
    }
    public int get(byte[] bytes){
        long prevnumber = 0, previd;
        Node node = null;
        for (int i = 0; i < bytes.length; i++) {
            if (!nodes.containsKey(previd = bytes[i] | (prevnumber<<8)))
                return 0;
            node = nodes.get(previd);
            prevnumber = node.number;
        }
        return node.cnt;
    }

}
