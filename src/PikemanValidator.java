import java.util.Arrays;
import java.util.Random;

public class PikemanValidator {
    public static void main(String[] args)  {
        int bound = 10;
        while (true) {
            var random = new Random();
            int n = random.nextInt(bound) + 1;
            long T = random.nextLong(bound) + 1;
            int a = random.nextInt(bound) + 1;
            int b = random.nextInt(bound) + 1;
            int c = random.nextInt(bound) + 1;
            int t = random.nextInt(c) + 1;
            if (!hard(n, T, a, b, c, t).equals(easy(n, T, a, b, c, t))) {
                System.out.println(n + " " + T + " " + a + " " + b + " " + c + " " + t);
                System.out.println(hard(n,T,a,b,c,t));

                System.out.println(easy(n,T,a,b,c,t));
                break;
            }

        }

    }
    public static String hard(int N, long T, int A, int B, int C, int t) {
        long totaltime = 0;
        long p = 0;
        //count occurrences of times
        int[] counts = new int[C+2];
        int[] encounters = new int[C+2];

        encounters[t] = 1;
        counts[t]++;
        int cnt = 1;
        boolean circle = false;
        while (cnt < N) {
            if (encounters[t = (int) (((long)A*t+B)%C) +1] != 0){
                counts[t]++;
                cnt++;
                circle = true;
                break;
            }
            encounters[t] = ++cnt;
            counts[t]++;
        }

        if (circle) {
            int circleLength = cnt - encounters[t];
            int wholeadd = (N - cnt) / circleLength;
            int leftadd = (N - cnt) % circleLength;
            for (int i = 0; i < leftadd; i++) {
                counts[t = (int) (((long) A * t + B) % C) + 1] += wholeadd + 1;
            }
            for (int i = leftadd; i < circleLength; i++) {
                counts[t = (int) (((long) A * t + B) % C) + 1] += wholeadd;
            }
        }
        cnt = 0;
        int time = 0;
        while (cnt < N){
            //advance to next time
            while (time < counts.length && counts[time] == 0) time++;
            if (totaltime + time >= T)
                break;
            long cantakeN = N-cnt;
            long cantakeT = (T-1 - totaltime)/time;

            long cantake = Math.min(cantakeN, cantakeT);
            cantake = Math.min(cantake, counts[time]);
            long timetakeen = cantake * time;
            long penalty = totaltime*cantake + time *(((cantake +1)*cantake)/2 );
            p += penalty;
            totaltime += timetakeen;
            cnt += cantake;
            time++;
        }
        return cnt + " " + p % 1000000007;
    }

    public static String neasy(int N, long T, int A, int B, int C, int t) {
        long p = 0;
        //count occurrences of times
        long[] counts = new long[C+2];
        counts[t]++;
        for (int i = 1; i < N; i++) {
            counts[t = (int) (((long)A*t+B)%C) +1]++;
        }
        long totaltime = 0;
        int cnt = 0;
        int time = 0;
        while (cnt < N){
            //advance to next time
            while (time < counts.length && counts[time] == 0) time++;
            if (totaltime + time > T)
                break;
            long cantakeN = N-cnt;
            long cantakeT = (T - totaltime)/time;

            long cantake = Math.min(cantakeN, cantakeT);
            cantake = Math.min(cantake, counts[t]);
            long timetakeen = cantake * time;
            long penalty = totaltime*cantake + time *(((cantake +1)*cantake)/2 );
            p += penalty;
            totaltime += timetakeen;
            cnt += cantake;
        }


        return cnt + " " + Math.round(p) % 1000000007;
    }
    public static String easy(int N, long T, int A, int B, int C, int t) {
        long totaltime = 0;
        int cnt = 0;
        long p = 0;
        //count occurrences of times
        long[] counts = new long[C+2];
        counts[t]++;
        for (int i = 1; i < N; i++) {
            counts[t = (int) (((long)A*t+B)%C) +1]++;
        }
        int time = 0;
        //advance to first time
        while (counts[time] == 0) time++;
        while (cnt < N && totaltime + time < T){
            //advance to next time
            while (counts[time] == 0) time++;
            totaltime += time;
            counts[time]--;
            p += totaltime;
            cnt ++;
        }
        return cnt + " " + p % 1000000007;
    }
}
