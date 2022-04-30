import java.util.Scanner;
public class Okviri
{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String in = sc.nextLine();
        String[] out = new String[]{"", "", "", "", ""};
        int count = 0;
        for (char c: in.toCharArray()){
            if (count == 0){
                out[0] += "..#.";
                out[1] += ".#.#";
                out[2] += "#." + c + ".";
                out[3] += ".#.#";
                out[4] += "..#.";
            }
            else if (count%3 == 0){
                out[0] += "..#.";
                out[1] += ".#.#";
                out[2] += "*." + c + ".";
                out[3] += ".#.#";
                out[4] += "..#.";
            }
            else if (count%3 == 1){
                out[0] += "..#.";
                out[1] += ".#.#";
                out[2] += "#." + c + ".";
                out[3] += ".#.#";
                out[4] += "..#.";
            }
            else if (count%3 == 2){
                out[0] += "..*.";
                out[1] += ".*.*";
                out[2] += "*." + c + ".";
                out[3] += ".*.*";
                out[4] += "..*.";
            }
            count++;
        }
        if (count%3 != 0){
            out[0] += ".";
            out[1] += ".";
            out[2] += "#";
            out[3] += ".";
            out[4] += ".";
        }
        else {
            out[0] += ".";
            out[1] += ".";
            out[2] += "*";
            out[3] += ".";
            out[4] += ".";
        }
        for (int i = 0; i<5; i++){
            System.out.println(out[i]);
        }
    }
}
