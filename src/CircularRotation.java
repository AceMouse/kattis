public class CircularRotation {
    public static void main(String[] args) {
        char[] A = args[0].toCharArray();
        char[] B = args[1].toCharArray();
        if (A.length != B.length) {System.out.println("not a circular rotation"); return;}
        boolean flag;
        for (int i = 0; i<A.length; i++){
            flag = true;
            for (int j = 0; j<B.length; j++){
                if (A[j] != B[(i + j) % A.length]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {System.out.println("a circular rotation"); return;}
        }
        System.out.println("not a circular rotation");
    }
}
