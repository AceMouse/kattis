public class UFDNNCacheOptimised {
    private int[] ints;
    private int self, size, parent, ghost, posOffset, nodeOffset;
    private int cnt;
    private final int s, N;
    private final int log2n;
    private int auxOffset;
    public UFDNNCacheOptimised(int n){
        N = n;
        s = n*2;
        log2n = (int)(Math.log(n) / Math.log(2));
        posOffset = 0;
        auxOffset = n + n*4*2;
        nodeOffset = n;
        self = 0;
        size = 1;
        parent = 2;
        ghost = 3;


        ints = new int[auxOffset + log2n];
        cnt = n;
        for (int i = 0; i < n; i++) {
            ints[self + nodeOffset+i*3] = ints[posOffset + i] = ints[parent + nodeOffset+i*3] = nodeOffset + i*3;
            ints[size + nodeOffset+i*3] = 1;
            ints[ghost + nodeOffset+i*3] = 0;
        }
    }

    public int find(int p) {return pfind(p);}
    public void union(int s, int t) {punion(s,t);}
    public void delete(int p) {pdelete(p);}

    private int pfind(int p){
        int pn = ints[posOffset + p];
        int cnt = 0;

        while (pn != ints[parent + pn]){
            if (ints[ghost+pn] == 0 && ints[ints[parent+pn]+ghost] == 1){
                ints[self +ints[parent+pn]] = ints[self +pn];
                ints[ghost +ints[parent+pn]] = 0;
                ints[ghost+pn] = 1;
                ints[posOffset  +ints[self +ints[parent+pn]]] = ints[parent+pn];
            }
            pn = ints[parent+pn];
            ints[auxOffset+cnt++] = pn;
        }
        for (int i = 0; i < cnt; i++) {
            ints[parent+ints[auxOffset+i]] = pn;
        }
        return pn;
    }

    private void punion(int p, int q){
        int pAncestor = find(p);
        int qAncestor = find(q);
        if (pAncestor == qAncestor)
            return;
        if (ints[size+pAncestor] > ints[size+qAncestor]){
            ints[parent+qAncestor] = pAncestor;
            ints[size+pAncestor] += ints[size+qAncestor];
        } else {
            ints[parent+pAncestor] = qAncestor;
            ints[size+qAncestor] += ints[size+pAncestor];
        }
    }

    private void pdelete(int p){
        if (cnt == s-1)
            reorganize();
        int n = find(p);
        ints[size+n]--;
        ints[ghost +ints[posOffset+p]] = 1;
        ints[posOffset+p] = nodeOffset + 3*cnt++;
        ints[size  +ints[posOffset+p]] = 1;
        ints[parent+ints[posOffset+p]] = ints[posOffset+p];
        ints[self  +ints[posOffset+p]] = p;
        ints[ghost +ints[posOffset+p]] = 0;
    }

    private void reorganize(){
        UFDNNCacheOptimised ufdnn = new UFDNNCacheOptimised(N);
        for (int i = 0; i < N; i++) {
            ufdnn.union(find(i), i);
        }
        this.ints = ufdnn.ints;
        this.cnt = ufdnn.cnt;
    }


}
