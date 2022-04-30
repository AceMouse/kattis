import java.util.Arrays;

public class UFTildes {
    private final int[] parent;
    private final int[] size;

    public UFTildes(int n) {
        parent = new int[n];
        size = new int[n];
        //Arrays.fill(parent, -1);
        //Arrays.fill(size, 1);
        while (n-- > 0) {
            parent[n] = n;
            size[n] = 1;
        }
    }

    public int find(int p) { //heavily inspired by the book
        while (p != (p = parent[p] = parent[parent[p]]));
        return p;
    }

    public int size(int p){
        return size[find(p)];
    }

    public void union(int p, int q) { //heavily inspired by the book
        int qAncestor;
        int pAncestor;
        if ((pAncestor = find(p)) == (qAncestor = find(q))) return;
        //if (size[pAncestor] < size[qAncestor]){
        parent[pAncestor] = qAncestor;
        size[qAncestor] += size[pAncestor];
        /*} else {
            parent[qAncestor] = pAncestor;
            size[pAncestor] += size[qAncestor];
        }*/
    }
}