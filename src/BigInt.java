public class BigInt {
    public byte[] digits;
    public int size;
    public boolean neg = false;
    public BigInt(String val, int precision){
        digits = new byte[precision];
        size = val.length();
        for (int i = 0; i < size; i++) {
            digits[i] = (byte)(val.charAt(size-i-1)-'0');
        }

    }

    public BigInt(byte[] digits, int size){
        this.digits = digits;
        this.size = size;
    }

    public static BigInt add(BigInt a, BigInt b){
        var resDigits = new byte[Math.max(a.size, b.size)+1];
        int c = 0;
        for (int i = 0; i < Math.max(a.size, b.size); i++) {
            int whole = a.digits[i] + b.digits[i] + c;
            c = whole/10;
            int r = whole-c*10;
            resDigits[i] = (byte)r;
        }
        resDigits[Math.max(a.size, b.size)] = (byte)c;
        return new BigInt(resDigits, (c != 0)?Math.max(a.size, b.size)+1:Math.max(a.size, b.size));
    }

    public byte[] toBytes(){
        byte[] res = new byte[size];
        for (int i = 0; i < size; i++) {
            res[i] = (byte) ('0' + digits[size-i-1]);
        }
        return res;
    }

    public void add(BigInt other) {
        int c = 0;
        for (int i = 0; i < Math.max(size, other.size); i++) {
            int whole = digits[i] + other.digits[i] + c;
            c = whole/10;
            int r = whole-c*10;
            digits[i] = (byte)r;
        }
        digits[Math.max(size, other.size)] = (byte)c;
        size = (c != 0)?Math.max(size, other.size)+1:Math.max(size, other.size);
    }

    public BigInt sub(BigInt other){
        var res = subSmaller(other);
        if (compareTo(other) < 0)
            res.neg = true;
        return res;
    }

    private BigInt subSmaller(BigInt smaller){
        int s = size;
        var res = nineComp();
        res.add(smaller);
        return res.nineComp(s);
    }

    private BigInt nineComp(int s){
        byte[] nineComp = new byte[s+1];

        int ns = 0;
        for (int i = 0; i < size; i++) {
            if ((nineComp[i] = (byte) (9-digits[i])) != 0)
                ns = i;
        }
        for (int sz = size; sz < s; sz++) {
            nineComp[ns = sz] = 9;
        }

        return new BigInt(nineComp, ns+1);
    }

    private BigInt nineComp(){
        byte[] nineComp = new byte[size+1];
        int ns = 0;
        for (int i = 0; i < size; i++) {
            if ((nineComp[i] = (byte) (9-digits[i])) != 0)
                ns = i;
        }

        return new BigInt(nineComp, ns+1);
    }
    public int compareTo(BigInt other) {
        if (other.size < size)
            for (int i = size - 1; i >= other.size; i--) {
                if (digits[i] != 0)
                    return 1;
            }
        else
            for (int i = other.size - 1; i >= size; i--) {
                if (other.digits[i] != 0)
                    return -1;
            }
        for (int i = Math.min(size,other.size) -1; 0 <= i; i--) {
            if (digits[i] != other.digits[i])
                return digits[i] - other.digits[i];
        }
        return 0;
    }

    public BigInt mod(BigInt other) {
        var res = this;
        while (res.compareTo(other) >= 0){
            res = res.subSmaller(other);
        }
        return res;
    }
}
