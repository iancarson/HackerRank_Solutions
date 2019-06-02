import java.io.*;
import java.util.*;

public class Solution {
  private static InputReader in;
  private static PrintWriter out;
  
  static class Edge {
    public int to;
    public long weight;
    public Edge(int to, int weight) {
      this.to = to;
      this.weight = weight;
    }
  }
  
  
  public static void dfs(int node, int par, long cdist) {
    dist[node] = cdist;
    for (Edge e : graph[node]) {
      if (e.to == par) continue;
      dfs(e.to, node, cdist+e.weight);
    }
  }
  
  public static void bfs(int node) {
    int front = 0, back = 0;
    Arrays.fill(vis, false);
    queue[back++] = node;
    dist[node] = 0;
    vis[node] = true;
    while (front < back) {
      int cur = queue[front++];
      for (Edge e : graph[cur]) {
        if (vis[e.to]) continue;
        vis[e.to] = true;
        dist[e.to] = dist[cur] + e.weight;
        queue[back++] = e.to;
      }
    }
  }
  
  public static ArrayList<Edge>[] graph;
  public static long[] dist;
  public static int[] queue;
  public static boolean[] vis;
  public static void main(String[] args) throws IOException {
    in = new InputReader(System.in);
    out = new PrintWriter(System.out, true);

    int n = in.nextInt();
    graph = new ArrayList[n];
    queue = new int[graph.length];
    vis = new boolean[graph.length];
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
    for (int i = 0; i < n-1; i++) {
      int a = in.nextInt()-1, b = in.nextInt()-1, c = in.nextInt();
      graph[a].add(new Edge(b,c));
      graph[b].add(new Edge(a,c));
    }
    
    dist = new long[n];
    bfs(0);
    long ans1 = 0;
    for (int i = 0; i < n; i++) ans1 += dist[i];
    bfs(n-1);
    long ans2 = 0;
    for (int i = 0; i < n; i++) ans2 += dist[i];
    out.println(Math.min(ans1, ans2));
    out.close();
    System.exit(0);
  }

  static class InputReader {
    public BufferedReader reader;
    public StringTokenizer tokenizer;

    public InputReader(InputStream stream) {
      reader = new BufferedReader(new InputStreamReader(stream), 32768);
      tokenizer = null;
    }

    public String next() {
      while (tokenizer == null || !tokenizer.hasMoreTokens()) {
        try {
          tokenizer = new StringTokenizer(reader.readLine());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
      return tokenizer.nextToken();
    }

    public int nextInt() {
      return Integer.parseInt(next());
    }
  }


}