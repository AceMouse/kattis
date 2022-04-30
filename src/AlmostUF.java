import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;

public class AlmostUF {
    private final int[] parent;
    private final int[] size;
    private final Hashtable[] children;
    private final long[] sum;
    private final static Object dummy = new Object();
    public AlmostUF(int n) {
        parent = new int[n];
        size = new int[n];
        sum = new long[n];
        children = new Hashtable[n];
        //Arrays.fill(parent, -1);
        Arrays.fill(size, 1);
        while (n-- > 0){
            sum[n] = n;
            parent[n] = n;
        }

    }

    public int find(int p) { //heavily inspired by the book
        while (p != (p = parent[p]));//= parent[p] == -1 ? p : parent[p])) ;
        return p;
    }

    public void union(int p, int q) { //heavily inspired by the book
        int pAncestor, qAncestor;
        if ((pAncestor=find(p)) == (qAncestor=find(q))) return;
        switch ((size[pAncestor] - size[qAncestor]) >> 31) {
            case -1:
                parent[pAncestor] = qAncestor;
                size[qAncestor] += size[pAncestor];
                sum[qAncestor] += sum[pAncestor];
                children[qAncestor].put(pAncestor, dummy);
                break;
            case 0:
                //children[pAncestor] = size[pAncestor] == 1?new Hashtable<Integer, Object>():children[pAncestor];
                children[pAncestor] = size[pAncestor] == 1 ? new Hashtable() : children[pAncestor];
                parent[qAncestor] = pAncestor;
                size[pAncestor] += size[qAncestor];
                sum[pAncestor] += sum[qAncestor];
                children[pAncestor].put(qAncestor, dummy);
        }
    }

    public void move(int p, int q) {
        int pAncestor;
        int qAncestor;
        int newAncestor;
        if ((newAncestor = pAncestor = find(p))==(qAncestor=find(q))) return;
        int s;
        if (children[p] != null && (s = children[p].size()) > 0) {
            Enumeration<Integer> it = children[p].keys();
            if (pAncestor == p) {
                parent[newAncestor = it.nextElement()] = newAncestor;
                s--;
            }
            Integer pChild;

            if (children[newAncestor] == null) children[newAncestor] = new Hashtable();

            while (s-- > 0) children[parent[pChild = it.nextElement()] = newAncestor].put(pChild, dummy);
        }

        if (children[parent[p]] != null) children[parent[p]].remove(p);
        if (children[p] != null)children[p].clear();
        if (children[qAncestor] == null) children[qAncestor] = new Hashtable();
        children[qAncestor].put(p,dummy);
        parent[p] = p;
        size[newAncestor] = Math.max(size[pAncestor] - 1,1);
        sum[newAncestor] = Math.max(sum[pAncestor] - p,newAncestor);
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