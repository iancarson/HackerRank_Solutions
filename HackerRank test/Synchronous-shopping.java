import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class HR_synchronous_shopping {

    static int k;

    public static void solve(Input in, PrintWriter out) throws IOException {
        int n = in.nextInt();
        int m = in.nextInt();
        k = in.nextInt();
        int[] cityMasks = new int[n];
        for (int i = 0; i < n; ++i) {
            int c = in.nextInt();
            for (int it = 0; it < c; ++it) {
                cityMasks[i] |= 1 << (in.nextInt() - 1);
            }
        }
        ArrayList<Edge>[] edges = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            edges[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; ++i) {
            int x = in.nextInt() - 1;
            int y = in.nextInt() - 1;
            int len = in.nextInt();
            edges[x].add(new Edge(x, y, len));
            edges[y].add(new Edge(y, x, len));
        }
        long[][] dists = dijkstra(0, edges, cityMasks);
        long ans = INF;
        for (int mask1 = 0; mask1 < 1 << k; ++mask1) {
            int complement = (1 << k) - 1 - mask1;
            for (int mask2 = complement; mask2 < 1 << k; mask2 = (mask2 + 1) | complement) {
                ans = Math.min(ans, Math.max(dists[n - 1][mask1], dists[n - 1][mask2]));
            }
        }
        out.println(ans);
    }

    static class Node implements Comparable<Node> {
        int id, mask;
        long val;

        Node(int id, int mask, long val) {
            this.id = id;
            this.mask = mask;
            this.val = val;
        }

        @Override
        public int compareTo(Node o) {
            return Long.compare(val, o.val);
        }
    }

    static class Edge {
        int a, b;
        long len;

        public Edge(int a, int b, int len) {
            this.a = a;
            this.b = b;
            this.len = len;
        }
    }

    static final long INF = 1000000000000000000L;

    private static long[][] dijkstra(int s, ArrayList<Edge>[] edges, int[] cityMasks) {
        long[][] dist = new long[edges.length][1 << k];
        for (long[] ar : dist) {
            Arrays.fill(ar, INF);
        }
        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        dist[s][cityMasks[s]] = 0;
        pq.add(new Node(s, cityMasks[s], 0));
        while (!pq.isEmpty()) {
            Node n = pq.poll();
            if (n.val != dist[n.id][n.mask]) {
                continue;
            }
            for (Edge e : edges[n.id]) {
                long nDist = dist[e.a][n.mask] + e.len;
                if (nDist < dist[e.b][n.mask | cityMasks[e.b]]) {
                    dist[e.b][n.mask | cityMasks[e.b]] = nDist;
                    pq.add(new Node(e.b, n.mask | cityMasks[e.b], nDist));
                }
            }
        }
        return dist;
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