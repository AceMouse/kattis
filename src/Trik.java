import java.util.Scanner;
public class Trik
{
    
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        String[] moves = sc.next().split("", -1);
        sc.close();
        int[] cups = {1, 0, 0};
        int temp;
        for (int i = 0; i < moves.length; i++){
            if (moves[i].equals("A")) {
                temp = cups[0];
                cups[0] = cups[1];
                cups[1] = temp;
            }
            else if (moves[i].equals("B")) {
                temp = cups[1];
                cups[1] = cups[2];
                cups[2] = temp;
            }
            else if (moves[i].equals("C")) {
                temp = cups[0];
                cups[0] = cups[2];
                cups[2] = temp;
            }
        }
        System.out.println(cups[0]*1+cups[1]*2+cups[2]*3);
    }
    // public static void main(String[] args)
    // {
        // Scanner sc = new Scanner(System.in);
        // String[] moves = sc.next().split("", -1);
        // int[] cups = {1, 0, 0};
        // for (int i = 0; i < moves.length; i++){
            // if (moves[i].equals("A")) swap(0, 1, cups);
            // else if (moves[i].equals("B")) swap(1, 2, cups);
            // else if (moves[i].equals("C")) swap(0, 2, cups);
        // }
        // System.out.println(cups[0]*1+cups[1]*2+cups[2]*3);
    // }
    
    // public static void swap(int a, int b, int[] cups){
        // int temp = cups[a];
        // cups[a] = cups[b];
        // cups[b] = temp;
    // }
    
    // public static void main(String[] args)
    // {
        // Scanner sc = new Scanner(System.in);
        // String[] moves = sc.next().split("", -1);
        // int[] cups = {1, 0, 0};
        // for (int i = 0; i < moves.length; i++){
            // if (moves[i].equals("A")) cups = swap(0, 1, cups);
            // else if (moves[i].equals("B")) cups = swap(1, 2, cups);
            // else if (moves[i].equals("C")) cups = swap(0, 2, cups);
        // }
        // System.out.println(cups[0]*1+cups[1]*2+cups[2]*3);
    // }
    
    // public static int[] swap(int a, int b, int[] arr){
        // int[] arr2 = new int[arr.length];
        // for (int i = 0 ; i< arr.length; i++){
            // arr2[i] = arr[i];
        // }
        // arr2[a] = arr[b];
        // arr2[b] = arr[a];
        // return arr2;
    // }
    
}
