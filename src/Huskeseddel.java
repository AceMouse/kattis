import java.util.*;
public class Huskeseddel {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String pinCode = sc.next();
        int rev = Integer.parseInt(pinCode);
        rev++;
        String[] padding = {"", "000", "00", "0", ""};
        StringBuilder sb = new StringBuilder(String.valueOf(rev));
        String reverse = sb.reverse().toString();
        reverse += padding[reverse.length()];
        System.out.println(reverse);
    }
}

