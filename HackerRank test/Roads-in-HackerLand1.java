import java.io.*;
import java.util.*;


public class Solution {
    private BufferedReader in;
    private StringTokenizer line;
    private PrintWriter out;
    private boolean isDebug;

    public Solution(boolean isDebug) {
        this.isDebug = isDebug;
    }

    private static final int mm = 1000000007;

    private long mult(long a, long b) {
        return a * b % mm;
    }

    private ArrayList<Pii>[] g;

    private int[] parent;
    private int[] rank;
    private long[] result;
    private int n;

    private int findSet(int v) {
        if (v == parent[v]) {
            return v;
        }
        return parent[v] = findSet(parent[v]);
    }

    private void unionSets(int a, int b) {
        a = findSet(a);
        b = findSet(b);
        if (a != b) {
            if (rank[a] < rank[b]) {
                int t = a;
                a = b;
                b = t;
            }
            parent[b] = a;
            if (rank[a] == rank[b]) {
                rank[a]++;
            }
        }
    }

    public void solve() throws IOException {
        n = nextInt();
        int m = nextInt();
        g = new ArrayList[n];
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
            parent[i] = i;
        }
        A[] a = new A[m];
        for (int i = 0; i < m; i++) {
            int t1 = nextInt() - 1;
            int t2 = nextInt() - 1;
            int t3 = nextInt();
            a[i] = new A(t1, t2, t3);
        }
        Arrays.sort(a, new Comparator<A>() {
            @Override
            public int compare(A o1, A o2) {
                return o1.d - o2.d;
            }
        });
        for (int i = 0; i < m; i++) {
            if (findSet(a[i].x) != findSet(a[i].y)) {
                unionSets(a[i].x, a[i].y);
                g[a[i].x].add(new Pii(a[i].y, a[i].d));
                g[a[i].y].add(new Pii(a[i].x, a[i].d));
            }
        }
        result = new long[210000];
        go(0, -1);
        for (int i = 0; i + 1 < result.length; i++) {
            result[i + 1] += result[i] / 2;
            result[i] %= 2;
        }
        int idx = 0;
        for (int i = result.length - 1; i > 0; i--) {
            if (result[i] == 1) {
                idx = i;
                break;
            }
        }
        while (idx >= 0) {
            out.print(result[idx--]);
        }
        out.println();
    }

    private int go(int v, int p) {
        int cnt = 1;
        for (Pii nv : g[v]) {
            if (nv.key != p) {
                int c = go(nv.key, v);
                cnt += c;
                result[nv.value] += (n - c) * (long) c;
            }
        }
        return cnt;
    }

    private static class A {
        private int x;
        private int y;
        private int d;

        public A(int x, int y, int d) {
            this.x = x;
            this.y = y;
            this.d = d;
        }
    }

    private long pow(long a, int n) {
        if (n == 0) return 1;
        long t = pow(a, n / 2);
        t = mult(t, t);
        if (n % 2 != 0) t = mult(a, t);
        return t;
    }

    public static void main(String[] args) throws IOException {
        new Solution(args.length > 0 && "DEBUG_MODE".equals(args[0])).run(args);
    }

    public void run(String[] args) throws IOException {
        if (isDebug) {
            in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
        } else {
            in = new BufferedReader(new InputStreamReader(System.in));
        }
        out = new PrintWriter(System.out);
//        out = new PrintWriter("output.txt");

//        int t = nextInt();
        int t = 1;
        for (int i = 0; i < t; i++) {
//            out.print("Case #" + (i + 1) + ": ");
            solve();
        }

        in.close();
        out.flush();
        out.close();
    }

    private int[] nextIntArray(int n) throws IOException {
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = nextInt();
        }
        return res;
    }

    private long[] nextLongArray(int n) throws IOException {
        long[] res = new long[n];
        for (int i = 0; i < n; i++) {
            res[i] = nextInt();
        }
        return res;
    }

    private int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }

    private long nextLong() throws IOException {
        return Long.parseLong(nextToken());
    }

    private double nextDouble() throws IOException {
        return Double.parseDouble(nextToken());
    }

    private String nextToken() throws IOException {
        while (line == null || !line.hasMoreTokens()) {
            line = new StringTokenizer(in.readLine());
        }
        return line.nextToken();
    }

    private static class Pll {
        private long key;
        private long value;

        public Pll(long key, long value) {
            this.key = key;
            this.value = value;
        }
    }

    private static class Pii {
        private int key;
        private int value;

        public Pii(int key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pii pii = (Pii) o;

            if (key != pii.key) return false;
            return value == pii.value;

        }

        @Override
        public int hashCode() {
            int result = key;
            result = 31 * result + value;
            return result;
        }

        @Override
        public String toString() {
            return "Pii{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

    private static class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair<?, ?> pair = (Pair<?, ?>) o;

            if (key != null ? !key.equals(pair.key) : pair.key != null) return false;
            return !(value != null ? !value.equals(pair.value) : pair.value != null);

        }

        @Override
        public int hashCode() {
            int result = key != null ? key.hashCode() : 0;
            result = 31 * result + (value != null ? value.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }
}