import java.util.Scanner;
import java.util.HashMap;

public class HayPoints
{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int n = sc.nextInt();
        //System.out.println(m + " " + n);
        HashMap<String, Integer> HayPoints = new HashMap<>();
        for (int i = 0; i<m; i++){
            HayPoints.put(sc.next(), sc.nextInt());
        }
        // for (String key : HayPoints.keySet()){
            // System.out.println(key + ": " + HayPoints.get(key));
        // }
        String word;
        int points;
        for (int i = 0; i<n; i++){
            word = "";
            points = 0;
            while (!word.equals(".")){
                word = sc.next();
                //System.out.println(word);
                if (HayPoints.get(word) != null) {
                    points += HayPoints.get(word);
                    //System.out.println(points + "...");
                }
            }
            System.out.println(points);
        }
    }
}
