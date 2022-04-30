import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HarmonyTestcaseGenerator {
    private static boolean[] marked;
    private static boolean[] color;
    private static boolean isHarmonious = true;
    public static void main(String[] args) throws IOException {
        //repeat generation of testcases till all are valid
        do {
            //try to generate testcases 2 through 99
            for (int t = 2; t < 100; t++) {
                //if the testcase already exists continue to the next one
                if (Files.exists(Paths.get("itu.harmony/testcases/" + t + ".ans")))
                    continue;
                //generate a random graph and apply (omitted) solution
                //save both the random graph and solution to t.in and t.out
                Writer out = new BufferedWriter(new FileWriter("itu.harmony/testcases/" + t + ".ans"));
                //solve...
                isHarmonious = true;
                HarmonyTestcaseGeneratorGraph graph = new HarmonyTestcaseGeneratorGraph(t);
                marked = new boolean[graph.V];
                color = new boolean[graph.V];
                for (int s = 0; s < graph.V; s++) {
                    if (!marked[s]) {
                        dfs(graph, s);
                        if (!isHarmonious) break;
                    }
                }
                out.write(isHarmonious ? '1' : '0');
                //write the solution to file and close the stream
                out.flush();
                out.close();
            }
        //validate the current testcases and generate new ones to replace the invalid ones
        } while (HarmonyTestcaseValidator.validate());

    }

    private static void dfs(HarmonyTestcaseGeneratorGraph graph, int v){
        marked[v] = true;
        IntArrayList adj = graph.adj(v);
        int w, s = adj.size();
        long lv = ((long) v)<< 18;
        LongSet harmonyEdges = graph.getHarmonyEdges();
        while (s-->0) {
            if (!marked[w = adj.get(s)]){
                if (harmonyEdges.contains(lv+w)) color[w] = color[v];
                else color[w] = !color[v];
                dfs(graph, w);
            } else if(isHarmonious){
                isHarmonious = color[v] == color[w] == harmonyEdges.contains(lv+w);
            } else
                return;
        }
    }
}