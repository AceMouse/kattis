import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Kaploeb {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int roundTimes = sc.nextInt();
        int rounds = sc.nextInt();
        int runners = sc.nextInt();
        HashMap<Long, Integer> runnerRounds = new HashMap<>(runners);
        HashMap<Long, Long> runnerTimes = new HashMap<>(runners);
        while (roundTimes-- > 0){
            long runner = sc.nextLong();
            String[] s = sc.next().split("\\.");
            long time = Integer.parseInt(s[0])*60+Integer.parseInt(s[1]);
            if (!runnerRounds.containsKey(runner)){
                runnerRounds.put(runner, 1);
                runnerTimes.put(runner, time);
            } else {
                runnerRounds.put(runner, runnerRounds.get(runner)+1);
                runnerTimes.put(runner, runnerTimes.get(runner)+time);
            }
        }
        PriorityQueue<PQEntry> pq = new PriorityQueue<>();
        for (Map.Entry<Long, Integer> entry: runnerRounds.entrySet()) {
            if (entry.getValue() == rounds){
                pq.add(new PQEntry(entry.getKey(), runnerTimes.get(entry.getKey())));
            }
        }
        for (PQEntry entry : pq) {
            System.out.println(entry.key);
        }

    }
    private static class PQEntry implements Comparable<PQEntry>{
        long key;
        long val;

        public PQEntry(long key, long val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public int compareTo(PQEntry that) {
            return Long.compare(this.val, that.val);
        }
    }
}
