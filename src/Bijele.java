import java.util.Scanner;
public class Bijele
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int[] input = new int[]{1, 1, 2, 2, 2, 8};
        for (int i = 0; i < 6; i++){
            System.out.print((input[i] - sc.nextInt()) + " ");
        }
        
    }

}
