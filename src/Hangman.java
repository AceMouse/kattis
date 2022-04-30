import java.util.Scanner;
import java.util.ArrayList;
public class Hangman
{
    
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        String word = sc.next();
        char[] arr = sc.next().toCharArray();
        int count = 0;
        for (int i = 0; i < arr.length ; i++){
            if (word.contains(arr[i]+"")){
                word = word.replace(arr[i]+"","");
                if (word.length() == 0){
                    System.out.println("WIN");
                    break;
                }
            }
            else{
                count++;
                if (count == 10){
                    System.out.println("LOSE");
                    break;
                }
            } 
        }
        
    }
    

}
