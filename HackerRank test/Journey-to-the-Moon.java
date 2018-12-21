

import java.io.*;
import java.util.*;

public class Solution{		
		
		private BufferedReader in;	
		private StringTokenizer st;
		private PrintWriter out;
		
		int[]pset;
		void initSet(int size) {for(int i = 0;i<size;++i) pset[i] = i;}
		int findSet(int i){
			if(pset[i] == i) return i;
			return pset[i] = findSet(pset[i]);
		}
		void unionSet(int i,int j) {pset[findSet(i)] = findSet(j);}
		boolean isSameSet(int i,int j) {return findSet(i) == findSet(j);}
		
		
		void solve() throws IOException{
			
		
			int n = nextInt();
			int l = nextInt();
			pset = new int[n];
			initSet(n);
			for (int i = 0; i < l; i++) {
				unionSet(nextInt(), nextInt());
			}
			int [] groups = new int[n];
			for (int i = 0; i < n; i++) {
				groups[findSet(i)]++;
			}
			long ans = ((long)n*(n-1))/2;
			for (int i = 0; i < groups.length; i++) {
				if(groups[i] > 0){
					ans -= ((long)groups[i]*(groups[i]-1))/2;
				}
			}
			out.println(ans);
			
					
		}
			

		Solution() throws IOException {
			in = new BufferedReader(new InputStreamReader(System.in));	
			out = new PrintWriter(System.out);
			eat("");
			solve();	
			out.close();
		}

		private void eat(String str) {
			st = new StringTokenizer(str);
		}

		String next() throws IOException {
			while (!st.hasMoreTokens()) {
				String line = in.readLine();				
				if (line == null) {					
					return null;
				}
				eat(line);
			}
			return st.nextToken();
		}

		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		double nextDouble() throws IOException {
			return Double.parseDouble(next());
		}

		public static void main(String[] args) throws IOException {
			new Solution();
		}

		int gcd(int a,int b){
			if(b>a) return gcd(b,a);
			if(b==0) return a;
			return gcd(b,a%b);
		}

}