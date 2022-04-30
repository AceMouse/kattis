import java.util.*;
//heavily inspired by the Algorithms and Datastructures Fourth Edition by Sedgewick and Wayne
public class UF {
    private final int[] parent;
    private final int[] size;
    private int sets;
    private boolean[] setExists;
    public UF(int n) {
        parent = new int[n];
        size = new int[n];
        setExists = new boolean[n];
        Arrays.fill(setExists, true);
        Arrays.fill(parent, -1);
        Arrays.fill(size,1);
        sets = n;
    }
    public void reset(int n){
        Arrays.fill(setExists, 0, n,true);
        Arrays.fill(parent, 0, n,-1);
        Arrays.fill(size,0, n,1);
        sets = n;
    }
    public int size(int p){
        return size[find(p)];
    }
    public int find(int p) {
        int t;
        while (p != (p = parent[p] = (parent[p] * (t = ((parent[p]>>31)+1))) + (1-t) * p));
        return p;
    }
    public int[] sets(){
        int cnt = 0;
        int[] sets = new int[this.sets];
        for (int i = 0; i < setExists.length; i++) {
            if (setExists[i])
                sets[cnt++] = i;
        }
        return sets;
    }
    public void union(int p, int q) {
        int pAncestor = find(p);
        int qAncestor = find(q);
        if (pAncestor == qAncestor)
            return;
        int t = ((size[pAncestor] - size[qAncestor]) >> 31)+1;
        int small = (1-t)*pAncestor + t*qAncestor;
        int large = (1-t)*qAncestor + t*pAncestor;
        parent[small] = large;
        setExists[small] = false;
        sets--;
        size[large] += size[small];
    }
}