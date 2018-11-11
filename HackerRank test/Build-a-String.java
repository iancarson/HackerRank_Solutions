
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*; 
import java.util.*;
import java.util.regex.*;
public class Solution {
	private static BufferedReader br;
	private static StringTokenizer st;
	private static PrintWriter pw;

	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		//int qq = 1;
		//int qq = Integer.MAX_VALUE;
		int qq = readInt();
		for(int casenum = 1; casenum <= qq; casenum++)	{
			int n = readInt();
			int a = readInt();
			int b = readInt();
			String s = nextToken();
			int[] list = new int[n];
			for(int i = 0; i < n; i++) {
				list[i] = s.charAt(i) - 'a';
			}

			int[] dp = new int[n+1];
			Arrays.fill(dp, 1 << 30);
			dp[0] = 0;

			ArrayList<int[]> edges = new ArrayList<int[]>();
			ArrayList<Integer> link = new ArrayList<Integer>();
			ArrayList<Integer> length = new ArrayList<Integer>();
			edges.add(empty(26));
			link.add(-1);
			length.add(0);
			int last = 0;
			for(int i = 0; i < n; i++) {
				
				dp[i+1] = Math.min(dp[i+1], dp[i] + a);
				int len = 0;
				int currSuffixLoc = 0;
				while(currSuffixLoc < edges.size() && i + len < list.length) {
					if(edges.get(currSuffixLoc)[list[i+len]] == -1) {
						break;
					}
					currSuffixLoc = edges.get(currSuffixLoc)[list[i+len]];
					len++;
				}
				
				dp[i+len] = Math.min(dp[i+len], dp[i] + b);
				
				// construct r
				edges.add(empty(26));
				length.add(i+1);
				link.add(0);
				int r = edges.size() - 1;
				int p = last;
				while(p >= 0 && edges.get(p)[list[i]] == -1) {
					edges.get(p)[list[i]] = r;
					p = link.get(p);
				}
				if(p != -1) {
					int q = edges.get(p)[list[i]];
					if(length.get(p) + 1 == length.get(q)) {
						link.set(r, q);
					} 
					else {
						// we have to split, add q'
						edges.add(deepCopy(edges.get(q))); // copy edges of q
						length.add(length.get(p) + 1);
						link.add(link.get(q).intValue()); // copy parent of q
						int qqq = edges.size()-1;
						// add qq as the new parent of q and r
						link.set(q, qqq);
						link.set(r, qqq);
						// move short classes pointing to q to point to q'
						while(p >= 0 && edges.get(p)[list[i]] == q) {
							edges.get(p)[list[i]] = qqq;
							p = link.get(p);
						}
					}
				}
				last = r;
			}
			pw.println(dp[n]);
		}
		exitImmediately();
	}

	public static int[] deepCopy(int[] list) {
		int[] ret = new int[list.length];
		for(int i = 0; i < ret.length; i++) {
			ret[i] = list[i];
		}
		return ret;
	}
	
	public static int[] empty(int len) {
		int[] ret = new int[len];
		Arrays.fill(ret, -1);
		return ret;
	}

	private static void exitImmediately() {
		pw.close();
		System.exit(0);
	}

	private static long readLong() throws IOException {
		return Long.parseLong(nextToken());
	}

	private static double readDouble() throws IOException {
		return Double.parseDouble(nextToken());
	}

	private static int readInt() throws IOException {
		return Integer.parseInt(nextToken());
	}

	private static String nextLine() throws IOException  {
		if(!br.ready()) {
			exitImmediately();
		}
		st = null;
		return br.readLine();
	}

	private static String nextToken() throws IOException  {
		while(st == null || !st.hasMoreTokens())  {
			if(!br.ready()) {
				exitImmediately();
			}
			st = new StringTokenizer(br.readLine().trim());
		}
		return st.nextToken();
	}
}