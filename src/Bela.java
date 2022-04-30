import java.util.Scanner;
import java.util.HashMap;
public class Bela
{
    public static void main(String[] args)
    {
        HashMap<String, Integer> dominant = new HashMap<>();
        dominant.put("A" , 11);
        dominant.put("K" , 4);
        dominant.put("Q" , 3);
        dominant.put("J" , 20);
        dominant.put("T" , 10);
        dominant.put("9" , 14);
        dominant.put("8" , 0);
        dominant.put("7" , 0);
        
        HashMap<String, Integer> notDominant = new HashMap<>();
        notDominant.put("A" , 11);
        notDominant.put("K" , 4);
        notDominant.put("Q" , 3);
        notDominant.put("J" , 2);
        notDominant.put("T" , 10);
        notDominant.put("9" , 0);
        notDominant.put("8" , 0);
        notDominant.put("7" , 0);
        
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        String B = sc.next();
        int total = 0;
        for (int i = 0; i<N*4; i++){
            String[] l= sc.next().split("",-1);
            if (B.equals(l[1]))
                total += dominant.get(l[0]);
            else 
                total += notDominant.get(l[0]);
        }
        System.out.println(total);
    }
}
