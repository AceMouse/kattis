import java.util.Scanner;

public class StuckInATimeLoop {
    public static void main(String[] args) {
        int N = new Scanner(System.in).nextInt()+1;
        for (int i = 1; i < N; i++) {
            System.out.println(i + " Abracadabra");
        }
    }
}
