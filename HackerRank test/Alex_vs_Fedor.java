

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

    public static void main(String[] args) throws IOException {
        InputReader reader = new InputReader(System.in);
        int N = reader.readInt();
        int M = reader.readInt();
        Edge[] edges = new Edge[M];
        long[][] A = new long[N-1][N-1];
        for (int m=0; m<M; m++) {
            int from = reader.readInt()-1;
            int to = reader.readInt()-1;
            int cost = reader.readInt();
            edges[m] = new Edge(from, to, cost);
            
            if (from != N-1) A[from][from]++;
            if (to != N-1) A[to][to]++;
            if (from != N-1 && to != N-1) {
                A[from][to]--;
                A[to][from]--;
            }
        }
        long total = determinant(A);
        long mst = 1;
        Arrays.sort(edges, new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.cost-o2.cost;
            }
        });
        int[] group = new int[N];
        for (int n=0; n<N; n++) {
            group[n] = n;
        }
        int nextEdge = 0;
        while (nextEdge < M) {
            int currentCost = edges[nextEdge].cost;
            Map<Integer,Node> map = new HashMap<Integer,Node>();
            while (nextEdge<M && edges[nextEdge].cost == currentCost) {
                Edge edge = edges[nextEdge];
                int fGroup = edge.from;
                while (group[fGroup] != fGroup) {
                    fGroup = group[fGroup];
                }
                Node fNode = map.get(fGroup);
                if (fNode == null) {
                    fNode = new Node();
                    fNode.origIndex = fGroup;
                    map.put(fGroup, fNode);
                }
                int tGroup = edge.to;
                while (group[tGroup] != tGroup) {
                    tGroup = group[tGroup];
                }
                Node tNode = map.get(tGroup);
                if (tNode == null) {
                    tNode = new Node();
                    tNode.origIndex = tGroup;
                    map.put(tGroup, tNode);
                }
                fNode.next.add(tNode);
                tNode.next.add(fNode);
                nextEdge++;
            }
            // DFS
            for (Node node : map.values()) {
                if (!node.visited) {
                    List<Node> component = new ArrayList<Node>();
                    dfs(node, component);
                    int componentGroup = component.get(0).origIndex;
                    // calculate #mst
                    int size = component.size();
                    for (int i=0; i<size; i++) {
                        component.get(i).index = i;
                    }
                    long[][] B = new long[size-1][size-1];
                    for (Node n1 : component) {
                        int from = n1.index;
                        for (Node n2 : n1.next) {
                            int to = n2.index;
                            if (from != size-1) B[from][from]++;
                            if (from != size-1 && to != size-1) {
                                B[from][to]--;
                            }
                        }
                        // collapse component
                        group[n1.origIndex] = componentGroup;
                    }
                    mst *= determinant(B);
                }
            }
        }
        long gcd = gcd(mst, total);
        mst /= gcd;
        total /= gcd;
        System.out.println(mst+"/"+total);
    }

    static void dfs(Node node, List<Node> component) {
        node.visited = true;
        component.add(node);
        for (Node subNode : node.next) {
            if (!subNode.visited) {
                dfs(subNode, component);
            }
        }
    }

    static class Node {
        int origIndex;
        int index;
        boolean visited;
        List<Node> next = new ArrayList<Node>();
    }

    static long gcd(long a, long b) {
        return (b == 0) ? a : gcd(b, a%b);
    }

    static long determinant(long[][] B) {
        int n = B.length;
        BigInteger[][] A = new BigInteger[n][n];
        for (int x=0; x<n; x++) {
            for (int y=0; y<n; y++) {
                A[x][y] = BigInteger.valueOf(B[x][y]);
            }
        }
        
        BigInteger D = BigInteger.ONE;
        for (int c=0; c<n; c++) {
            // find row with the smallest pivot
            int minRow = -1;
            BigInteger minPivot = null;
            for (int r=c; r<n; r++) {
                BigInteger pivot = A[r][c].abs();
                if (!pivot.equals(BigInteger.ZERO) && (minPivot == null || pivot.compareTo(minPivot)<0)) {
                    minPivot = pivot;
                    minRow = r;
                }
            }
            if (minRow == -1) return 0;
            if (minRow != c) {
                // swith rows
                BigInteger[] temp = A[c];
                A[c] = A[minRow];
                A[minRow] = temp;
                D = D.multiply(BigInteger.valueOf(-1));
            }
            BigInteger pivot = A[c][c];
            for (int r=c+1; r<n; r++) {
                BigInteger lead = A[r][c];
                if (!lead.equals(BigInteger.ZERO)) {
                    BigInteger gcd = lead.abs().gcd(minPivot);
                    BigInteger d = pivot.divide(gcd);
                    D = D.multiply(d);
                    BigInteger f = lead.divide(gcd).negate();
                    for (int cc=c; cc<n; cc++) {
                        A[r][cc] = A[r][cc].multiply(d);
                        A[r][cc] = A[r][cc].add(f.multiply(A[c][cc]));
                    }
                }
            }
        }
        BigInteger result = BigInteger.ONE;
        for (int c=0; c<n; c++) {
            result = result.multiply(A[c][c]);
        }
        return result.divide(D).longValue();
    }

    static class Edge {
        int from;
        int to;
        int cost;
        
        Edge(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }

    static final class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int read() throws IOException {
            if (curChar >= numChars) {
                curChar = 0;
                numChars = stream.read(buf);
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        public final int readInt() throws IOException {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res;
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }
    }
    
}