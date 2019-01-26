import java.io.*;
import java.util.*;
import java.math.BigInteger;
import java.util.Map.Entry;

import static java.lang.Math.*;
import static java.lang.StrictMath.sqrt;

public class Solution extends PrintWriter {

	class Edge {
		final int to, cost;

		public Edge(int to, int cost) {
			this.to = to;
			this.cost = cost;
		}
	}

	boolean dfs(int from, int to, int mask, List<Edge>[] g, boolean[] color) {
		if (from == to) {
			return true;
		}

		if (color[from]) {
			return false;
		}
		color[from] = true;

		for (Edge e : g[from]) {
			if ((mask & e.cost) == 0) {
				if (dfs(e.to, to, mask, g, color)) {
					return true;
				}
			}
		}

		return false;
	}

	void run() {
		int n = nextInt(), m = nextInt(), k = 10;
		int curMask = 0;

		List<Edge>[] g = new List[n];

		for (int i = 0; i < n; i++) {
			g[i] = new ArrayList<Edge>();
		}

		for (int i = 0; i < m; i++) {
			int u = nextInt() - 1, v = nextInt() - 1, c = nextInt();
			g[u].add(new Edge(v, c));
			g[v].add(new Edge(u, c));
		}

		int a = nextInt()-1, b = nextInt()-1;

		if (!dfs(a, b, 0, g, new boolean[n])) {
			println(-1);
			return;
		}

		for (int bit = k - 1; bit >= 0; bit--) {
			int tempMask = curMask | (1 << bit);
			if (dfs(a, b, tempMask, g, new boolean[n])) {
				curMask = tempMask;
			}
		}

		println(((1 << k) - 1) ^ curMask);

	}

	public static boolean nextPermutation(int[] permutation) {
		int n = permutation.length, a = n - 2;
		while (0 <= a && permutation[a] >= permutation[a + 1]) {
			a--;
		}
		if (a == -1) {
			return false;
		}

		int b = n - 1;
		while (permutation[b] <= permutation[a]) {
			b--;
		}

		swap(permutation, a, b);
		for (int i = a + 1, j = n - 1; i < j; i++, j--) {
			swap(permutation, i, j);
		}
		return true;
	}

	public static void swap(int[] array, int i, int j) {
		if (i == j)
			return;
		array[i] ^= array[j];
		array[j] ^= array[i];
		array[i] ^= array[j];
	}

	int[][] nextMatrix(int n, int m) {
		int[][] matrix = new int[n][m];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				matrix[i][j] = nextInt();
		return matrix;
	}

	String next() {
		while (!tokenizer.hasMoreTokens())
			tokenizer = new StringTokenizer(nextLine());
		return tokenizer.nextToken();
	}

	boolean hasNext() {
		while (!tokenizer.hasMoreTokens()) {
			String line = nextLine();
			if (line == null) {
				return false;
			}
			tokenizer = new StringTokenizer(line);
		}
		return true;
	}

	int[] nextArray(int n) {
		int[] array = new int[n];
		for (int i = 0; i < n; i++) {
			array[i] = nextInt();
		}
		return array;
	}

	int nextInt() {
		return Integer.parseInt(next());
	}

	long nextLong() {
		return Long.parseLong(next());
	}

	double nextDouble() {
		return Double.parseDouble(next());
	}

	String nextLine() {
		try {
			return reader.readLine();
		} catch (IOException err) {
			return null;
		}
	}

	public Solution(OutputStream outputStream) {
		super(outputStream);
	}

	static BufferedReader reader;
	static StringTokenizer tokenizer = new StringTokenizer("");
	static Random rnd = new Random();

	public static void main(String[] args) throws IOException {
		reader = new BufferedReader(new InputStreamReader(System.in));
		Solution solution = new Solution(System.out);

		solution.run();
		solution.close();
		reader.close();
	}
}