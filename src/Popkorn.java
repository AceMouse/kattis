import java.math.BigInteger;
import java.util.Scanner;

public class Popkorn {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BigInteger contestants = sc.nextBigInteger();
        BigInteger groupSize = contestants.divide(new BigInteger("4"));//contestants>>2;
        BigInteger popcornPrGroup = groupSize.multiply(groupSize.subtract(new BigInteger("1"))).divide(new BigInteger("2"));//(groupSize*(groupSize-1))>>1;
        BigInteger total = popcornPrGroup.multiply(new BigInteger("4")).add(new BigInteger("4"));//(popcornPrGroup<<2)+4;
        System.out.println(total);
    }
}
