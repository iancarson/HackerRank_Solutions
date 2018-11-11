import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Solution {
	static InputStream is;
	static PrintWriter out;
	static String INPUT = "";
	static char[] reg;
	static int len, pos;
	static List<Node> nodes;
	
	static void solve()
	{
		int mod = 1000000007;
		for(int T = ni();T >= 1;T--){
			reg = ns(123);
			len = reg.length;
			pos = 0;
			idbase = 0;
			nodes = new ArrayList<Node>();
			Node[] root = expr();
			if(pos != len){
				throw new AssertionError();
			}
			
//			root[0].makeNexts();
			for(Node n : nodes){
				n.makeNexts();
			}
			root[1].isSink = 1;
			root[0].makeSink();
//			for(Node n : nodes){
//				tr(n.id, n.as, n.bs, n.next, n.isSink);
//			}
			
			Map<BitSet, Integer> states = new HashMap<BitSet, Integer>();
			BitSet ini = new BitSet();
			ini.set(root[0].id);
			states.put(ini, 0);
			
			Queue<BitSet> q = new ArrayDeque<BitSet>();
			q.add(ini);
			int step = 1;
			List<int[]> es = new ArrayList<int[]>();
			while(!q.isEmpty()){
				BitSet cur = q.poll();
				{
					BitSet nex = new BitSet();
					for(int i = cur.nextSetBit(0);i != -1;i = cur.nextSetBit(i + 1)){
						nex.or(nodes.get(i).as);
					}
//					tr(cur, 'a', nex);
					if(!nex.isEmpty()){
						if(!states.containsKey(nex)){
							states.put(nex, step++);
							q.add(nex);
						}
						es.add(new int[]{states.get(cur), states.get(nex)});
					}
				}
				{
					BitSet nex = new BitSet();
					for(int i = cur.nextSetBit(0);i != -1;i = cur.nextSetBit(i + 1)){
						nex.or(nodes.get(i).bs);
					}
//					tr(cur, 'b', nex);
					if(!nex.isEmpty()){
						if(!states.containsKey(nex)){
							states.put(nex, step++);
							q.add(nex);
						}
						es.add(new int[]{states.get(cur), states.get(nex)});
					}
				}
			}
			
			int[][] M = new int[step+1][step+1];
			for(int[] e : es){
				M[e[1]][e[0]]++;
			}
			for(Map.Entry<BitSet, Integer> e : states.entrySet()){
				for(int i = e.getKey().nextSetBit(0);i != -1;i = e.getKey().nextSetBit(i + 1)){
					if(nodes.get(i).isSink == 1){
						M[step][e.getValue()]++;
						break;
					}
				}
			}
			
			int L = ni();
			int[] v = new int[step+1];
			v[0] = 1;
			out.println(pow(M, v, L+1, mod)[step]);
		}
	}
	
	// A^e*v
	public static int[] pow(int[][] A, int[] v, long e, int mod)
	{
		int[][] MUL = A;
		for(int i = 0;i < v.length;i++)v[i] %= mod;
		for(;e > 0;e>>>=1) {
			if((e&1)==1)v = mul(MUL, v, mod);
			MUL = p2(MUL, mod);
		}
		return v;
	}
	
	public static int[] mul(int[][] A, int[] v, int mod)
	{
		int m = A.length;
		int n = v.length;
		int[] w = new int[m];
		for(int i = 0;i < m;i++){
			long sum = 0;
			for(int k = 0;k < n;k++){
				sum += (long)A[i][k] * v[k];
				sum %= mod;
			}
			w[i] = (int)sum;
		}
		return w;
	}
	
	public static int[][] p2(int[][] A, int mod)
	{
		int n = A.length;
		int[][] C = new int[n][n];
		for(int i = 0;i < n;i++){
			for(int j = 0;j < n;j++){
				long sum = 0;
				for(int k = 0;k < n;k++){
					sum += (long)A[i][k] * A[k][j];
					sum %= mod;
				}
				C[i][j] = (int)sum;
			}
		}
		return C;
	}
	
	static Node[] expr()
	{
		if(reg[pos] == '('){
			pos++;
			Node[] r1 = expr();
			if(reg[pos] == '*'){
				pos++;
				if(reg[pos] != ')')throw new AssertionError();
				pos++;
				Node source = new Node();
				Node sink = new Node();
				source.link(r1[0], 'e');
				r1[1].link(sink, 'e');
				r1[1].link(r1[0], 'e');
				source.link(sink, 'e');
				return new Node[]{source, sink};
			}else if(reg[pos] == '|'){
				pos++;
				Node[] r2 = expr();
				if(reg[pos] != ')')throw new AssertionError();
				pos++;
				Node source = new Node();
				Node sink = new Node();
				source.link(r1[0], 'e');
				source.link(r2[0], 'e');
				r1[1].link(sink, 'e');
				r2[1].link(sink, 'e');
				return new Node[]{source, sink};
			}else{
				Node[] r2 = expr();
				if(reg[pos] != ')')throw new AssertionError();
				pos++;
				r1[1].link(r2[0], 'e');
				return new Node[]{r1[0], r2[1]};
			}
		}else{
			Node source = new Node();
			Node sink = new Node();
			source.link(sink, reg[pos++]);
			return new Node[]{source, sink};
		}
	}
	
	static class Edge
	{
		public char c;
		public Node to;
	
		public Edge(char c, Node to) {
			this.c = c;
			this.to = to;
		}
		
		public String toString()
		{
			return c + ":" + to.id;
		}
	}
	
	static int idbase = 0;
	
	static class Node
	{
		public List<Edge> next;
		public BitSet as;
		public BitSet bs;
		public int id;
		public int isSink;
		public boolean und;
		
		public Node()
		{
			next = new ArrayList<Edge>();
			nodes.add(this);
			id = idbase++;
		}
		
		public void link(Node to, char c)
		{
			next.add(new Edge(c, to));
		}
		
		public void makeSink()
		{
			if(isSink != 0)return;
			this.isSink = -1;
			for(Edge e : next){
				e.to.makeSink();
				if(e.c == 'e' && e.to.isSink == 1){
					this.isSink = 1;
				}
			}
		}
		
		public void makeNexts()
		{
			Queue<Node> q = new ArrayDeque<Node>();
			as = new BitSet();
			bs = new BitSet();
			q.add(this);
			BitSet ved = new BitSet();
			ved.set(this.id);
			while(!q.isEmpty()){
				Node n = q.poll();
				for(Edge e : n.next){
					if(e.c == 'e'){
						if(!ved.get(e.to.id)){
							ved.set(e.to.id);
							q.add(e.to);
						}
					}else if(e.c == 'a'){
						as.set(e.to.id);
					}else if(e.c == 'b'){
						bs.set(e.to.id);
					}
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		long S = System.currentTimeMillis();
		is = INPUT.isEmpty() ? System.in : new ByteArrayInputStream(INPUT.getBytes());
		out = new PrintWriter(System.out);
		
		solve();
		out.flush();
		long G = System.currentTimeMillis();
		tr(G-S+"ms");
	}
	
	private static byte[] inbuf = new byte[1024];
	static int lenbuf = 0, ptrbuf = 0;
	
	private static int readByte()
	{
		if(lenbuf == -1)throw new InputMismatchException();
		if(ptrbuf >= lenbuf){
			ptrbuf = 0;
			try { lenbuf = is.read(inbuf); } catch (IOException e) { throw new InputMismatchException(); }
			if(lenbuf <= 0)return -1;
		}
		return inbuf[ptrbuf++];
	}
	
	private static boolean isSpaceChar(int c) { return !(c >= 33 && c <= 126); }
	private static int skip() { int b; while((b = readByte()) != -1 && isSpaceChar(b)); return b; }
	
	private static char[] ns(int n)
	{
		char[] buf = new char[n];
		int b = skip(), p = 0;
		while(p < n && !(isSpaceChar(b))){
			buf[p++] = (char)b;
			b = readByte();
		}
		return n == p ? buf : Arrays.copyOf(buf, p);
	}
	
	private static int ni()
	{
		int num = 0, b;
		boolean minus = false;
		while((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'));
		if(b == '-'){
			minus = true;
			b = readByte();
		}
		
		while(true){
			if(b >= '0' && b <= '9'){
				num = num * 10 + (b - '0');
			}else{
				return minus ? -num : num;
			}
			b = readByte();
		}
	}
	
	private static void tr(Object... o) { if(INPUT.length() != 0)System.out.println(Arrays.deepToString(o)); }
}
