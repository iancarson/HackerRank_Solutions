//package hackerrank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter writer = new PrintWriter(System.out);
    StringTokenizer stringTokenizer;

    String next() throws IOException {
        while (stringTokenizer == null || !stringTokenizer.hasMoreTokens()) {
            stringTokenizer = new StringTokenizer(reader.readLine());
        }
        return stringTokenizer.nextToken();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    long nextLong() throws IOException {
        return Long.parseLong(next());
    }

    final int MOD = 1000 * 1000 * 1000 + 7;

    int sum(int a, int b) {
        a += b;
        return a >= MOD ? a - MOD : a;
    }
    int product(int a, int b) {
        return (int)(1L * a * b % MOD);
    }
    int pow(int x, int k) {
        int result = 1;
        while(k > 0) {
            if(k % 2 == 1) {
                result = product(result, x);
            }
            x = product(x, x);
            k /= 2;
        }
        return result;
    }
    int inv(int x) {
        return pow(x, MOD - 2);
    }

    int encode(int[] decoded) {
        int result = 0;
        for(int i = 0; i < decoded.length; i++) {
            result *= 3;
            result += decoded[decoded.length - 1 - i];
        }
        return result;
    }

    void solveTest() throws IOException {
        final int rows = nextInt(), cols = nextInt();
        final int[][] rowConnectors = new int[rows][cols];
        final int[][] colConnectors = new int[cols][rows];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols - 1; j++) {
                rowConnectors[i][j] = nextInt();
            }
        }
        for(int i = 0; i < rows - 1; i++) {
            for(int j = 0; j < cols; j++) {
                colConnectors[j][i] = nextInt();
            }
        }
        int maxMask = 1;
        for(int i = 0; i < cols + 1; i++) {
            maxMask *= 3;
        }
        final int[][][] dp = new int[rows + 1][cols + 1][maxMask];
        for(int i = 0; i < rows + 1; i++) {
            for(int j = 0; j < cols + 1; j++) {
                Arrays.fill(dp[i][j], Integer.MAX_VALUE);
            }
        }
        dp[0][0][0] = 0;
        int[] stack = new int[cols + 1];
        int stackTop = 0;
        int[] decoded = new int[cols + 1];
        int[] copy = new int[cols + 1];
        int[] correspondent = new int[cols + 1];
        for(int r = 0; r < rows; r++) {
            for(int c = 0; c <= cols; c++) {
                for(int mask = 0; mask < maxMask; mask++) {
                    if(dp[r][c][mask] == Integer.MAX_VALUE) continue;
                    if(c == cols) {
                        if(mask < maxMask / 3) {
                            dp[r + 1][0][mask * 3] = Math.min(dp[r + 1][0][mask * 3], dp[r][c][mask]);
                        }
                        continue;
                    }
                    int maskCopy = mask;
//                    Arrays.fill(correspondent, 0);
                    for(int i = 0; i <= cols; i++) {
                        decoded[i] = maskCopy % 3;
                        maskCopy /= 3;
                        if(decoded[i] == 1) {
                            stack[stackTop++] = i;
                        }
                        if(decoded[i] == 2) {
                            int pop = stack[--stackTop];
                            correspondent[i] = pop;
                            correspondent[pop] = i;
                        }
                    }
                    if(decoded[c] == 0 && decoded[c + 1] == 0) {
                        decoded[c] = 1;
                        decoded[c + 1] = 2;
                        int encoded = encode(decoded);
                        dp[r][c + 1][encoded] = Math.min(dp[r][c + 1][encoded], dp[r][c][mask] + rowConnectors[r][c] + colConnectors[c][r]);
                    }
                    else if(decoded[c] == 0 || decoded[c + 1] == 0) {
                        System.arraycopy(decoded, 0, copy, 0, decoded.length);
                        copy[c] = decoded[c] + decoded[c + 1];
                        copy[c + 1] = 0;
                        int encoded = encode(copy);
                        dp[r][c + 1][encoded] = Math.min(dp[r][c + 1][encoded], dp[r][c][mask] + colConnectors[c][r]);
                        copy[c + 1] = decoded[c] + decoded[c + 1];
                        copy[c] = 0;
                        encoded = encode(copy);
                        dp[r][c + 1][encoded] = Math.min(dp[r][c + 1][encoded], dp[r][c][mask] + rowConnectors[r][c]);
                    }
                    else {
                        boolean connect = (r == rows - 1 && c == cols - 1) || correspondent[c] != c + 1;
                        if(connect) {
                            int c1 = correspondent[c];
                            int c2 = correspondent[c + 1];
                            decoded[c1] = c1 < c2 ? 1 : 2;
                            decoded[c2] = c2 < c1 ? 1 : 2;
                            decoded[c] = decoded[c + 1] = 0;
                            int encoded = encode(decoded);
                            dp[r][c + 1][encoded] = Math.min(dp[r][c + 1][encoded], dp[r][c][mask]);
                        }
                    }
                }
            }
        }
        int ans = dp[rows][0][0];
        if(ans == Integer.MAX_VALUE) ans = 0;
        writer.println(ans);
        writer.close();
    }

    void solve() throws IOException {
        for(int i = nextInt(); i >= 1; i--) {
            solveTest();
        }
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        new Solution().solveTest();
    }
}