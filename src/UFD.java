import java.util.Objects;

public class UFD {
    private static class Node {
        public boolean ghost;
        public int value;
        public Node parent;
        public int size = 1;
        public Node(int value){
            this.value = value;
            parent = this;
            ghost = false;
        }
    }
    final Node[] nodes;
    public UFD(int n) {
        nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i);
        }
    }

    public int find(int p){
        Node pn = nodes[p];
        //ArrayList<Node> nodesAlongTheWay = new ArrayList<>(1);
        while (pn != pn.parent){
            if (!pn.ghost && pn.parent.ghost) {
                pn.parent.value = pn.value;
                pn.parent.ghost = false;
                pn.ghost = true;
                nodes[pn.parent.value] = pn.parent;
            }
            pn = pn.parent;
        }
        //for (int i = 0; i < nodesAlongTheWay.size(); i++) {
        //    nodesAlongTheWay.get(i).parent = pn;
        //}
        return pn.value;
    }

    public void union(int p, int q){
        Integer pAncestor = find(p);
        Integer qAncestor = find(q);
        if (Objects.equals(pAncestor, qAncestor))
            return;
        if (nodes[pAncestor].size > nodes[qAncestor].size) {
            nodes[qAncestor].parent = nodes[pAncestor];
            nodes[pAncestor].size += nodes[qAncestor].size;
        } else {
            nodes[pAncestor].parent = nodes[qAncestor];
            nodes[qAncestor].size += nodes[pAncestor].size;
        }
    }

    public void delete(int p){
        Node pn = nodes[p];
        nodes[find(p)].size--;
        pn.ghost = true;
        nodes[p] = new Node(p);
    }

}