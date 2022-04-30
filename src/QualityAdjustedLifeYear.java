import java.util.Locale;
import java.util.Scanner;

public class QualityAdjustedLifeYear {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);
        int N = scanner.nextInt();
        double accumulatedQALY = 0;
        while (N-->0){
            accumulatedQALY += scanner.nextDouble()*scanner.nextDouble();
        }
        System.out.println(accumulatedQALY);
    }
}
