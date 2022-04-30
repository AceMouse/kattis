import java.util.Scanner;

public class LastFactorialDigit {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        while(N-->0){
            System.out.println(fac(scanner.nextInt())%10);
        }
    }
    private static int fac(int a){
        int ret = a;
        while (a-->1){
            ret*=a;
        }
        return ret;
    }
}
