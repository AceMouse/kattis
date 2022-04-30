import java.util.*;
public class UFWorks {
    private final int[] parent;
    private final int[] size;
    private final HashMap[] children;
    private static final Object dummy = new Object();
    public UFWorks(int n) {
        parent = new int[n];
        size = new int[n];
        Arrays.fill(parent, -1);
        Arrays.fill(size,1);
        children = new HashMap[n];
    }

    public int find(int p) { //heavily inspired by the book
        while (p != (p = parent[p] = parent[p] == -1? p : parent[p]));
        return p;
    }

    public void union(int pAncestor, int qAncestor) { //heavily inspired by the book
        switch ((size[pAncestor] - size[qAncestor]) >> 31) {
            case -1:
                parent[pAncestor] = qAncestor;
                size[qAncestor] += size[pAncestor];
                children[qAncestor].put(pAncestor, dummy);
                break;
            case 0:
                children[pAncestor] = size[pAncestor] == 1?new HashMap<Integer, Object>():children[pAncestor];
                parent[qAncestor] = pAncestor;
                size[pAncestor] += size[qAncestor];
                children[pAncestor].put(qAncestor, dummy);
                /*
                switch (size[pAncestor]) {
                    case 1:
                        children[pAncestor] = new HashMap<Integer, Object>();
                    default:
                        parent[qAncestor] = pAncestor;
                        size[pAncestor] += size[qAncestor];
                        children[pAncestor].put(qAncestor, dummy);
                }*/
        }
    }

    public void move(int p, int qAncestor, int pAncestor) {
        int newAncestor = pAncestor;
        int s;
        if (children[p] != null && (s = children[p].size())>0) {
            Iterator it = children[p].keySet().iterator();
            if (pAncestor == p) {
                parent[newAncestor = (int) it.next()] = newAncestor;
                s--;
            }
            Integer pChild;
            if (children[newAncestor] == null) children[newAncestor] = new HashMap<Integer,Object>();
            while (s-- > 0) children[parent[pChild = (Integer) it.next()] = newAncestor].put(pChild, dummy);
            children[p].clear();
        } //else
        //children[p] = new HashMap<Integer, Object>();

        size[newAncestor] = --size[pAncestor];
        size[p] = 0;
        Integer P = p;
        if (children[parent[p]] != null) children[parent[p]].remove(P);
        parent[p] = qAncestor;
        if (children[qAncestor] == null) children[qAncestor] = new HashMap<Integer, Object>();
        children[qAncestor].put(P,dummy);
        size[qAncestor]++;
    }
}
