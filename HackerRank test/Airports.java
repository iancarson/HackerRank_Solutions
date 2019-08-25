import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader reader = null;
	static StringTokenizer tokenizer = null;
	static PrintWriter writer = null;

	static String nextToken() throws IOException {
		while (tokenizer == null || !tokenizer.hasMoreTokens()) {
			tokenizer = new StringTokenizer(reader.readLine());
		}
		return tokenizer.nextToken();
	}

	static int nextInt() throws NumberFormatException, IOException {
		return Integer.parseInt(nextToken());
	}

	static double nextDouble() throws NumberFormatException, IOException {
		return Double.parseDouble(nextToken());
	}

	static long nextLong() throws NumberFormatException, IOException {
		return Long.parseLong(nextToken());
	}

	public static void main(String[] args) throws IOException {
		reader = new BufferedReader(new InputStreamReader(System.in));
		writer = new PrintWriter(System.out);
		banana();
		reader.close();
		writer.close();
	}

	static void banana() throws NumberFormatException, IOException {
		int q = nextInt();
		for (int t = 0; t < q; t++) {
			int n = nextInt();
			int d = nextInt();
			int L = 0;
			int R = 0;
			TreeSet<Integer> unc = new TreeSet<>();
			TreeMap<Integer, Integer> intervals = new TreeMap<>();
			for (int p = 0; p < n; p++) {
				int x = nextInt();
				if (p == 0) {
					L = x;
					writer.print("0");
				} else if (p == 1) {
					R = Math.max(L, x);
					L = Math.min(L, x);
					writer.print(" " + Math.max(0, d - (R - L)));
				} else {
					writer.print(" ");
					if (x < L) {
						int tmp = x;
						x = L;
						L = tmp;
						int mx = L + d;
						while (true) {
							if (unc.isEmpty()) {
								break;
							}
							int big = unc.last();
							if (big >= mx) {
								Integer prev = unc.floor(big - 1);
								if (prev != null) {
									int in = big - prev;
									int cv = intervals.get(in);
									if (cv == 1) {
										intervals.remove(in);
									} else {
										intervals.put(in, cv - 1);
									}
								}
								unc.remove(big);
							} else {
								break;
							}
						}
					}
					if (x > R) {
						int tmp = x;
						x = R;
						R = tmp;
						int mn = R - d;
						while (true) {
							if (unc.isEmpty()) {
								break;
							}
							int small = unc.first();
							if (small <= mn) {
								Integer next = unc.ceiling(small + 1);
								if (next != null) {
									int in = next - small;
									int cv = intervals.get(in);
									if (cv == 1) {
										intervals.remove(in);
									} else {
										intervals.put(in, cv - 1);
									}
								}
								unc.remove(small);
							} else {
								break;
							}
						}
					}
					if (x < L + d && x > R - d) {
						// uncovered
						if (!unc.contains(x)) {
							Integer prev = unc.floor(x - 1);
							Integer next = unc.ceiling(x + 1);
							if (prev != null && next != null) {
								int in = next - prev;
								int cv = intervals.get(in);
								if (cv == 1) {
									intervals.remove(in);
								} else {
									intervals.put(in, cv - 1);
								}
							}
							if (prev != null) {
								int in = x - prev;
								if (intervals.containsKey(in)) {
									intervals.put(in, intervals.get(in) + 1);
								} else {
									intervals.put(in, 1);
								}
							}
							if (next != null) {
								int in = next - x;
								if (intervals.containsKey(in)) {
									intervals.put(in, intervals.get(in) + 1);
								} else {
									intervals.put(in, 1);
								}
							}
							unc.add(x);
						}
					}
					if (unc.isEmpty()) {
						writer.print(Math.max(0, d - (R - L)));
					} else {
						int mn = R - d;
						int mx = L + d;
						int mxeco = -1000000000;
						mxeco = Math.max(mxeco, unc.first() - mn);
						mxeco = Math.max(mxeco, mx - unc.last());
						if (!intervals.isEmpty()) {
							mxeco = Math.max(mxeco, intervals.lastKey());
						}
						writer.print(mx - mn - mxeco);
					}
				}
			}
			writer.println();
		}
	}
}