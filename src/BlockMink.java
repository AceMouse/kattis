import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class BlockMink {
    static final HashSet<Long> powersOfTwo = new HashSet<>(Arrays.asList(1L << 1, 1L << 2, 1L << 3, 1L << 4, 1L << 5, 1L << 6, 1L << 7, 1L << 8, 1L << 9, 1L << 10, 1L << 11, 1L << 12, 1L << 13, 1L << 14, 1L << 15, 1L << 16, 1L << 17, 1L << 18, 1L << 19, 1L << 20, 1L << 21, 1L << 22, 1L << 23, 1L << 24, 1L << 25, 1L << 26, 1L << 27, 1L << 28, 1L << 29, 1L << 30, 1L << 31, 1L << 32, 1L << 33, 1L << 34, 1L << 35, 1L << 36, 1L << 37, 1L << 38, 1L << 39, 1L << 40, 1L << 41, 1L << 42, 1L << 43, 1L << 44, 1L << 45, 1L << 46, 1L << 47, 1L << 48, 1L << 49, 1L << 50, 1L << 51, 1L << 52, 1L << 53, 1L << 54, 1L << 55, 1L << 56, 1L << 57, 1L << 58, 1L << 59, 1L << 60, 1L << 61, 1L << 62, 1L << 63));
    public static void main(String[] args) throws IOException {
        solve();
    }
    public static void solve() throws IOException {
        Reader reader = new Reader();
        Writer writer = new Writer();

        int h= reader.nextInt();
        int b= reader.nextInt();
        int d= reader.nextInt();
        byte[][] area = new byte[h][];
        for (int i = 0; i < h; i++) {
            area[i] = reader.readLine();
        }

        short[][] blockMap = new short[h][b];
        short mink = 0;
        Block[] blocks = new Block[202];
        Block[] positiveBlocks = new Block[202];
        Block[] negativeBlocks = new Block[202];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < b; j++) {
                if (area[i][j] == '<'){
                    mink++;
                    blocks[mink] = Block.colorBlock(i, j, mink, blockMap, blocks, d);
                    if (blocks[mink].cost > 0)
                        positiveBlocks[mink] = blocks[mink];
                    else
                        negativeBlocks[mink] = blocks[mink];
                    j += 2;
                }
            }
        }
        int maxSum;
        int removeAllSum = 0;
        HashSet<Integer> allBlocks = new HashSet<>();
        for (Block block : blocks) {
            if (block != null) {
                removeAllSum += block.cost;
                allBlocks.add(block.nr);
            }
        }
        HashSet<Integer> freePositiveBlocks = new HashSet<>();
        int freePositiveBlocksSum = 0;
        for (Block block : positiveBlocks) {
            if (block != null && (block.rightChild == 0 || freePositiveBlocks.contains(block.leftChild)) && (block.leftChild == 0 || freePositiveBlocks.contains(block.rightChild))) {
                freePositiveBlocksSum += block.cost;
                freePositiveBlocks.add(block.nr);
            }
        }
        HashSet<Integer> toRemove = new HashSet<>(freePositiveBlocks);
        maxSum = freePositiveBlocksSum;
        UF uf = new UF(mink+1);
        for (int i = 1; i <= mink; i++){
            Block block = blocks[i];
            if (block == null)
                continue;
            if (block.leftChild != 0)
                uf.union(i, block.leftChild);
            if (block.rightChild != 0)
                uf.union(i, block.rightChild);
        }

        int[] sets = uf.sets();
        for (int i = 1; i < sets.length; i++) {
            int currentSet = sets[i];
            LinkedList<Block> notFreeBlocksInSet = new LinkedList<>();
            int sumOfNotFreeBlocksInSet = 0;
            for (int j = 1; j <= mink; j++) {
                if (uf.find(j) == currentSet) {
                    if (!freePositiveBlocks.contains(blocks[j].nr)) {
                        sumOfNotFreeBlocksInSet += blocks[j].cost;
                        notFreeBlocksInSet.add(blocks[j]);
                    }
                }
            }
            var it = notFreeBlocksInSet.descendingIterator();
            while (it.hasNext()) {
                Block block = it.next();
                if (block.rightParent + block.leftParent != 0 || block.cost > 0)
                    continue;
                it.remove();
                sumOfNotFreeBlocksInSet -= block.cost;
                if (block.leftChild != 0)
                    blocks[block.leftChild].rightParent = 0;
                if (block.rightChild != 0)
                    blocks[block.rightChild].leftParent = 0;
            }
            int sizeOfSet = notFreeBlocksInSet.size();
            Block[] notFreeBlocksInSetArr = new Block[sizeOfSet];
            notFreeBlocksInSet.toArray(notFreeBlocksInSetArr);
            int maxSize = 5;
            if (1 < sizeOfSet && sizeOfSet < maxSize){
                var descendantsOfBlockIInSet = new HashSet[sizeOfSet];
                int cnt = 0;
                for (Block block : notFreeBlocksInSet) {
                    descendantsOfBlockIInSet[cnt++] = block.descendants(blocks);
                }
                // idea from here: https://math.stackexchange.com/a/240770
                int combs = (int) Math.pow(2, sizeOfSet);
                combloop:
                for (int j = 1; j < combs; j++) {
                    if (powersOfTwo.contains((long)j))
                        continue;
                    ArrayList<HashSet<Integer>> totalDescendants= new ArrayList<>();
                    var blocksInComb = new ArrayList<Integer>();
                    for (int k = 0; k < maxSize; k++) {
                        if ((j & 1L<<k) != 0){
                            for(HashSet<Integer> set : totalDescendants)
                                if (set.contains(k))
                                    continue combloop;
                            if (freePositiveBlocks.contains(notFreeBlocksInSetArr[k].nr))
                                continue combloop;
                            totalDescendants.add(descendantsOfBlockIInSet[k]);
                            blocksInComb.add(notFreeBlocksInSetArr[k].nr);
                        }
                    }
                    var used = new HashSet<Integer>();
                    int sumOfBestComp = 0;
                    for(HashSet<Integer> set : totalDescendants){
                        for(int e : set){
                            if (used.contains(e))
                                continue;
                            used.add(e);
                            sumOfBestComp += blocks[e].cost;
                        }
                    }
                    for (int e : blocksInComb) {
                        Block block = blocks[e];
                        sumOfBestComp += block.cost;
                        used.add(block.nr);
                    }

                    if (sumOfBestComp > 0){
                        toRemove.addAll(used);
                        maxSum += sumOfBestComp;
                    }
                }
            } else {
                HashSet<Integer> blocksInBestSubSet = new HashSet<>();
                for (Block block : notFreeBlocksInSet) {
                    blocksInBestSubSet.add(block.nr);
                }
                int sumOfNotFreeBlocksInBestSubSet = sumOfNotFreeBlocksInSet;
                for (Block block : notFreeBlocksInSet) {
                    int sumOfNotFreeBlocksInSubSet = block.cost;
                    var descendants = block.descendants(blocks);
                    for (int k : descendants) {
                        if (!freePositiveBlocks.contains(k))
                            sumOfNotFreeBlocksInSubSet += blocks[k].cost;
                    }
                    if (sumOfNotFreeBlocksInSubSet >= sumOfNotFreeBlocksInBestSubSet) {
                        blocksInBestSubSet = new HashSet<>();
                        for (int descendant : descendants) {
                            if (!freePositiveBlocks.contains(descendant))
                                blocksInBestSubSet.add(descendant);
                        }
                        blocksInBestSubSet.add(block.nr);
                        sumOfNotFreeBlocksInBestSubSet = sumOfNotFreeBlocksInSubSet;
                    }
                }
                if (sumOfNotFreeBlocksInBestSubSet > 0) {
                    maxSum += sumOfNotFreeBlocksInBestSubSet;
                    toRemove.addAll(blocksInBestSubSet);
                }
            }

        }
        if (removeAllSum > 0 || maxSum > 0)
            writeArea(area, blockMap, writer, removeAllSum > maxSum?allBlocks:toRemove);

    }

    private static void writeArea(byte[][] area, short[][] blockMap, Writer writer, HashSet<Integer> toRemove) throws IOException {
        for (int i = 0; i < area.length; i++) {
            for (int j = 0; j < area[0].length; j++) {
                if (toRemove.contains((int) blockMap[i][j]))
                    writer.write(' ');
                else
                    writer.write((char) area[i][j]);
            }
            writer.write('\n');
        }
        writer.flush();
    }

    private static byte[][] genArea(int h, int b, Random random) {
        byte[][] area = new byte[h][b];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < b; j++) {
                area[i][j] = '#';
            }
        }
        int mink = 0;
        for (int i = 1; i < h-1; i++) {
            for (int j = 1; j < b-3; j++) {
                if (area[i][j] == '#' && mink <= 200 && random.nextInt(40) < 1) {
                    area[i][j] = '<';
                    area[i][++j] = '=';
                    area[i][++j] = '>';
                    mink++;
                }
            }
        }
        if (mink == 0){
            area[1][1] = '<';
            area[1][2] = '=';
            area[1][3] = '>';
        }
        return area;
    }

    static class Block{
        final int nr;
        final int cost;
        final int leftChild;
        final int rightChild;
        int leftParent;
        int rightParent;
        private Block(int cost, int leftChild, int rightChild, int nr){
            this.cost = cost;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.nr = nr;
        }
        public HashSet<Integer> descendants(Block[] blocks){
            HashSet<Integer> set = new HashSet<>();
            descendants(blocks, set);
            return set;
        }
        private void descendants(Block[] blocks, HashSet<Integer> set){
            if (leftChild != 0 && !set.contains(leftChild)) {
                set.add(leftChild);
                blocks[leftChild].descendants(blocks, set);
            }
            if (rightChild != 0 && !set.contains(rightChild)) {
                set.add(rightChild);
                blocks[rightChild].descendants(blocks, set);
            }
        }
        public static Block colorBlock(int i, int j, short mink, short[][] blockMap, Block[] blocks, int d) {
            int costOfBlock = d;
            blockMap[i][j] = mink;
            blockMap[i][j+1] = mink;
            blockMap[i][j+2] = mink;
            int left = 0, right = 0;
            for (int k = i-1; k >= 0 ; k--) {
                if (blockMap[k][j] != 0) {
                    left = blockMap[k][j];
                    blocks[left].rightParent = mink;
                    break;
                }
                blockMap[k][j] = mink;
                costOfBlock--;
            }
            for (int k = i-1; k >= 0 ; k--) {
                if (blockMap[k][j+1] != 0) {
                    break;
                }
                blockMap[k][j+1] = mink;
                costOfBlock--;
            }
            for (int k = i-1; k >= 0 ; k--) {
                if (blockMap[k][j+2] != 0) {
                    right = blockMap[k][j+2];
                    blocks[right].leftParent = mink;
                    break;
                }
                blockMap[k][j+2] = mink;
                costOfBlock--;
            }
            return new Block(costOfBlock, left, right, mink);
        }
    }

    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public boolean hasNext() throws IOException {
            if (bufferPointer == bytesRead) fillBuffer();
            return buffer[bufferPointer] != -1;
        }

        public String next() throws IOException {
            byte[] buf = new byte[80]; // line length
            int cnt = 0, c;
            while ((c = read()) == ' ') ;
            do {
                if (c == ' ' || c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            } while ((c = read()) != -1);
            return new String(buf, 0, cnt);
        }

        public byte[] readLine() throws IOException {
            byte[] buf = new byte[202]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return Arrays.copyOf(buf, cnt);
        }


        public void skip(int x) {
            bufferPointer += x;
        }

        public void skipTil(char c) throws IOException {
            while (read() != c) ;
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }

    static class Writer {
        private final BufferedOutputStream outputStream = new BufferedOutputStream(System.out);
        static final byte[] DigitTens = {
                '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
                '1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
                '2', '2', '2', '2', '2', '2', '2', '2', '2', '2',
                '3', '3', '3', '3', '3', '3', '3', '3', '3', '3',
                '4', '4', '4', '4', '4', '4', '4', '4', '4', '4',
                '5', '5', '5', '5', '5', '5', '5', '5', '5', '5',
                '6', '6', '6', '6', '6', '6', '6', '6', '6', '6',
                '7', '7', '7', '7', '7', '7', '7', '7', '7', '7',
                '8', '8', '8', '8', '8', '8', '8', '8', '8', '8',
                '9', '9', '9', '9', '9', '9', '9', '9', '9', '9',
        };

        static final byte[] DigitOnes = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        };

        private static byte[] getBytes(int i) { // copied and modified from Java Integer class
            boolean neg = i < 0;
            if (!neg)
                i = -i;
            int charPos = 0, p = -10;
            for (int j = 1; j < 10; j++) {
                if (i > p) {
                    charPos = j;
                    break;
                }
                p = 10 * p;
            }
            if (charPos == 0)
                charPos = 10;
            if (neg)
                charPos++;
            byte[] buf = new byte[charPos];
            buf[0] = '-';
            int q, r;

            // Generate two digits per iteration
            while (i <= -100) {
                q = i / 100;
                r = (q * 100) - i;
                i = q;
                buf[--charPos] = DigitOnes[r];
                buf[--charPos] = DigitTens[r];
            }

            // We know there are at most two digits left at this point.
            q = i / 10;
            r = (q * 10) - i;
            buf[--charPos] = (byte) ('0' + r);

            // Whatever left is the remaining digit.
            if (q < 0) {
                buf[--charPos] = (byte) ('0' - q);
            }
            return buf;
        }

        private static byte[] getBytes(long i) { // same as above but not able to handle negative numbers
            i = -i;
            int charPos = 0, d = 0, p = -10;
            for (int j = 1; j < 10; j++) {
                if (i > p) {
                    charPos = j + d;
                    break;
                }
                p = 10 * p;
            }
            if (charPos == 0)
                charPos = 10 + d;
            byte[] buf = new byte[charPos];
            int r;
            long q;

            // Generate two digits per iteration
            while (i <= -100) {
                q = i / 100;
                r = (int) ((q * 100) - i);
                i = q;
                buf[--charPos] = DigitOnes[r];
                buf[--charPos] = DigitTens[r];
            }

            // We know there are at most two digits left at this point.
            q = i / 10;
            r = (int) ((q * 10) - i);
            buf[--charPos] = (byte) ('0' + r);

            // Whatever left is the remaining digit.
            if (q < 0) {
                buf[--charPos] = (byte) ('0' - q);
            }
            return buf;
        }

        public void write(String s) throws IOException {
            outputStream.write(s.getBytes());
        }

        public void write(byte[] s) throws IOException {
            outputStream.write(s);
        }

        public void write(char c) throws IOException {
            outputStream.write(c);
        }

        public void write(int i) throws IOException {
            outputStream.write(getBytes(i));
        }

        public void write(long l) throws IOException {
            outputStream.write(getBytes(l));
        }

        public void write(short s) throws IOException { //to be improved
            outputStream.write(getBytes((int) s));
        }

        public void write(double d) throws IOException { //to be improved
            write(String.valueOf(d));
        }

        public void write(float f) throws IOException { //to be improved
            write(String.valueOf(f));
        }

        public void flush() throws IOException {
            outputStream.flush();
        }
    }
}

