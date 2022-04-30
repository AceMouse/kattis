import java.time.LocalTime;
import java.util.Scanner;

public class FlightsBasic {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        int n = reader.nextInt(), m = reader.nextInt();
        RedBlackBST<String, String> departures = new RedBlackBST<>();
        for (int i = 0; i < n; i++)
            departures.put(reader.next(), reader.next());
        String s;
        for (int i = 0; i < m; i++) {
            String c = reader.next();
            if (c.equals("cancel")) //cancel s
                departures.delete(reader.next());
            else if (c.equals("delay")) { //delay s d
                String temp = departures.get(s = reader.next());
                departures.delete(s);
                s = LocalTime.parse(s).plusSeconds(reader.nextInt()).toString();
                departures.put(s + (s.length() == 5 ? ":00" : ""), temp);
            }
            else if (c.equals("reroute")) //reroute s c
                departures.put(reader.next(), reader.next());
            else if (c.equals("destination")) { //destination s
                s = departures.get(reader.next());
                if (s != null)
                    System.out.println(s);
                else
                    System.out.println('-');
            }
            else if (c.equals("next")) { //next s
                s = departures.ceiling(reader.next());
                System.out.println(s + " " + departures.get(s));
            }
            else if (c.equals("count")) { //count s t
                System.out.println(departures.size(reader.next(), reader.next()));
            }
        }
    }
}