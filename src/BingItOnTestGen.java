import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class BingItOnTestGen {
    public static void main(String[] args) throws IOException {
        genTest();
        BingItOn.main(new String[0]);
        BingItOnBrute.main(new String[0]);
    }

    private static void genTest() throws IOException {
        Random rand = new Random();
        if (!Files.isDirectory(Paths.get("/tmp/BringItOnTest")))
            Files.createDirectory(Paths.get("/tmp/BringItOnTest"));
        Files.deleteIfExists(Paths.get("/tmp/BringItOnTest/1.in"));
        Files.createFile(Paths.get("/tmp/BringItOnTest/1.in"));
        BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.get("/tmp/BringItOnTest/1.in").toString()));
        int N = rand.nextInt(1,100);
        writer.write(String.valueOf(N));
        writer.write('\n');
        for (int i = 0; i < N; i++) {
            int chs = rand.nextInt(1,33);
            for (int j = 0; j < chs; j++) {
                writer.write((char)rand.nextInt('a','z'+1));
            }
            writer.write('\n');
        }
        writer.flush();
    }
}
