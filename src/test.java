import java.util.*;
public class test {
    public static void main(String[] args) {
        // Hjælp til Anton, det er hans kode. Har for at hjælpe modificeret den og vil nu tjekke om den løser test 1
        // DET ER IKKE UDELUKKENDE MIN KODE!
        Scanner sc = new Scanner(System.in);

        // Første linje indeholder en liste over de arter
        // behøves ikke gemmes til første testcase
        sc.nextLine();

        // Anden linje indeholer det samlede antal dyrearter på gården. (Laver en array)
        String[] linjer = new String[sc.nextInt()];
        //hopper til næste linje efter tallet
        sc.nextLine();
        // læser alle linjerne i inputtet
        for (int i = 0; i < linjer.length; i++) {
            // samiter input for tegnsætning
            linjer[i] = sc.nextLine().replaceAll("[,.!]", "");
        }

        // Tæller som tæller antal grise og høne "dominerede" linjer indtil videre
        int griseIAlt = 0;
        int hønerIAlt = 0;
        // skal læses som "for String linje i linjer"
        for (String linje : linjer) {
            // Tæller som tæller antal gange der bliver nævt gris og høne
            int griseILinje = 0;
            int hønerILinje = 0;
            // del linjen på " " får at få ordene, tjek om de er gris eller høne.
            // skal læses som "for ord i linje.split(" ")"
            for (String ord : linje.split(" ")) {
                // tjek om ordet er gris (med stort eller lille g) eller høne (med stort eller lille h)
                if (ord.equals("gris") || ord.equals("Gris")) {
                    griseILinje++;
                } else if (ord.equals("høne") || ord.equals("Høne")) {
                    hønerILinje++;
                }
            }
            // tilføj en gris eller en høne i alt alt efter hvad der var mest af i linjen
            if (griseILinje > hønerILinje){
                griseIAlt++;
            } else {
                hønerIAlt++;
            }
        }

        // Printer gris hvis "gris" optræder mere end "høne" ellers print høne
        if (griseIAlt > hønerIAlt) {
            System.out.println("gris");
        } else {
            System.out.println("høne");
        }
    }
}