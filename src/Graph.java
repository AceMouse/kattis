public class Graph
{
    private final int V; // number of vertices
    private int E; // number of edges
    private IntArrayList[] adj; // adjacency lists
    public Graph(int V)
    {
        this.V = V; this.E = 0;
        adj = new IntArrayList[V]; // Create array of lists.
        for (int i = 0; i < V; i++) {
            adj[i] = new IntArrayList(4);
        }
    }
    public int V() { return V; }
    public int E() { return E; }
    public void addEdge(int v, int w)
    {
        adj[v].add(w); // Add w to v’s list.
        adj[w].add(v); // Add v to w’s list.
        E++;
    }
    public IntArrayList adj(int v)
    { return adj[v]; }
    public int deg(int v){
        return adj[v].size();
    }
}
