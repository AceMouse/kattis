import java.util.ArrayList;
public class PrimeGen
{
    public static void main (String[] args){
        long startTime = System.nanoTime();
        ArrayList<Integer> primes = new ArrayList<>();
        for (int i = 3; i<1000000; i+=2){
            boolean prime = true;
            int size = primes.size();
            for (int j = 0; (j<size)&&(j*j<=i); j++){
                int p=primes.get(j);
                if (i%p == 0) prime = false;
            }
            if (prime) primes.add(i);
        };
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000 ;
        long startTime2 = System.nanoTime();
        ArrayList<Integer> primes2 = new ArrayList<>();
        for (int i = 3; i<1000000; i+=2){
            boolean prime = true;
            for (int j = 0; (j<primes2.size())&&(j*j<=i); j++){
                int p=primes2.get(j);
                if (i%p == 0) prime = false;
            }
            if (prime) primes2.add(i);
        };
        long endTime2 = System.nanoTime();
        long duration2 = (endTime2 - startTime2)/1000000 ;
        System.out.println(duration);
        System.out.println(duration2);
        //for(int i = 1; i< 100; i++) System.out.println(primes.get(primes.size()-i));
    }
    
    public static void SieveOfEratosthenes(){
        boolean[] prime = new boolean[1000001];
        for (int i = 2; i<prime.length; i++) prime[i] = true;
        for (int p = 2; p*p<=1000000;p++){
            if (prime[p]){
                for (int i=p*p;i<=1000000; i+=p){
                    prime[i] = false;
                }
            }
        }
        for (int i = 0; i<100; i++) if (prime[i]) System.out.println(i);
    
    }
}
