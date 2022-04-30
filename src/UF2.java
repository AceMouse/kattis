import java.util.Arrays;

public class UF2 {
    private final int[] parent;
    //private final int[] size;
    public UF2(int n) {
        parent = new int[n];
        //size = new int[n];
        //Arrays.fill(parent, -1);
        //Arrays.fill(size, 1);
        while (n-- > 0) {
            parent[n] = n;
        }
    }

    public int find(int p) { //heavily inspired by the book
        while (p != (p = parent[p] = parent[parent[p]]));
        return p;
    }

    public void union(int pAncestor, int qAncestor) { //heavily inspired by the book
        //int pAncestor = find(p);
        //int qAncestor = find(q);
        //if (pAncestor == qAncestor) return;
        //int t = (size[pAncestor] - size[qAncestor]) >> 31; //0 or -1
        /*switch ((size[pAncestor] - size[qAncestor]) >> 31) {
            case -1:*/
                parent[pAncestor] = qAncestor;
                /*size[qAncestor] += size[pAncestor];
                break;
            case 0:
                parent[qAncestor] = pAncestor;
                size[pAncestor] += size[qAncestor];
        }*/
    }
}

