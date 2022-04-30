import java.util.Scanner;
public class DiceCup
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[] sums = new int[N+M];
        for (int i = 1; i<N; i++){
            for (int j = 1; j <M ; j++)
                sums[i+j]++;
        }
        int highest = 0;
        for (int i = 0; i< sums.length; i++){
            if (highest < sums[i])
                highest = sums[i];
        }
        for (int i = 0; i < sums.length; i++){
            if (highest == sums[i])
                System.out.println(i+1);
        }
    }
}
