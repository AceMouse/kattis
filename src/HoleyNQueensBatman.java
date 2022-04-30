import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class HoleyNQueensBatman {
    private static int[] cnt = new int[13];
    private static long[][] noHolesSolutions = new long[][]{null,null,null,null,new long[10], new long[50], new long[20], new long[200], new long[460], new long[1760], new long[3620], new long[13400], new long[71000]};//https://en.wikipedia.org/wiki/Eight_queens_puzzle#Exact_enumeration
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        Writer writer = new Writer();
        searchDown12(0, 0, 0, 0, new long[5]);
        
        searchDown11(0, 0, 0, 0, new long[5]);
        
        searchDown10(0, 0, 0, 0, new long[5]);
        
        searchDown9(0, 0, 0, 0, new long[5]);
        
        searchDown8(0, 0, 0, 0, new long[5]);
        
        searchDown7(0, 0, 0, 0, new long[5]);
        
        searchDown6(0, 0, 0, 0, new long[5]);
        
        searchDown5(0, 0, 0, 0, new long[5]);
        
        searchDown4(0, 0, 0, 0, new long[5]);
        
        
        int n,m;
        while ((n = reader.nextUInt()) != 0 | (m = reader.nextUInt()) != 0) {
            if (n < 4){
                writer.write(n == 1?1-m:0);
                writer.write('\n');
                continue;
            }
            if (m == 0){
                writer.write(noHolesSolutions[n].length/5);
                writer.write('\n');
                continue;
            }

            long[] holes = new long[5];
            int pos;
            for (int i = 0; i < m; i++) {
                holes[(pos = reader.nextUInt() + reader.nextUInt()*n)>>>5] ^= 1L << (pos& 31);
            }
            int cnt = 0;
            for (int i = 0; i < noHolesSolutions[n].length; i+=5) {
                cnt += (noHolesSolutions[n][i]&holes[0] | noHolesSolutions[n][i+1]&holes[1] | noHolesSolutions[n][i+2]&holes[2] | noHolesSolutions[n][i+3]&holes[3] | noHolesSolutions[n][i+4]&holes[4]) > 0 ?0:1;
            }
            writer.write(cnt);
            writer.write('\n');
        }
        writer.flush();
    }

    private static void searchDown12(int y, int column, int diag1, int diag2, long[] positionsUsed) { // mostly taken from Competitive Programmer’s Handbook by Antti Laaksonen
        if (y == 12) {
            noHolesSolutions[12][cnt[12]++] = positionsUsed[0];
            noHolesSolutions[12][cnt[12]++] = positionsUsed[1];
            noHolesSolutions[12][cnt[12]++] = positionsUsed[2];
            noHolesSolutions[12][cnt[12]++] = positionsUsed[3];
            noHolesSolutions[12][cnt[12]++] = positionsUsed[4];
            return;
        }
        
        int d1Shift = 1 << y;
        int d2Shift = 1 << - y + 12 - 1;
        int pos;
        int y12 = y*12;

        if (!((column & 1) > 0 || (diag1 & d1Shift) > 0 || (diag2 & d2Shift) > 0)) {
            positionsUsed[(pos = y12)>>>5] ^= 1L << (pos& 31);
            searchDown12(y + 1, column | 1, diag1 | d1Shift, diag2 | d2Shift, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 2) > 0 || (diag1 & d1Shift << 1) > 0 || (diag2 & d2Shift << 1) > 0)) {
            positionsUsed[(pos = 1 + y12)>>>5] ^= 1L << (pos& 31);
            searchDown12(y+1, column | 2, diag1 | d1Shift << 1, diag2 | d2Shift << 1, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }

        if (!((column & 4) > 0 || (diag1 & d1Shift << 2) > 0 || (diag2 & d2Shift << 2) > 0 )) {
            positionsUsed[(pos = 2 + y12)>>>5] ^= 1L << (pos& 31);
            searchDown12(y+1, column | 4, diag1 | d1Shift << 2, diag2 | d2Shift << 2, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 8) > 0 || (diag1 & d1Shift << 3) > 0 || (diag2 & d2Shift << 3) > 0 )) {
            positionsUsed[(pos = 3 + y12)>>>5] ^= 1L << (pos& 31);
            searchDown12(y+1, column | 8, diag1 | d1Shift << 3, diag2 | d2Shift << 3, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 16) > 0 || (diag1 & d1Shift << 4) > 0 || (diag2 & d2Shift << 4) > 0 )) {
            positionsUsed[(pos = 4 + y12)>>>5] ^= 1L << (pos& 31);
            searchDown12(y+1, column | 16, diag1 | d1Shift << 4, diag2 | d2Shift << 4, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 32) > 0 || (diag1 & d1Shift << 5) > 0 || (diag2 & d2Shift << 5) > 0 )) {
            positionsUsed[(pos = 5 + y12)>>>5] ^= 1L << (pos& 31);
            searchDown12(y+1, column | 32, diag1 | d1Shift << 5, diag2 | d2Shift << 5, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 64) > 0 || (diag1 & d1Shift << 6) > 0 || (diag2 & d2Shift << 6) > 0 )) {
            positionsUsed[(pos = 6 + y12)>>>5] ^= 1L << (pos& 31);
            searchDown12(y+1, column | 64, diag1 | d1Shift << 6, diag2 | d2Shift << 6, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 128) > 0 || (diag1 & d1Shift << 7) > 0 || (diag2 & d2Shift << 7) > 0 )) {
            positionsUsed[(pos = 7 + y12)>>>5] ^= 1L << (pos& 31);
            searchDown12(y+1, column | 128, diag1 | d1Shift << 7, diag2 | d2Shift << 7, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 256) > 0 || (diag1 & d1Shift << 8) > 0 || (diag2 & d2Shift << 8) > 0 )) {
            positionsUsed[(pos = 8 + y12)>>>5] ^= 1L << (pos& 31);
            searchDown12(y+1, column | 256, diag1 | d1Shift << 8, diag2 | d2Shift << 8, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 512) > 0 || (diag1 & d1Shift << 9) > 0 || (diag2 & d2Shift << 9) > 0 )) {
            positionsUsed[(pos = 9 + y12)>>>5] ^= 1L << (pos& 31);
            searchDown12(y+1, column | 512, diag1 | d1Shift << 9, diag2 | d2Shift << 9, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 1024) > 0 || (diag1 & d1Shift << 10) > 0 || (diag2 & d2Shift << 10) > 0 )) {
            positionsUsed[(pos = 10 + y12)>>>5] ^= 1L << (pos& 31);
            searchDown12(y+1, column | 1024, diag1 | d1Shift << 10, diag2 | d2Shift << 10, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 2048) > 0 || (diag1 & d1Shift << 11) > 0 || (diag2 & d2Shift << 11) > 0 )) {
            positionsUsed[(pos = 11 + y12)>>>5] ^= 1L << (pos& 31);
            searchDown12(y+1, column | 2048, diag1 | d1Shift << 11, diag2 | d2Shift << 11, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
    }

    private static void searchDown11(int y, int column, int diag1, int diag2, long[] positionsUsed) { // mostly taken from Competitive Programmer’s Handbook by Antti Laaksonen
        if (y == 11) {
            noHolesSolutions[11][cnt[11]++] = positionsUsed[0];
            noHolesSolutions[11][cnt[11]++] = positionsUsed[1];
            noHolesSolutions[11][cnt[11]++] = positionsUsed[2];
            noHolesSolutions[11][cnt[11]++] = positionsUsed[3];
            noHolesSolutions[11][cnt[11]++] = positionsUsed[4];
            return;
        }
        
        int d1Shift = 1 << y;
        int d2Shift = 1 << - y + 11 - 1;
        int pos, y11 = y*11;

        if (!((column & 1) > 0 || (diag1 & d1Shift) > 0 || (diag2 & d2Shift) > 0)) {
            positionsUsed[(pos = y11)>>>5] ^= 1L << (pos& 31);
            searchDown11(y + 1, column | 1, diag1 | d1Shift, diag2 | d2Shift, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 2) > 0 || (diag1 & d1Shift << 1) > 0 || (diag2 & d2Shift << 1) > 0)) {
            positionsUsed[(pos = 1 + y11)>>>5] ^= 1L << (pos& 31);
            searchDown11(y+1, column | 2, diag1 | d1Shift << 1, diag2 | d2Shift << 1, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }

        if (!((column & 4) > 0 || (diag1 & d1Shift << 2) > 0 || (diag2 & d2Shift << 2) > 0 )) {
            positionsUsed[(pos = 2 + y11)>>>5] ^= 1L << (pos& 31);
            searchDown11(y+1, column | 4, diag1 | d1Shift << 2, diag2 | d2Shift << 2, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 8) > 0 || (diag1 & d1Shift << 3) > 0 || (diag2 & d2Shift << 3) > 0 )) {
            positionsUsed[(pos = 3 + y11)>>>5] ^= 1L << (pos& 31);
            searchDown11(y+1, column | 8, diag1 | d1Shift << 3, diag2 | d2Shift << 3, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 16) > 0 || (diag1 & d1Shift << 4) > 0 || (diag2 & d2Shift << 4) > 0 )) {
            positionsUsed[(pos = 4 + y11)>>>5] ^= 1L << (pos& 31);
            searchDown11(y+1, column | 16, diag1 | d1Shift << 4, diag2 | d2Shift << 4, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 32) > 0 || (diag1 & d1Shift << 5) > 0 || (diag2 & d2Shift << 5) > 0 )) {
            positionsUsed[(pos = 5 +y11)>>>5] ^= 1L << (pos& 31);
            searchDown11(y+1, column | 32, diag1 | d1Shift << 5, diag2 | d2Shift << 5, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 64) > 0 || (diag1 & d1Shift << 6) > 0 || (diag2 & d2Shift << 6) > 0 )) {
            positionsUsed[(pos = 6 + y11)>>>5] ^= 1L << (pos& 31);
            searchDown11(y+1, column | 64, diag1 | d1Shift << 6, diag2 | d2Shift << 6, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 128) > 0 || (diag1 & d1Shift << 7) > 0 || (diag2 & d2Shift << 7) > 0 )) {
            positionsUsed[(pos = 7 + y11)>>>5] ^= 1L << (pos& 31);
            searchDown11(y+1, column | 128, diag1 | d1Shift << 7, diag2 | d2Shift << 7, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 256) > 0 || (diag1 & d1Shift << 8) > 0 || (diag2 & d2Shift << 8) > 0 )) {
            positionsUsed[(pos = 8 + y11)>>>5] ^= 1L << (pos& 31);
            searchDown11(y+1, column | 256, diag1 | d1Shift << 8, diag2 | d2Shift << 8, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 512) > 0 || (diag1 & d1Shift << 9) > 0 || (diag2 & d2Shift << 9) > 0 )) {
            positionsUsed[(pos = 9 + y11)>>>5] ^= 1L << (pos& 31);
            searchDown11(y+1, column | 512, diag1 | d1Shift << 9, diag2 | d2Shift << 9, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 1024) > 0 || (diag1 & d1Shift << 10) > 0 || (diag2 & d2Shift << 10) > 0 )) {
            positionsUsed[(pos = 10 + y11)>>>5] ^= 1L << (pos& 31);
            searchDown11(y+1, column | 1024, diag1 | d1Shift << 10, diag2 | d2Shift << 10, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
    }
    private static void searchDown10(int y, int column, int diag1, int diag2, long[] positionsUsed) { // mostly taken from Competitive Programmer’s Handbook by Antti Laaksonen
        if (y == 10) {
            noHolesSolutions[10][cnt[10]++] = positionsUsed[0];
            noHolesSolutions[10][cnt[10]++] = positionsUsed[1];
            noHolesSolutions[10][cnt[10]++] = positionsUsed[2];
            noHolesSolutions[10][cnt[10]++] = positionsUsed[3];
            noHolesSolutions[10][cnt[10]++] = positionsUsed[4];
            return;
        }
        
        int d1Shift = 1 << y;
        int d2Shift = 1 << - y + 10 - 1;

        int pos, y10 = y*10;

        if (!((column & 1) > 0 || (diag1 & d1Shift) > 0 || (diag2 & d2Shift) > 0)) {
            positionsUsed[(pos = y10)>>>5] ^= 1L << (pos& 31);
            searchDown10(y + 1, column | 1, diag1 | d1Shift, diag2 | d2Shift, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 2) > 0 || (diag1 & d1Shift << 1) > 0 || (diag2 & d2Shift << 1) > 0)) {
            positionsUsed[(pos = 1 + y10)>>>5] ^= 1L << (pos& 31);
            searchDown10(y+1, column | 2, diag1 | d1Shift << 1, diag2 | d2Shift << 1, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }

        if (!((column & 4) > 0 || (diag1 & d1Shift << 2) > 0 || (diag2 & d2Shift << 2) > 0 )) {
            positionsUsed[(pos = 2 + y10)>>>5] ^= 1L << (pos& 31);
            searchDown10(y+1, column | 4, diag1 | d1Shift << 2, diag2 | d2Shift << 2, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 8) > 0 || (diag1 & d1Shift << 3) > 0 || (diag2 & d2Shift << 3) > 0 )) {
            positionsUsed[(pos = 3 + y10)>>>5] ^= 1L << (pos& 31);
            searchDown10(y+1, column | 8, diag1 | d1Shift << 3, diag2 | d2Shift << 3, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 16) > 0 || (diag1 & d1Shift << 4) > 0 || (diag2 & d2Shift << 4) > 0 )) {
            positionsUsed[(pos = 4 + y10)>>>5] ^= 1L << (pos& 31);
            searchDown10(y+1, column | 16, diag1 | d1Shift << 4, diag2 | d2Shift << 4, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 32) > 0 || (diag1 & d1Shift << 5) > 0 || (diag2 & d2Shift << 5) > 0 )) {
            positionsUsed[(pos = 5 + y10)>>>5] ^= 1L << (pos& 31);
            searchDown10(y+1, column | 32, diag1 | d1Shift << 5, diag2 | d2Shift << 5, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 64) > 0 || (diag1 & d1Shift << 6) > 0 || (diag2 & d2Shift << 6) > 0 )) {
            positionsUsed[(pos = 6 + y10)>>>5] ^= 1L << (pos& 31);
            searchDown10(y+1, column | 64, diag1 | d1Shift << 6, diag2 | d2Shift << 6, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 128) > 0 || (diag1 & d1Shift << 7) > 0 || (diag2 & d2Shift << 7) > 0 )) {
            positionsUsed[(pos = 7 + y10)>>>5] ^= 1L << (pos& 31);
            searchDown10(y+1, column | 128, diag1 | d1Shift << 7, diag2 | d2Shift << 7, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 256) > 0 || (diag1 & d1Shift << 8) > 0 || (diag2 & d2Shift << 8) > 0 )) {
            positionsUsed[(pos = 8 + y10)>>>5] ^= 1L << (pos& 31);
            searchDown10(y+1, column | 256, diag1 | d1Shift << 8, diag2 | d2Shift << 8, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 512) > 0 || (diag1 & d1Shift << 9) > 0 || (diag2 & d2Shift << 9) > 0 )) {
            positionsUsed[(pos = 9 + y10)>>>5] ^= 1L << (pos& 31);
            searchDown10(y+1, column | 512, diag1 | d1Shift << 9, diag2 | d2Shift << 9, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
    }
    private static void searchDown9(int y, int column, int diag1, int diag2, long[] positionsUsed) { // mostly taken from Competitive Programmer’s Handbook by Antti Laaksonen
        if (y == 9) {
            noHolesSolutions[9][cnt[9]++] = positionsUsed[0];
            noHolesSolutions[9][cnt[9]++] = positionsUsed[1];
            noHolesSolutions[9][cnt[9]++] = positionsUsed[2];
            noHolesSolutions[9][cnt[9]++] = positionsUsed[3];
            noHolesSolutions[9][cnt[9]++] = positionsUsed[4];
            return;
        }
        
        int d1Shift = 1 << y;
        int d2Shift = 1 << - y + 9 - 1;
        int pos, y9 = y*9;

        if (!((column & 1) > 0 || (diag1 & d1Shift) > 0 || (diag2 & d2Shift) > 0)) {
            positionsUsed[(pos = y9)>>>5] ^= 1L << (pos& 31);
            searchDown9(y + 1, column | 1, diag1 | d1Shift, diag2 | d2Shift, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 2) > 0 || (diag1 & d1Shift << 1) > 0 || (diag2 & d2Shift << 1) > 0)) {
            positionsUsed[(pos = 1 + y9)>>>5] ^= 1L << (pos& 31);
            searchDown9(y+1, column | 2, diag1 | d1Shift << 1, diag2 | d2Shift << 1, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }

        if (!((column & 4) > 0 || (diag1 & d1Shift << 2) > 0 || (diag2 & d2Shift << 2) > 0 )) {
            positionsUsed[(pos = 2 + y9)>>>5] ^= 1L << (pos& 31);
            searchDown9(y+1, column | 4, diag1 | d1Shift << 2, diag2 | d2Shift << 2, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 8) > 0 || (diag1 & d1Shift << 3) > 0 || (diag2 & d2Shift << 3) > 0 )) {
            positionsUsed[(pos = 3 + y9)>>>5] ^= 1L << (pos& 31);
            searchDown9(y+1, column | 8, diag1 | d1Shift << 3, diag2 | d2Shift << 3, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 16) > 0 || (diag1 & d1Shift << 4) > 0 || (diag2 & d2Shift << 4) > 0 )) {
            positionsUsed[(pos = 4 + y9)>>>5] ^= 1L << (pos& 31);
            searchDown9(y+1, column | 16, diag1 | d1Shift << 4, diag2 | d2Shift << 4, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 32) > 0 || (diag1 & d1Shift << 5) > 0 || (diag2 & d2Shift << 5) > 0 )) {
            positionsUsed[(pos = 5 + y9)>>>5] ^= 1L << (pos& 31);
            searchDown9(y+1, column | 32, diag1 | d1Shift << 5, diag2 | d2Shift << 5, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 64) > 0 || (diag1 & d1Shift << 6) > 0 || (diag2 & d2Shift << 6) > 0 )) {
            positionsUsed[(pos = 6 + y9)>>>5] ^= 1L << (pos& 31);
            searchDown9(y+1, column | 64, diag1 | d1Shift << 6, diag2 | d2Shift << 6, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 128) > 0 || (diag1 & d1Shift << 7) > 0 || (diag2 & d2Shift << 7) > 0 )) {
            positionsUsed[(pos = 7 + y9)>>>5] ^= 1L << (pos& 31);
            searchDown9(y+1, column | 128, diag1 | d1Shift << 7, diag2 | d2Shift << 7, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 256) > 0 || (diag1 & d1Shift << 8) > 0 || (diag2 & d2Shift << 8) > 0 )) {
            positionsUsed[(pos = 8 + y9)>>>5] ^= 1L << (pos& 31);
            searchDown9(y+1, column | 256, diag1 | d1Shift << 8, diag2 | d2Shift << 8, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
    }
    private static void searchDown8(int y, int column, int diag1, int diag2, long[] positionsUsed) { // mostly taken from Competitive Programmer’s Handbook by Antti Laaksonen
        if (y == 8) {
            noHolesSolutions[8][cnt[8]++] = positionsUsed[0];
            noHolesSolutions[8][cnt[8]++] = positionsUsed[1];
            noHolesSolutions[8][cnt[8]++] = positionsUsed[2];
            noHolesSolutions[8][cnt[8]++] = positionsUsed[3];
            noHolesSolutions[8][cnt[8]++] = positionsUsed[4];
            return;
        }
        
        int d1Shift = 1 << y;
        int d2Shift = 1 << - y + 8 - 1;
        int pos, y8 = y*8;
        if (!((column & 1) > 0 || (diag1 & d1Shift) > 0 || (diag2 & d2Shift) > 0)) {
            positionsUsed[(pos = y8)>>>5] ^= 1L << (pos& 31);
            searchDown8(y + 1, column | 1, diag1 | d1Shift, diag2 | d2Shift, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 2) > 0 || (diag1 & d1Shift << 1) > 0 || (diag2 & d2Shift << 1) > 0)) {
            positionsUsed[(pos = 1 + y8)>>>5] ^= 1L << (pos& 31);
            searchDown8(y+1, column | 2, diag1 | d1Shift << 1, diag2 | d2Shift << 1, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }

        if (!((column & 4) > 0 || (diag1 & d1Shift << 2) > 0 || (diag2 & d2Shift << 2) > 0 )) {
            positionsUsed[(pos = 2 + y8)>>>5] ^= 1L << (pos& 31);
            searchDown8(y+1, column | 4, diag1 | d1Shift << 2, diag2 | d2Shift << 2, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 8) > 0 || (diag1 & d1Shift << 3) > 0 || (diag2 & d2Shift << 3) > 0 )) {
            positionsUsed[(pos = 3 + y8)>>>5] ^= 1L << (pos& 31);
            searchDown8(y+1, column | 8, diag1 | d1Shift << 3, diag2 | d2Shift << 3, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 16) > 0 || (diag1 & d1Shift << 4) > 0 || (diag2 & d2Shift << 4) > 0 )) {
            positionsUsed[(pos = 4 + y8)>>>5] ^= 1L << (pos& 31);
            searchDown8(y+1, column | 16, diag1 | d1Shift << 4, diag2 | d2Shift << 4, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 32) > 0 || (diag1 & d1Shift << 5) > 0 || (diag2 & d2Shift << 5) > 0 )) {
            positionsUsed[(pos = 5 + y8)>>>5] ^= 1L << (pos& 31);
            searchDown8(y+1, column | 32, diag1 | d1Shift << 5, diag2 | d2Shift << 5, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 64) > 0 || (diag1 & d1Shift << 6) > 0 || (diag2 & d2Shift << 6) > 0 )) {
            positionsUsed[(pos = 6 + y8)>>>5] ^= 1L << (pos& 31);
            searchDown8(y+1, column | 64, diag1 | d1Shift << 6, diag2 | d2Shift << 6, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 128) > 0 || (diag1 & d1Shift << 7) > 0 || (diag2 & d2Shift << 7) > 0 )) {
            positionsUsed[(pos = 7 + y8)>>>5] ^= 1L << (pos& 31);
            searchDown8(y+1, column | 128, diag1 | d1Shift << 7, diag2 | d2Shift << 7, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
    }
    private static void searchDown7(int y, int column, int diag1, int diag2, long[] positionsUsed) { // mostly taken from Competitive Programmer’s Handbook by Antti Laaksonen
        if (y == 7) {
            noHolesSolutions[7][cnt[7]++] = positionsUsed[0];
            noHolesSolutions[7][cnt[7]++] = positionsUsed[1];
            noHolesSolutions[7][cnt[7]++] = positionsUsed[2];
            noHolesSolutions[7][cnt[7]++] = positionsUsed[3];
            noHolesSolutions[7][cnt[7]++] = positionsUsed[4];
            return;
        }
        
        int d1Shift = 1 << y;
        int d2Shift = 1 << - y + 7 - 1;
        int pos, y7 = y*7;
        if (!((column & 1) > 0 || (diag1 & d1Shift) > 0 || (diag2 & d2Shift) > 0)) {
            positionsUsed[(pos = y7)>>>5] ^= 1L << (pos& 31);
            searchDown7(y + 1, column | 1, diag1 | d1Shift, diag2 | d2Shift, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 2) > 0 || (diag1 & d1Shift << 1) > 0 || (diag2 & d2Shift << 1) > 0)) {
            positionsUsed[(pos = 1 + y7)>>>5] ^= 1L << (pos& 31);
            searchDown7(y+1, column | 2, diag1 | d1Shift << 1, diag2 | d2Shift << 1, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }

        if (!((column & 4) > 0 || (diag1 & d1Shift << 2) > 0 || (diag2 & d2Shift << 2) > 0 )) {
            positionsUsed[(pos = 2 + y7)>>>5] ^= 1L << (pos& 31);
            searchDown7(y+1, column | 4, diag1 | d1Shift << 2, diag2 | d2Shift << 2, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 8) > 0 || (diag1 & d1Shift << 3) > 0 || (diag2 & d2Shift << 3) > 0 )) {
            positionsUsed[(pos = 3 + y7)>>>5] ^= 1L << (pos& 31);
            searchDown7(y+1, column | 8, diag1 | d1Shift << 3, diag2 | d2Shift << 3, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 16) > 0 || (diag1 & d1Shift << 4) > 0 || (diag2 & d2Shift << 4) > 0 )) {
            positionsUsed[(pos = 4 + y7)>>>5] ^= 1L << (pos& 31);
            searchDown7(y+1, column | 16, diag1 | d1Shift << 4, diag2 | d2Shift << 4, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 32) > 0 || (diag1 & d1Shift << 5) > 0 || (diag2 & d2Shift << 5) > 0 )) {
            positionsUsed[(pos = 5 + y7)>>>5] ^= 1L << (pos& 31);
            searchDown7(y+1, column | 32, diag1 | d1Shift << 5, diag2 | d2Shift << 5, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 64) > 0 || (diag1 & d1Shift << 6) > 0 || (diag2 & d2Shift << 6) > 0 )) {
            positionsUsed[(pos = 6 + y7)>>>5] ^= 1L << (pos& 31);
            searchDown7(y+1, column | 64, diag1 | d1Shift << 6, diag2 | d2Shift << 6, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
    }

    private static void searchDown6(int y, int column, int diag1, int diag2, long[] positionsUsed) { // mostly taken from Competitive Programmer’s Handbook by Antti Laaksonen
        if (y == 6) {
            noHolesSolutions[6][cnt[6]++] = positionsUsed[0];
            noHolesSolutions[6][cnt[6]++] = positionsUsed[1];
            noHolesSolutions[6][cnt[6]++] = positionsUsed[2];
            noHolesSolutions[6][cnt[6]++] = positionsUsed[3];
            noHolesSolutions[6][cnt[6]++] = positionsUsed[4];
            return;
        }
        
        int d1Shift = 1 << y;
        int d2Shift = 1 << - y + 6 - 1;
        int pos, y6 = y*6;
        if (!((column & 1) > 0 || (diag1 & d1Shift) > 0 || (diag2 & d2Shift) > 0)) {
            positionsUsed[(pos = y6)>>>5] ^= 1L << (pos& 31);
            searchDown6(y + 1, column | 1, diag1 | d1Shift, diag2 | d2Shift, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 2) > 0 || (diag1 & d1Shift << 1) > 0 || (diag2 & d2Shift << 1) > 0)) {
            positionsUsed[(pos = 1 + y6)>>>5] ^= 1L << (pos& 31);
            searchDown6(y+1, column | 2, diag1 | d1Shift << 1, diag2 | d2Shift << 1, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }

        if (!((column & 4) > 0 || (diag1 & d1Shift << 2) > 0 || (diag2 & d2Shift << 2) > 0 )) {
            positionsUsed[(pos = 2 + y6)>>>5] ^= 1L << (pos& 31);
            searchDown6(y+1, column | 4, diag1 | d1Shift << 2, diag2 | d2Shift << 2, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 8) > 0 || (diag1 & d1Shift << 3) > 0 || (diag2 & d2Shift << 3) > 0 )) {
            positionsUsed[(pos = 3 + y6)>>>5] ^= 1L << (pos& 31);
            searchDown6(y+1, column | 8, diag1 | d1Shift << 3, diag2 | d2Shift << 3, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 16) > 0 || (diag1 & d1Shift << 4) > 0 || (diag2 & d2Shift << 4) > 0 )) {
            positionsUsed[(pos = 4 + y6)>>>5] ^= 1L << (pos& 31);
            searchDown6(y+1, column | 16, diag1 | d1Shift << 4, diag2 | d2Shift << 4, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 32) > 0 || (diag1 & d1Shift << 5) > 0 || (diag2 & d2Shift << 5) > 0 )) {
            positionsUsed[(pos = 5 + y6)>>>5] ^= 1L << (pos& 31);
            searchDown6(y+1, column | 32, diag1 | d1Shift << 5, diag2 | d2Shift << 5, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
    }
    private static void searchDown5(int y, int column, int diag1, int diag2, long[] positionsUsed) { // mostly taken from Competitive Programmer’s Handbook by Antti Laaksonen
        if (y == 5) {
            noHolesSolutions[5][cnt[5]++] = positionsUsed[0];
            noHolesSolutions[5][cnt[5]++] = positionsUsed[1];
            noHolesSolutions[5][cnt[5]++] = positionsUsed[2];
            noHolesSolutions[5][cnt[5]++] = positionsUsed[3];
            noHolesSolutions[5][cnt[5]++] = positionsUsed[4];
            return;
        }
        
        int d1Shift = 1 << y;
        int d2Shift = 1 << - y + 5 - 1;
        int pos, y5 = y*5;
        if (!((column & 1) > 0 || (diag1 & d1Shift) > 0 || (diag2 & d2Shift) > 0)) {
            positionsUsed[(pos = y5)>>>5] ^= 1L << (pos& 31);
            searchDown5(y + 1, column | 1, diag1 | d1Shift, diag2 | d2Shift, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 2) > 0 || (diag1 & d1Shift << 1) > 0 || (diag2 & d2Shift << 1) > 0)) {
            positionsUsed[(pos = 1 + y5)>>>5] ^= 1L << (pos& 31);
            searchDown5(y+1, column | 2, diag1 | d1Shift << 1, diag2 | d2Shift << 1, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }

        if (!((column & 4) > 0 || (diag1 & d1Shift << 2) > 0 || (diag2 & d2Shift << 2) > 0 )) {
            positionsUsed[(pos = 2 + y5)>>>5] ^= 1L << (pos& 31);
            searchDown5(y+1, column | 4, diag1 | d1Shift << 2, diag2 | d2Shift << 2, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 8) > 0 || (diag1 & d1Shift << 3) > 0 || (diag2 & d2Shift << 3) > 0 )) {
            positionsUsed[(pos = 3 + y5)>>>5] ^= 1L << (pos& 31);
            searchDown5(y+1, column | 8, diag1 | d1Shift << 3, diag2 | d2Shift << 3, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 16) > 0 || (diag1 & d1Shift << 4) > 0 || (diag2 & d2Shift << 4) > 0 )) {
            positionsUsed[(pos = 4 + y5)>>>5] ^= 1L << (pos& 31);
            searchDown5(y+1, column | 16, diag1 | d1Shift << 4, diag2 | d2Shift << 4, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
    }
    private static void searchDown4(int y, int column, int diag1, int diag2, long[] positionsUsed) { // mostly taken from Competitive Programmer’s Handbook by Antti Laaksonen
        if (y == 4) {
            noHolesSolutions[4][cnt[4]++] = positionsUsed[0];
            noHolesSolutions[4][cnt[4]++] = positionsUsed[1];
            noHolesSolutions[4][cnt[4]++] = positionsUsed[2];
            noHolesSolutions[4][cnt[4]++] = positionsUsed[3];
            noHolesSolutions[4][cnt[4]++] = positionsUsed[4];
            return;
        }
        
        int d1Shift = 1 << y;
        int d2Shift = 1 << - y + 4 - 1;
        int pos, y4 = y*4;
        if (!((column & 1) > 0 || (diag1 & d1Shift) > 0 || (diag2 & d2Shift) > 0)) {
            positionsUsed[(pos = y4)>>>5] ^= 1L << (pos& 31);
            searchDown4(y + 1, column | 1, diag1 | d1Shift, diag2 | d2Shift, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 2) > 0 || (diag1 & d1Shift << 1) > 0 || (diag2 & d2Shift << 1) > 0)) {
            positionsUsed[(pos = 1 + y4)>>>5] ^= 1L << (pos& 31);
            searchDown4(y+1, column | 2, diag1 | d1Shift << 1, diag2 | d2Shift << 1, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 4) > 0 || (diag1 & d1Shift << 2) > 0 || (diag2 & d2Shift << 2) > 0 )) {
            positionsUsed[(pos = 2 + y4)>>>5] ^= 1L << (pos& 31);
            searchDown4(y+1, column | 4, diag1 | d1Shift << 2, diag2 | d2Shift << 2, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
        if (!((column & 8) > 0 || (diag1 & d1Shift << 3) > 0 || (diag2 & d2Shift << 3) > 0 )) {
            positionsUsed[(pos = 3 + y4)>>>5] ^= 1L << (pos& 31);
            searchDown4(y+1, column | 8, diag1 | d1Shift << 3, diag2 | d2Shift << 3, positionsUsed);
            positionsUsed[pos>>>5] ^= 1L << (pos& 31);
        }
    }
    static class Reader {
        final private int BUFFER_SIZE = 65536, LINE_LENGTH = 80;
        private DataInputStream din;
        private byte[] buffer, auxBuf;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            auxBuf = new byte[LINE_LENGTH];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            auxBuf = new byte[LINE_LENGTH];
            bufferPointer = bytesRead = 0;
        }

        public boolean hasNext() throws IOException {
            if (bufferPointer == bytesRead) fillBuffer();
            return buffer[bufferPointer] != -1;
        }

        public String next() throws IOException {
            byte[] buf = auxBuf;
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
            byte[] buf = auxBuf;
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return Arrays.copyOf(buf, cnt);
        }

        public String nextLine() throws IOException {
            byte[] buf = auxBuf;
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
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
            boolean neg = c == '-';
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public int nextUInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();

            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            return ret;
        }

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = c == '-';
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

        public long nextULong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();

            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            return ret;
        }

        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = c == '-';
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

        public double nextUDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
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

        private static byte[] getBytes(int i) { // copied from Java Integer class
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
                r = q * 100 - i;
                i = q;
                buf[--charPos] = DigitOnes[r];
                buf[--charPos] = DigitTens[r];
            }

            // We know there are at most two digits left at this point.
            q = i / 10;
            r = q * 10 - i;
            buf[--charPos] = (byte) ('0' + r);

            // Whatever left is the remaining digit.
            if (q < 0) {
                buf[--charPos] = (byte) ('0' - q);
            }
            return buf;
        }

        private static byte[] getBytes(long i) { // copied from Java Integer class
            boolean neg = i < 0;
            if (!neg)
                i = -i;
            int charPos = 0;
            long p = -10;
            for (int j = 1; j < 20; j++) {
                if (i > p) {
                    charPos = j;
                    break;
                }
                p = 10 * p;
            }
            if (charPos == 0)
                charPos = 20;
            if (neg)
                charPos++;
            byte[] buf = new byte[charPos];
            buf[0] = '-';
            int r;
            long q;

            // Generate two digits per iteration
            while (i <= -100) {
                q = i / 100;
                r = (int) (q * 100 - i);
                i = q;
                buf[--charPos] = DigitOnes[r];
                buf[--charPos] = DigitTens[r];
            }

            // We know there are at most two digits left at this point.
            q = i / 10;
            r = (int) (q * 10 - i);
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

