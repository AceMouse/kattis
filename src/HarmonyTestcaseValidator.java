import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

public class HarmonyTestcaseValidator {
    public static boolean validate() throws IOException {
        int zeroes = 0, ones = 0;
        boolean deleted = false, delete, isZero;
        HashSet<String> edges = new HashSet<>();
        //try to validate testcases from 0 to 99
        for (int t = 0; t < 110; t++) {
            //if the testcase does not exist continue to the next one
            if (!Files.exists(Paths.get("itu.harmony/testcases/" + t + ".in")))
                continue;
            //clear the set of already seen edges
            edges.clear();
            //create readers for the testcase .in and .out files
            BufferedReader in = new BufferedReader(new FileReader("itu.harmony/testcases/" + t + ".in"));
            BufferedReader out = new BufferedReader(new FileReader("itu.harmony/testcases/" + t + ".ans"));

            //check what the outcome of this testcase is
            if (out.read() == '0'){
                isZero = true;
                zeroes++;
            }else {
                //generate the list of testcases with the outcome 1
                System.out.print(t + " ");
                ones++;
                isZero = false;
            }
            //close the file stream
            out.close();

            //clear the delete flag
            delete = false;
            //if more than ~ 50% of the processed testcases have the same output as this one set the delete flag
            if (zeroes > (t/2)+1 && isZero)
                delete = true;
            if (ones > (t/2)+1 && !isZero)
                delete = true;

            //if we have not already determined that the file should be deleted further validate it
            if (!delete) {
                //find how many edges are in the testcase
                String[] line = in.readLine().split(" ");
                int E = Integer.parseInt(line[1]);
                //loop through the edges
                for (int e = 0; e < E; e++) {
                    line = in.readLine().split(" ");
                    //check if the edge is parallel to any already seen edge or is a self loop
                    if (edges.contains(line[0] + " " + line[1]) || line[0].equals(line[1])) {
                        //if it is set the delete flag and do not check the remaining edges
                        delete = true;
                        break;
                    } else {
                        //if not add it to the edges already seen
                        edges.add(line[0] + " " + line[1]);
                        edges.add(line[1] + " " + line[0]);
                    }
                }
            }
            //close the file stream
            in.close();
            //delete the testcase and set the deleted flag if the delete flag is set
            if (delete){
                deleted = true;
                Files.deleteIfExists(Paths.get("itu.harmony/testcases/" + t + ".in"));
                Files.deleteIfExists(Paths.get("itu.harmony/testcases/" + t + ".ans"));
            }
        }
        System.out.println();
        //did this validation run delete any testcases
        return deleted;
    }
}
