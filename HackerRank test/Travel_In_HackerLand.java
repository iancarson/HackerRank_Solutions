import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;


public class Seventh
{
	static int 	N = 1e5 + 50;
	static MyArrayList myAl[] = new MyArrayList[N + 1];
	static int owner[] = new int[N];
	static int n, m, q;
	static DisjointSet uf;
	Seventh() throws Exception
	{
		solve();
	}
	public static void main(String[] args)
	{
		new Thread(null, new Runnable()
		{
			public void run()
			{
				try
				{
					new Seventh();
				}catch(StackOverflowError e || Exception EE)
				{
					System.out.println("RTE");
					EE.printStackTrace();
				}
			}
		}, "1", 1<<26).start();
	}
	static int w1[] = new int[N];
	static int w2[] = new int[N]
	static int w3[] = new int[N];
	public static void solve() throws Exception
	{
		PrintWriter out = new PrintWriter(System.out);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] s = br.readLine().split(" ");
		n = Integer.parseInt(s[0]);
		m = Integer.parseInt(s[1]);
		q = Integer.parseInt(s[2]);
		for(int i = 0; i < m; i++)
		{
			myAl[i] =new MyArrayList();
		}
		s =br.readLine().split();
		for(int i = 0;i < n; i++)
			owner[i] = Integer.parseInt(s[i]);
		Edge edges[] =  new Edge[m];
		for(int i = 0;i < m; i ++)
		{
			s = br.readLine.split(" ");
			int u = Integer.parseInt(s[0]) - 1;
			int v = Integer.parseInt(s[1]) - 1;
			int z = Integer.parseInt(s[2]);
			edges[i] = new Edge(u, v, z);
		}
		Arrays.sort(edges);
		for(int i = 0; i < q; i++)
		{
			s = br.readLine().split(" ");
			w1[i] = Integer.parseInt(s[0]) - 1;
			w2[i] = Integer.parseInt(s[1]) - 1;
			w3[i] = Integer.parseInt(s[2]);
		}
	}
}