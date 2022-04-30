import java.util.Arrays;
import java.util.Scanner;

public class CrawlingQBertGolf {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        int[] currentBottomLayerCosts = new int[reader.nextInt()+1]; //pyramidHeight+1 = platforms on bottom layer
        for (int platformsInLayer = 1, upperRightPlatformCost = currentBottomLayerCosts[0]; platformsInLayer < currentBottomLayerCosts.length; platformsInLayer++, upperRightPlatformCost = currentBottomLayerCosts[0]){
            currentBottomLayerCosts[0] += reader.nextInt();
            for (int platform = 1; platform < platformsInLayer; platform++)
                currentBottomLayerCosts[platform] = Math.min(upperRightPlatformCost+reader.nextInt(), (upperRightPlatformCost = currentBottomLayerCosts[platform])+reader.nextInt());
            currentBottomLayerCosts[platformsInLayer] = upperRightPlatformCost + reader.nextInt();
        }
        System.out.println(Arrays.stream(currentBottomLayerCosts).min().getAsInt());
    }
}
