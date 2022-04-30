public class UFDNN {
    private boolean[] ghost;
    private int[] ints;
    private int valueOffset;
    private int sizeOffset;
    private int parentOffset;
    private int posOffset;
    private int cnt;
    private final int s, N;
    private final int log2n;
    private int prevOffset;
    public UFDNN(int n){
        N = n;
        s = n*2;
        log2n = (int)(Math.log(n) / Math.log(2));
        prevOffset = 0;
        ghost = new boolean[s];
        valueOffset = log2n;
        sizeOffset = valueOffset + s;
        parentOffset = sizeOffset + s;
        posOffset = parentOffset + s;
        ints = new int[posOffset + n];
        cnt = n;
        for (int i = 0; i < n; i++) {
            ints[posOffset+i] = ints[valueOffset+i] = ints[parentOffset+i] = i;
            ints[sizeOffset+i] = 1;
        }
    }

    public int find(int p){
        int pn = ints[posOffset+p];
        int cnt = 0;

        while (pn != ints[parentOffset+pn]){
            if (!ghost[pn] && ghost[ints[parentOffset+pn]]){
                ints[valueOffset+ints[parentOffset+pn]] = ints[valueOffset+pn];
                ghost[ints[parentOffset+pn]] = false;
                ghost[pn] = true;
                ints[posOffset+ints[valueOffset+ints[parentOffset+pn]]] = ints[parentOffset+pn];
            }
            ints[prevOffset+cnt++] = pn;
            pn = ints[parentOffset+pn];
        }
        for (int i = 0; i < cnt; i++) {
            ints[parentOffset+ints[prevOffset+i]] = pn;
        }
        return ints[valueOffset+pn];
    }

    public void union(int p, int q){
        int pAncestor = ints[posOffset+find(p)];
        int qAncestor = ints[posOffset+find(q)];
        if (pAncestor == qAncestor)
            return;
        if (ints[sizeOffset+pAncestor] > ints[sizeOffset+qAncestor]){
            ints[parentOffset+qAncestor] = pAncestor;
            ints[sizeOffset+pAncestor] += ints[sizeOffset+qAncestor];
        } else {
            ints[parentOffset+pAncestor] = qAncestor;
            ints[sizeOffset+qAncestor] += ints[sizeOffset+pAncestor];
        }
    }

    public void delete(int p){
        if (cnt == s-1)
            reorganize();
        ints[sizeOffset+ints[posOffset+find(p)]]--;
        ghost[ints[posOffset+p]] = true;
        ints[posOffset+p] = cnt++;
        ints[sizeOffset+ints[posOffset+p]] = 1;
        ints[parentOffset+ints[posOffset+p]] = ints[posOffset+p];
        ints[valueOffset+ints[posOffset+p]] = p;
    }

    public void reorganize(){
        UFDNN ufdnn = new UFDNN(N);
        for (int i = 0; i < N; i++) {
            ufdnn.union(find(i), i);
        }
        this.ghost = ufdnn.ghost;
        this.ints = ufdnn.ints;
        this.cnt = ufdnn.cnt;
    }
}
