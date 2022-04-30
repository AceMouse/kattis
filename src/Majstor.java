import java.util.Scanner;
public class Majstor
{
    
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int R = sc.nextInt();
        String[] sven = sc.next().split("",-1);
        int N = sc.nextInt();
        String[][] f = new String[N][]; 
        for (int i = 0; i<N; i++){
            f[i] = sc.next().split("",-1);
        } 
        int actual = 0;
        
        for (int i = 0; i<R; i++){
            String s = sven[i];
            for (int j = 0; j<N; j++){
                if (s.equals(f[j][i])) {
                    actual += 1;
                }
                else if ((s.equals("R") && f[j][i].equals("S")) || (s.equals("S") && f[j][i].equals("P")||(s.equals("P") && f[j][i].equals("R")))){
                    actual += 2;
                }
            } 
        }
        int potential = 0;
        for (int i = 0; i<R; i++){
            int r = 0;
            int s = 0;
            int p = 0;
            for (int j = 0; j<N; j++){
                if ("R".equals(f[j][i])) {
                    r += 1;
                    p += 2;
                }
                else if ("S".equals(f[j][i])) {
                    s += 1;
                    r += 2;
                }
                else if ("P".equals(f[j][i])) {
                    p += 1;
                    s += 2;
                }
            } 
            if (r >= s && r >= p) potential += r;
            else if (s >= r && s >= p) potential += s;
            else if (p >= s && p >= r) potential += p;
        }
        System.out.println(actual);
        System.out.println(potential);
    }

}
