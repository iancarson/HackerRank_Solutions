import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

public class BeautifulQuadruples {
    private static long solve(int A, int B, int C, int D) {
        final int MAX = 3000;
        final int MAX_XOR = 4096;
 
        // Sort A, B, C, D in ascending order
        int[] arr = {A, B, C, D};
        Arrays.sort(arr);
        A = arr[0];
        B = arr[1];
        C = arr[2];
        D = arr[3];
 
 
        // total[b] holds the number of pairs {a,b} such that a≤b with 1≤a≤A and 1≤b≤B
        int[] total = new int[MAX + 1];
        for (int a = 1; a <= A; a++) {
            for (int b = a; b <= B; b++) {
                total[b] += 1;
            }
        }
        for (int i = 1; i <= MAX; i++) {
            total[i] += total[i - 1];
        }
 
 
        // count[B][x] holds the number of pairs {a,b} such that a≤b and a⊕b=x with 1≤a≤A, 1≤b≤B
        // Note the maximum XOR value we can get is 4096. This is because, 3000 is 12 bits, and maximum value is when all
        // 12 bits are set, which is 2^12 = 4096
        int[][] count = new int[MAX + 1][MAX_XOR + 1];
        for (int a = 1; a <= A; a++) {
            for (int b = a; b <= B; b++) {
                count[b][a ^ b] += 1;
            }
        }
        for (int i = 0; i <= MAX_XOR; i++) {
            for (int b = 1; b <= MAX; b++) {
                count[b][i] += count[b - 1][i];
            }
        }
 
        long ans = 0;
        for (int c = 1; c <= C; c++) {
            for (int d = c; d <= D; d++) {
                ans += (total[c] - count[c][c ^ d]);
            }
        }
 
        return ans;
    }
 
    public static void main(String[] args) throws FileNotFoundException {
     // System.setIn(new FileInputStream(System.getProperty("user.home") + "/" + "in.txt"));
        Scanner in = new Scanner(System.in);
 
 
        int a = in.nextInt();
        int b = in.nextInt();
        int c = in.nextInt();
        int d = in.nextInt();
 
        System.out.println(solve(a, b, c, d));
    }
}

