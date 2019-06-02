import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class HR_cat_jogging {

    static Random rnd = new Random();

    public static void solve(Input in, PrintWriter out, int K) throws IOException {
        int n = in.nextInt();
        int m = in.nextInt();
        ArrayList<Integer>[] edgesList = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            edgesList[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; ++i) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
//            int u, v;
//            do {
//                u = rnd.nextInt(n);
//                v = rnd.nextInt(n);
//            } while (edgesList[u].size() == K + 1 || edgesList[v].size() == K + 1);
            edgesList[u].add(v);
            edgesList[v].add(u);
        }
        int[][] edges = new int[n][];
        for (int i = 0; i < n; ++i) {
            edges[i] = new int[edgesList[i].size()];
            for (int it = 0; it < edges[i].length; ++it) {
                edges[i][it] = edgesList[i].get(it);
            }
            Arrays.sort(edges[i]);
        }
        long[] ar = new long[m * K];
        int arLen = 0;
        long ans = 0;
        boolean[] col = new boolean[n];
        for (int i = 0; i < n; ++i) {
            if (edges[i].length <= K) {
                for (int it = 0; it < edges[i].length; ++it) {
                    for (int jt = it + 1; jt < edges[i].length; ++jt) {
                        ar[arLen++] = n * edges[i][it] + edges[i][jt];
                    }
                }
            } else {
                Arrays.fill(col, false);
                for (int j : edges[i]) {
                    col[j] = true;
                }
                for (int j = 0; j < n; ++j) {
                    if (edges[j].length > K && j <= i) {
                        continue;
                    }
                    long cnt = 0;
                    for (int k : edges[j]) {
                        if (col[k]) {
                            cnt++;
                        }
                    }
                    ans += cnt * (cnt - 1) / 2;
                }
            }
        }
        Arrays.sort(ar, 0, arLen);
        for (int i = 0; i < arLen; ) {
            int j = i;
            while (j < ar.length && ar[i] == ar[j]) {
                ++j;
            }
            long cnt = j - i;
            ans += cnt * (cnt - 1) / 2;
            i = j;
        }
        if (ans % 2 != 0) {
            throw new AssertionError();
        }
        out.println(ans / 2);
    }

    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(System.out);
        solve(new Input(new BufferedReader(new InputStreamReader(System.in))), out, 150);
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