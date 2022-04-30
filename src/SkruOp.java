import java.util.Scanner;

public class SkruOp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        int volume = 7;
        while(n-- > 0){
            if ("Skru op!".equals(sc.nextLine())){
                if (volume < 10)
                    volume++;
            }
            else {
                if (volume > 0)
                    volume--;
            }
        }
        System.out.println(volume);
    }
}
