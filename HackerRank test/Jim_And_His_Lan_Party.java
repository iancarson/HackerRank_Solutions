import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class Solution
{
	public static void main(String [] args)
	{
		Scanner sc = new Scanner(System.in);
		final int MAX = 100010;
		int n = sc.nextInt();
		int m = sc.nextInt();
		int q = sc.nextInt();
		int[] c = new int[MAX];
		int[] lab = new int[MAX];
		int[] sum = new int[MAX];
		int [] sl = new int[MAX];
		int [] res = new int[MAX];
		Map<Integer, Integer> [] s = new TreeMap[MAX];
		for(int i = 0; i  < MAX; i++)
		{
			s[i] = new TreeMap<Integer, Integer>();
		}
		for(int i = 1; i <= n; i++)
		{
			c[i] = sc.nextInt();
			sl[c[i]]++;
			lab[i] = i;
			s[i].put(c[i], 1);
			sum[i] = 1;
		}
		for(int i = 1;i <= m; i++)
		{
			if(sl[i] == 1)
				res[i] = 0;
			else
				res[i] = -1;
		}
		for(int i = 1; i <= q; i++)
		{
			int u = sc.nextInt();
			int v = sc.nextInt();
			u = getRoot(u, lab);
			v = getRoot(v, lab);
			if( u==v )
				continue;
			if(sum[u] > sum[v])
			{
				int tmp = u;
				u = v;
				v = tmp;
			}
			for(Map.Entry<Integer, Integer> entry : s[u].entrySet())
			{
				int k = entry.getKey();
				int S = entry.getValue();
				Integer val = s[v].get(k);
				if(val == null)
					val = 0;
				if((res[k] == -1) && (S+val == s1[k]))
					res[k] = i;
				s[v].put(k, val+S);
			}
			sum[v] += sum[u];
			lab[u] = v;
		}
		for(int i = 1; i <= m; i++)
		{
			System.out.println(res[i]);
			sc.close();
		}
	}
		static int getRoot(int u, int[] lab)
		{
			if(lab[u] == u)
				return u;
			lab[u] = getRoot(lab[u], lab);
			return lab[u];
		}
	}
