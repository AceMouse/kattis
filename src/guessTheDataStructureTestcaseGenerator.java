import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class guessTheDataStructureTestcaseGenerator {
    public static void main(String[] args) throws IOException {
        Random random = new Random();
        int tests = 100;
        for (int i = 0; i < tests; i++) {
            Writer out = new BufferedWriter(new FileWriter("guessTheDataStructure/testcases/" + i + ".in"));
            int t = random.nextInt(10)+1;
            while(t-- > 0){
                int n = random.nextInt(10)+1;
                out.write(n + "\n");
                int s = 0;
                ArrayList<Integer> used = new ArrayList<>();
                while (n-->0){
                    int command = (random.nextInt(2)+1);
                    if (command == 2 && used.size() == 0)
                        command = 1;
                    int x;
                    if(command == 1) {
                        x = (random.nextInt(100) + 1);
                        used.add(x);
                    }
                    else {
                        int index;
                        x = used.get(index = random.nextInt(used.size()));
                        used.remove(index);
                    }
                    out.write( command + " " +  x + "\n");
                }
            }
            out.flush();

            generateAnswer(i);
        }
    }
    public static void generateAnswer(int i) throws IOException {
        Scanner reader = new Scanner(new FileInputStream("guessTheDataStructure/testcases/" + i + ".in"));
        final byte[] queueOutput = "queue\n".getBytes();
        final byte[] stackOutput = "stack\n".getBytes();
        final byte[] priorityQueueOutput = "priority queue\n".getBytes();
        final byte[] notSureOutput = "not sure\n".getBytes();
        final byte[] impossibleOutput = "impossible\n".getBytes();
        final byte[][] output = {impossibleOutput,
                priorityQueueOutput,
                stackOutput,
                notSureOutput,
                queueOutput,
                notSureOutput,
                notSureOutput,
                notSureOutput
        };
        final byte[] canNotBePQ =    {0, 0, 2, 2, 4, 4, 6, 6}; //maps i to i&0b110
        final byte[] canNotBeStack = {0, 1, 0, 1, 4, 5, 4, 5}; //maps i to i&0b101
        final byte[] canNotBeQueue = {0, 1, 2, 3, 0, 1, 2, 3}; //maps i to i&0b011
        Writer out = new BufferedWriter(new FileWriter("guessTheDataStructure/testcases/" + i + ".ans"));
        final boolean[] canBePQ =    {false,true,false,true,false,true,false,true};
        final boolean[] canBeStack = {false,false,true,true,false,false,true,true};
        final boolean[] canBeQueue = {false,false,false,false,true,true,true,true};
        final ByteMaxPQ pq =    new ByteMaxPQ(1000);
        final ByteStack stack = new ByteStack(1000);
        final ByteQueue queue = new ByteQueue(1000);
        while(true){
            int n = reader.nextInt(), s = 0;
            pq.reset();
            stack.reset();
            queue.reset();
            byte canBe = 7;
            while (n-- > 0) {
                if (reader.nextInt() == 1){
                    s++;
                    byte item = (byte)reader.nextInt();
                    pq.insert(item);
                    stack.push(item);
                    queue.enqueue(item);
                } else {
                    byte item = (byte)reader.nextInt();
                    if (--s<0 || canBe == 0) {
                        canBe = 0;
                        while (n-- > 0){
                            reader.nextInt();
                            reader.nextInt();
                        }
                        break;
                    }
                    if (canBePQ[canBe] && item != pq.delMax())
                        canBe = canNotBePQ[canBe];
                    if (canBeStack[canBe] && item != stack.pop())
                        canBe = canNotBeStack[canBe];
                    if (canBeQueue[canBe] && item != queue.dequeue())
                        canBe = canNotBeQueue[canBe];
                }
            }
            out.write(new String(output[canBe]));
            if (!reader.hasNext()) {
                out.flush();
                return;
            }
        }
    }
}
