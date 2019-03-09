
import java.awt.Point;
import java.io.*;
import java.math.BigInteger;
import java.util.*;
import static java.lang.Math.*;

public class Solution {
    BufferedReader in;
    PrintWriter out;
    StringTokenizer tok = new StringTokenizer("");

    public static void main(String[] args) {
        new Solution().run();
    }

    public void run() {
        try {
            long t1 = System.currentTimeMillis();
                in = new BufferedReader(new InputStreamReader(System.in));
                out = new PrintWriter(System.out);
            
            Locale.setDefault(Locale.US);
            solve();
            in.close();
            out.close();
            long t2 = System.currentTimeMillis();
            System.err.println("Time = " + (t2 - t1));
        } catch (Throwable t) {
            t.printStackTrace(System.err);
            System.exit(-1);
        }
    }

    String readString() throws IOException {
        while (!tok.hasMoreTokens()) {
            tok = new StringTokenizer(in.readLine());
        }
        return tok.nextToken();
    }

    int readInt() throws IOException {
        return Integer.parseInt(readString());
    }

    long readLong() throws IOException {
        return Long.parseLong(readString());
    }

    double readDouble() throws IOException {
        return Double.parseDouble(readString());
    }

    // solution
    void invertEdges() {
        ArrayList<Edge> edgesContainer = new ArrayList<Edge>(m);
        for (int i = 0; i < n; i++) {
            for (Edge edge = first[i]; edge != null; edge = edge.next) {
                edgesContainer.add(edge);
            }
        }
        Arrays.fill(first, null);
        for (int i = 0; i < edgesContainer.size(); i++) {
            Edge edge = new Edge(edgesContainer.get(i).b, edgesContainer.get(i).a, first);
        }
    }
    int n;
    int m;
    Edge[] first;

    void dfs(int source, boolean[] visited) {
        if (visited[source]) {
            return;
        }
        visited[source] = true;
    //    out.println("visiting " + source);
        for (Edge edge = first[source]; edge != null; edge = edge.next) {
            dfs(edge.b, visited);
        }
    }
    long modulo = 1000000000L;

    void solve() throws IOException {
        n = readInt();
        m = readInt();
        first = new Edge[n];
        for (int i = 0; i < m; i++) {
            int a = readInt() - 1;
            int b = readInt() - 1;
            Edge edge = new Edge(a, b, first);
        }

        boolean[] visitedA = new boolean[n];
        boolean[] visitedB = new boolean[n];
        boolean[] importantNode = new boolean[n];
        dfs(0, visitedA);
        invertEdges();
      //  out.println("----");
        dfs(n - 1, visitedB);
        invertEdges();

        for (int i = 0; i < n; i++) {
            importantNode[i] = visitedA[i] && visitedB[i];
        }

        int[] counter = new int[n];
        long[] f = new long[n];
        for (int i = 0; i < n; i++) {
            if (importantNode[i]) {
                for (Edge edge = first[i]; edge != null; edge = edge.next) {
                    if (importantNode[edge.b]) {
                        counter[edge.b]++;
                    }
                }
            }
        }
        f[0] = 1;
        counter[0] = 1;
        calculateNumberOfPaths(0, n - 1, counter, f);
        if (importantNode[n - 1] //if there is a path from 0 to n - 1
                && counter[n - 1] != 0)//then there is a cycle, probably
        {
            out.println("INFINITE PATHS");
        } else {
            out.println(f[n - 1]);
        }
    }

    private void calculateNumberOfPaths(int source, int target, int[] counter, long[] f) {
        counter[source]--;
        if (counter[source] == 0) {
            for (Edge edge = first[source]; edge != null; edge = edge.next) {
                f[edge.b] = (f[edge.b] + f[edge.a]) % modulo;
                calculateNumberOfPaths(edge.b, target, counter, f);
            }
        }
    }
}

class Edge {

    int a;
    int b;
    Edge next;

    Edge(int a, int b, Edge[] edgeTable) {
        this.a = a;
        this.b = b;
        next = edgeTable[a];
        edgeTable[a] = this;
    }
}