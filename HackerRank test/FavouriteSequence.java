import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class HR_favourite_sequence {

    public static void solve(Input in, PrintWriter out) throws IOException {
        int n = in.nextInt();
        ArrayList<Integer>[] g = new ArrayList[1000001];
        for (int i = 0; i < g.length; ++i) {
            g[i] = new ArrayList<Integer>();
        }
        boolean[] col = new boolean[g.length];
        int[] l = new int[g.length];
        for (int it = 0; it < n; ++it) {
            int k = in.nextInt();
            int prev = -1;
            for (int i = 0; i < k; ++i) {
                int x = in.nextInt();
                col[x] = true;
                if (i > 0) {
                    g[prev].add(x);
                    l[x]++;
                }
                prev = x;
            }
        }
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        for (int i = 0; i < g.length; ++i) {
            if (col[i] && l[i] == 0) {
                pq.add(i);
            }
        }
        while (!pq.isEmpty()) {
            int i = pq.poll();
            for (int j : g[i]) {
                l[j]--;
                if (l[j] == 0) {
                    pq.add(j);
                }
            }
            out.print(i + " ");
        }
        out.println();
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
