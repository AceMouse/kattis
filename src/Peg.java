import java.util.Scanner;
public class Peg
{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String[][] board = new String[7][7];
        int legalMoves = 0;
        for (int i = 0; i<7; i++){
            String[] row = sc.nextLine().split("",-1);
            for (int j = 0; j<7; j++){
                board[i][j] = row[j];
                if (board[i][j].equals("o")){
                    if (i>=2 && board[i-1][j].equals("o") && board[i-2][j].equals(".")) {
                        legalMoves++;
                        // System.out.println(i + ", " + j + " to " + (i-2) + ", " + j);
                    }
                    if (j>=2 && board[i][j-1].equals("o") && board[i][j-2].equals(".")) {
                        legalMoves++;
                        // System.out.println(i + ", " + j + " to " + i + ", " + (j-2));
                    }
                }
                else if (board[i][j].equals(".")){
                    if (i>=2 && board[i-1][j].equals("o") && board[i-2][j].equals("o")) {
                        legalMoves++;
                        // System.out.println(i + ", " + j + " to " + (i-2) + ", " + j);
                    }
                    if (j>=2 && board[i][j-1].equals("o") && board[i][j-2].equals("o")) {
                        legalMoves++;
                        //System.out.println(i + ", " + j + " to " + i + ", " + (j-2));
                    }
                }
            }
        }
        
        // for (int i = 0; i<7; i++){
            // for (int j = 0; j<7; j++){
                // if (board[i][j].equals("o")){
                    // if (i>=2 && board[i-1][j].equals("o") && board[i-2][j].equals(".")) legalMoves++;
                    // if (i<=4 && board[i+1][j].equals("o") && board[i+2][j].equals(".")) legalMoves++;
                    // if (j>=2 && board[i][j-1].equals("o") && board[i][j-2].equals(".")) legalMoves++;
                    // if (j<=4 && board[i][j+1].equals("o") && board[i][j+2].equals(".")) legalMoves++;
                // }
            // }
        // }
        System.out.println(legalMoves);
    }
}