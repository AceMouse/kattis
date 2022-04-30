import java.util.Scanner;

public class Pot {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt(), total = 0;
        while (N-->0){
            int m = scanner.nextInt(), pow = m%10, num = m/10;
            total += Math.pow(num, pow);
        }
        System.out.println(total);
    }
}
