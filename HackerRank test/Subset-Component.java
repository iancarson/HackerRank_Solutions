import java.io.*;
import java.math.BigInteger;

public class Solution {

    static int col = 0;
    static long[] a;

    public static void solve(Input in, PrintWriter out) throws IOException {
        int n = in.nextInt();
        a = new long[n];
        for (int i = 0; i < n; ++i) {
            a[i] = new BigInteger(in.next()).longValue();
        }
        int ans = 0;
        for (int mask = 0; mask < 1 << n; ++mask) {
            col = 0;
            int countBits = 64;
            int add = 0;
            for (int i = 0; i < n; ++i) {
                if (((mask & ~col) & (1 << i)) != 0) {
                    long mask1 = dfs(i, mask);
                    if (mask1 != 0) {
                        add++;
                        countBits -= Long.bitCount(mask1);
                    }
                }
            }
            add += countBits;
            ans += add;
        }
        out.println(ans);
    }

    private static long dfs(int i, int mask) {
        if ((col & (1 << i)) != 0) {
            return 0;
        }
        col |= 1 << i;
        long ret = a[i];
        for (int j = 0; j < a.length; ++j) {
            if ((mask & (1 << j)) != 0 && (a[i] & a[j]) != 0) {
                ret |= dfs(j, mask);
            }
        }
        return ret;
    }

    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(System.out);
        solve(new Input(new BufferedReader(new InputStreamReader(System.in))), out);
        out.close();
    }

    static class Input {
        BufferedReader in;
        StringBuilder sb = new StringBuilder();

        public Input(BufferedReader in) {
            this.in = in;
        }

        public Input(String s) {
            this.in = new BufferedReader(new StringReader(s));
        }

        public String next() throws IOException {
            sb.setLength(0);
            while (true) {
                int c = in.read();
                if (c == -1) {
                    return null;
                }
                if (" \n\r\t".indexOf(c) == -1) {
                    sb.append((char)c);
                    break;
                }
            }
            while (true) {
                int c = in.read();
                if (c == -1 || " \n\r\t".indexOf(c) != -1) {
                    break;
                }
                sb.append((char)c);
            }
            return sb.toString();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        public double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }
    }
}