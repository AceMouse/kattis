import java.util.Scanner;

public class FroshWeekBubbleSort {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        int swaps = 0;
        for (int j = 0; j < n; j++) {
            for (int i = 1; i < n; i++) {
                if (arr[i-1] > arr[i]){
                    int tmp = arr[i-1];
                    arr[i-1] = arr[i];
                    arr[i] = tmp;
                    swaps++;
                }
            }
        }

        /*for (int i = 0; i < n; i++) {
            System.out.print(arr[i]);
            System.out.print(' ');
        }
        System.out.println();*/
        System.out.println(swaps);
    }
}
