import java.util.Scanner;
public class SpeedLimit
{
    
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int time = 0;
            int dist = 0;
            int n = sc.nextInt();
            if (n == -1) break;
            for (int i = 0; i < n; i++){
                int s = sc.nextInt();
                int t = sc.nextInt();
                dist += s*(t-time);
                time = t;
            }
            System.out.println(dist + " miles");
        }
    }

}
