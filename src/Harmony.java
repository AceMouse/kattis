import java.io.IOException;

public class Harmony {
    private static boolean[] marked;
    private static boolean[] color;
    private static boolean isHarmonious = true;
    public static void main(String[] args) throws IOException {
        HarmonyGraph graph = new HarmonyGraph();
        marked = new boolean[graph.V];
        color = new boolean[graph.V];
        for (int s = 0; s < graph.V; s++) {
            if(!marked[s]){
                dfs(graph, s);
                if (!isHarmonious) break;
            }
        }
        System.out.println(isHarmonious?'1':'0');

    }

    private static void dfs(HarmonyGraph graph, int v){
        marked[v] = true;
        IntArrayList adj = graph.adj(v);
        int w, s = adj.size();
        long lv = ((long) v) << 18;
        LongSet harmonyEdges = graph.getHarmonyEdges();
        while (s-->0) {
            if (!marked[w = adj.get(s)]){
                if (harmonyEdges.contains(lv|w)) color[w] = color[v];
                else color[w] = !color[v];
                dfs(graph, w);
            } else if(isHarmonious){
                isHarmonious = color[v] == color[w] == harmonyEdges.contains(lv|w);
            } else
                return;
        }
    }

    public static long cantor(int a, int b){ //inspiration: https://en.wikipedia.org/wiki/Pairing_function
        return (long)(0.5*(a+b)*(a+b+1)+b);
    }

    public static long unorderedPairing(int a, int b){ //inspiration: https://gist.github.com/ma11hew28/9457506;
        int x;
        return a*b+(x = Math.abs(b-a)-1)*x/4;
    }
}