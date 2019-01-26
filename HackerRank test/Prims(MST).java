import java.io.*;
import java.util.*;

public class Solution {

    private StreamTokenizer in;
    private PrintWriter out;

    private int[] parent;
    private int[] rank;

    class Edge {
        public int v;
        public int u;
        public int cost;

        public Edge(int v, int u, int cost) {
            this.v = v;
            this.u = u;
            this.cost = cost;
        }
    }

    private int find(int v) {
        if (v == parent[v])
            return v;
        return parent[v] = find(parent[v]);
    }

    private void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a != b) {
            if (rank[a] < rank[b]) {
                int k = a;
                a = b;
                b = k;
            }
            parent[b] = a;
            if (rank[a] == rank[b]) {
                ++rank[a];
            }
        }
    }

    private int nextInt() throws IOException {
        in.nextToken();
        return (int)in.nval;
    }

    private void run() throws IOException {
        in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        out = new PrintWriter(new OutputStreamWriter(System.out));
        int T = 1;
        for (int t = 0; t < T; t++) {
            int n = nextInt();
            int m = nextInt();
            parent = new int[n + 1];
            rank = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
            SortedSet<Edge> edges = new TreeSet<Edge>(new Comparator<Edge>() {
                @Override
                public int compare(Edge o1, Edge o2) {
                    if (o1.cost == o2.cost) {
                        if (o1.v == o2.v) {
                            return o1.u - o2.u;
                        }
                        return o1.v - o2.v;
                    }
                    return o1.cost - o2.cost;
                }
            });
            for (int i = 0; i < m; i++) {
                int v = nextInt();
                int u = nextInt();
                int w = nextInt();
                edges.add(new Edge(v, u, w));
                edges.add(new Edge(u, v, w));
            }
            int ans = 0;
            for (Edge edge : edges) {
                int v = find(edge.v);
                int u = find(edge.u);
                if (v != u) {
                    union(edge.v, edge.u);
                    ans += edge.cost;
                }
            }
            out.println(ans);
        }
        out.flush();
        out.close();
    }

    public static void main(String[] args) throws IOException {
        new Solution().run();
    }
}