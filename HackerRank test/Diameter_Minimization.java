import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

	static int[] stack = new int[1100];
	static int[] visited = new int[1100];
	
	static int diameter(int[][] g, int b) {
		for (int i = 0; i < g.length; i++) {
			visited[i] = -1;
		}
		stack[0] = b;
		visited[b] = 0;
		int d = 0;
		int c = 0;
		int v = 1;
		while(c < v) {
			int p = stack[c];			
			for (int i = 0; i < g[p].length; i++) {
				int q = g[p][i];
				if(visited[q] < 0) {
					visited[q] = visited[p] + 1;
					stack[v] = q;
					v++;
					if(visited[q] > d) {
						d = visited[q];
					}
				}
			}
			c++;
		}
		if(v < g.length) throw new RuntimeException("Visited " + v + " out of " + g.length);
		return d;
	}
	
	static int diameter(int[][] g) {
		int d = 0;
		for (int i = 0; i < g.length; i++) {
			int di = diameter(g, i);
			if(di > d) d = di;
		}
		return d;
	}
	
	static int[][] generateGraph(int n, int m) {
		int[] b = getK(n,  m);
		int n1 = n / m;
		if(n < 10) n1 = n; else {
			n1 = b[1];
			if(seed.nextDouble() < 0.5 && n1 * m < n) n1 = n1 * m;
		}
		int[][] g = new int[n][m];
		int l = 1;
		for (int p = 0; p < n; p++) {
			int s = 0;
			for (int i = 0; i < m; i++) {
				if(l < n) {
					g[p][i] = l;
					l++;
				} else {
					g[p][i] = s;
					if(s > 0) g[p][i] = seed.nextInt(n1);
					if(s + 1 < n) s++;
				}
			}
		}		
		return g;
	}
	
	static int[] getK(int n, int m) {
		int leaves = n - (n + m - 1) / m;
		if(m > 1 && n > 10){
			return new int[]{1, (n - leaves) };
		}
		int[] ps = new int[11];
		ps[0] = 1;
		int s = 1;
		int l = 1;
		int b = 0;
		int e = 0;
		while(true) {
			ps[l] = ps[l-1] * m;
			if(ps[l] * ps[l] < leaves * (m - 1)) {
				b = s;
				e = b + ps[l];
			}
			s += ps[l];
			if(s >= n) {
				ps[l] = ps[l] - s + n;
				break;
			} else {
				l++;
			}
		}
		return new int[]{b, e};
	}
	
	static int[][] generateGraph1(int n, int m, int h) {
		if(h > n / 2) h = n / 2;
		int[] b = getK(n,  m);
		int[][] g = new int[n][m];
		int l = 1;
		int q = b[0];
		for (int p = 0; p < n; p++) {
			int s = 0;
			for (int i = 0; i < m; i++) {
				if(l < n) {
					g[p][i] = l;
					l++;
				} else {
					g[p][i] = s;
					if(s > 0 || p % h != 0) {
						g[p][i] = q;
						q++;
						if(q == b[1]) q = b[0];
					} else {
						if(s + 1 < n) s++;
					}
				}
			}
		}		
		return g;
	}
	
	static void print(int[][] g, int n, int m) {
		int d = diameter(g);
		System.out.println(d);
		for (int p = 0; p < n; p++) {
			for (int i = 0; i < m; i++) {
				if(i > 0) System.out.print(" ");
				System.out.print(g[p][i]);
			}
			System.out.println("");
		}		
	}
	
	static Random seed = new Random();
	
	static void run() {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int m = in.nextInt();
		long t = System.currentTimeMillis();
		int[][] g = generateGraph(n, m);
		int d = diameter(g);
		for (int h = 1; h < n / 3 && h < 30; h++) {
			try {
				int[][] gi = generateGraph1(n, m, h);
				int di = diameter(gi);
				if(di < d) {
					g = gi;
					d = di;
				}
				long dt = System.currentTimeMillis() - t;
				if(dt > 700) break;
			} catch (Exception e) {
				break;
			}
		}
		for (int i = 0; i < 5000; i++) {
			int[][] gi = generateGraph(n, m);
			int di = diameter(gi);
			if(di < d) {
				g = gi;
				d = di;
			}
			long dt = System.currentTimeMillis() - t;
			if(dt > 1000) break;
		}
		print(g, n, m);
	}
    public static void main(String[] args) {
        run();
    }
}