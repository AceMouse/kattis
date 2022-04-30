import java.util.Scanner;

public class E190820 {
    public static int[][] world;
    public static boolean[][] marks;
    private static int R,C;
    private static boolean noWater;

    public static void main(String[] args) {
        readInput();

        for (int i = 0; i < R*C; i++) {
            if (grassIsConnected()) {
                System.out.println(i);
                return;
            } else {
                lowerWater();
            }
            if (noWater)
                break;
        }
        System.out.println("impossible");
    }

    private static void readInput() {
        Scanner sc = new Scanner(System.in);
        R = sc.nextInt();
        C = sc.nextInt();
        sc.nextLine();
        world = new int[R][];
        for (int i = 0; i < R; i++) {
            String line = sc.nextLine();
            int[] aLine = new int[C];
            for (int j = 0; j < C; j++) {
                aLine[j] = switch (line.charAt(j)){
                    case 'G' -> 0;
                    case 'L' -> Integer.MAX_VALUE;
                    default -> Integer.parseInt(String.valueOf(line.charAt(j)));
                };
            }
            world[i] = aLine;
        }
    }

    private static void lowerWater() {
        noWater = true;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (world[i][j] > 0) {
                    world[i][j]--;
                    if (world[i][j] < R*C){
                        noWater = false;
                    }
                }
            }
        }
    }

    private static boolean grassIsConnected() {
        marks = new boolean[R][C];
        boolean doneMarking = false;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (world[i][j] == 0) {
                    if (doneMarking && !marks[i][j]){
                        return false;
                    } else {
                        markGrass(i, j);
                        doneMarking = true;
                    }
                }
            }
        }
        return true;
    }

    private static void markGrass(int vi, int vj) {
        if (world[vi][vj] == 0 && !marks[vi][vj]){
            marks[vi][vj] = true;
            if (vi > 0)
                markGrass(vi-1,vj);
            if (vj > 0)
                markGrass(vi,vj-1);
            if (vi < R-1)
                markGrass(vi+1,vj);
            if (vj < C-1)
                markGrass(vi,vj+1);
            if (vi > 0 && vj < C-1)
                markGrass(vi-1,vj+1);
            if (vj < C-1 && vi < R-1)
                markGrass(vi+1,vj+1);
        }
    }
}
