import java.util.Scanner;

public class CPRNummer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] cpr = sc.nextLine().replace("-","").toCharArray();
        int sum = ((cpr[0]-'0')*4);
        sum += ((cpr[1]-'0')*3);
        sum += ((cpr[2]-'0')*2);
        sum += ((cpr[3]-'0')*7);
        sum += ((cpr[4]-'0')*6);
        sum += ((cpr[5]-'0')*5);
        sum += ((cpr[6]-'0')*4);
        sum += ((cpr[7]-'0')*3);
        sum += ((cpr[8]-'0')*2);
        sum += ((cpr[9]-'0')*1);
        System.out.println(sum%11==0?'1':'0');
    }
}
