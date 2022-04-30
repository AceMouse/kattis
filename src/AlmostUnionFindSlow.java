import java.util.Arrays;

public class AlmostUnionFindSlow {
    private final int[] parent;
    private final int[] size;
    private final long[] sum;
    public AlmostUnionFindSlow(int n) {
        parent = new int[n];
        size = new int[n];
        sum = new long[n];
        for (int i = 0; i<n; i++) {
            sum[i] = i;
            parent[i] = i;
        }
        Arrays.fill(size,1);
    }

    public int find(int p) {
        while (p != (p = parent[p] = parent[parent[p]]));
        return p;
    }

    public void union(int p, int q) {
        int pAncestor = find(p);
        int qAncestor = find(q);
        if (pAncestor == qAncestor) return;
        switch ((size[pAncestor] - size[qAncestor]) >> 31) {
            case -1:
                parent[pAncestor] = qAncestor;
                size[qAncestor] += size[pAncestor];
                sum[qAncestor] += sum[pAncestor];
                break;
            case 0:
                parent[qAncestor] = pAncestor;
                size[pAncestor] += size[qAncestor];
                sum[pAncestor] += sum[qAncestor];
                break;
        }
    }

    public void move(int p, int q) {
        int pAncestor = find(p);
        int qAncestor = find(q);
        if (pAncestor == qAncestor) return;
        int newAncestor = pAncestor;
        boolean pIsNewAncestor = newAncestor == p;
        //sets the parent of all of p's children to be either the ancestor of p,
        //or if p is the ancestor the first child of p found
        for (int i = 0; i < parent.length; i++) {
            if (i != p && parent[i] == p) {
                if (pIsNewAncestor) {
                    newAncestor = i;
                    pIsNewAncestor = false;
                }
                parent[i] = newAncestor;
            }
        }
        parent[p] = p;
        size[newAncestor] = size[pAncestor] - 1;
        sum[newAncestor] = sum[pAncestor] - p;
        size[p] = 1;
        sum[p] = p;
        parent[p] = qAncestor;
        size[qAncestor] += 1;
        sum[qAncestor] += p;
    }

    public String show(int p) {
        int pAncestor = find(p);
        return size[pAncestor] + " " + sum[pAncestor] + "\n";
    }
}
