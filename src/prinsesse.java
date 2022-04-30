import java.util.Scanner;

public class prinsesse {
    private static Scanner sc = new Scanner(System.in);
    private static int M,N,S;
    public static void main(String[] args) {
        M = sc.nextInt();
        N = sc.nextInt();
        S = sc.nextInt();
        int sqrt = (int)Math.sqrt(M)+1;
        if (N < (S+sqrt-1)*2){
            for (int i = 0; i < M; i+=sqrt) {
                StringBuilder stringBuilder = new StringBuilder("? ");
                for (int j = i; j < i+sqrt-1 && j < M-1; j++) {
                    stringBuilder.append(j).append(' ');
                }
                stringBuilder.append(Math.min(i+sqrt-1,M-1));
                System.out.println(stringBuilder);
                System.out.flush();
                if (sc.nextInt() == 1){
                    for (int j = i; j < i+sqrt && j < M; j++) {
                        System.out.println("? "+j);
                        System.out.flush();
                        if (sc.nextInt() == 1){
                            System.out.println("! " + j);
                            System.out.flush();
                            return;
                        }
                    }
                }
            }
        }
        else
            System.out.println(find(0, M-1));
        System.out.flush();
    }

    private static String find(int lo, int hi) {
        if (lo >= hi){
            return "! " + lo;
        } else if (N >= hi-lo+S-1){
            for (int i = lo; i < hi; i++){
                System.out.println("? "+i);
                System.out.flush();
                if (sc.nextInt() == 1){
                    return "! " + i;
                }
            }
            return "! " + hi;
        }
        StringBuilder stringBuilder = new StringBuilder("? ");
        for (int i = lo; i < (lo+hi)/2; i++) {
            stringBuilder.append(i).append(' ');
        }
        stringBuilder.append((lo+hi)/2);
        System.out.println(stringBuilder);
        System.out.flush();
        if (sc.nextInt() == 1) {
            N -= S;
            return find(lo, (lo + hi) / 2);
        }
        else{
            N--;
            return find((lo+hi)/2+1,hi);
        }
    }
}
