import java.util.Scanner;
public class Tarifa {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int X = scanner.nextInt(), N = scanner.nextInt(), max = N*X+X;
        while(N-->0) max -= scanner.nextInt();
        System.out.println(max);
    }
}
