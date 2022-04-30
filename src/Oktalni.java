import java.util.Scanner;
import java.util.HashMap;
public class Oktalni
{
    public static void main(String[] args){
        HashMap<String, Integer> numbers = new HashMap<>();
        numbers.put("000", 0);
        numbers.put("001", 1);
        numbers.put("010", 2);
        numbers.put("011", 3);
        numbers.put("100", 4);
        numbers.put("101", 5);
        numbers.put("110", 6);
        numbers.put("111", 7);
        Scanner sc = new Scanner(System.in);
        String binary = sc.next();
        if (binary.length()%3 == 1) binary = "00" + binary;
        else if (binary.length()%3 == 2) binary = "0" + binary;
        for (int i = 0; i<binary.length()/3; i++){
            int num = numbers.get(binary.substring(i*3,(i+1)*3));
            System.out.print(num);
        }
    }
}