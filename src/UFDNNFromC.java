public class UFDNNFromC {
    int N = 100000;
    int S = N*2;
    int cnt;
    boolean[] ghost = new boolean[S];
    int[]  value = new int[S];
    int[] size = new int[S];
    int[]  parent = new int[S];
    int[]  pos = new int[N];
    void init() {
        for (int i=0;i<N;i++) {
            this.ghost[i] = false;
            this.pos[i] = this.value[i] = this.parent[i] = i;
            this.size[i] = 1;
        }
        cnt = N;
    }

    int find(int p) {
        int pn = this.pos[p];
        while (pn != this.parent[pn]) {
            if (!this.ghost[pn] && this.ghost[this.parent[pn]]) {
                this.value[this.parent[pn]] = this.value[pn];
                this.ghost[this.parent[pn]] = false;
                this.ghost[pn] = true;
                this.pos[this.value[this.parent[pn]]] = this.parent[pn];
            }
            pn = this.parent[pn];
        }
        return this.value[pn];
    }

    void union(int p, int q) {
        int pAncestor = this.pos[find(p)];
        int qAncestor = this.pos[find(q)];
        if (pAncestor == qAncestor) return;
        if (this.size[pAncestor] > this.size[qAncestor]) {
            this.parent[qAncestor] = pAncestor;
            this.size[pAncestor] += this.size[pAncestor];
        } else {
            this.parent[pAncestor] = qAncestor;
            this.size[qAncestor] += this.size[pAncestor];
        }
    }

//void reorganize() {
//
//
//    init();
//
//    for (int i=0;i<N;i++) {
//        unionset();
//    }
//}

    void delete(int p) {
        //if (cnt == S) reorganize();
        this.size[this.pos[find(p)]]--;
        this.ghost[this.pos[p]] = true;
        this.pos[p] = cnt++;
        this.size[this.pos[p]] = 1;
        this.parent[this.pos[p]] = this.pos[p];
        this.value[this.pos[p]] = p;
    }
}
