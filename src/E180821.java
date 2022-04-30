import java.util.*;

public class E180821 {
    public static void main(String[] args) {
        HashMap<Character, Integer> charOccurrences = new HashMap<>();
        String s = "THEQUICKBROWNFOXJUMPSOVERTHELAZYDOG";
        for (Character c : s.toCharArray()) {
            charOccurrences.put(c, charOccurrences.getOrDefault(c,0) + 1);
        }
        ArrayList<Map.Entry<Character, Integer>> entries = new ArrayList<>(charOccurrences.entrySet());
        entries.sort((o1, o2) -> -(o1.getValue() - o2.getValue()));
        for (Map.Entry<Character,Integer> e : entries) {
            System.out.print(e.getKey());
        }
    }
}
