import java.io.*;
import java.util.*;
public class Permutations<T>
{
	static class FastReader
	{
		BufferedReader br;
		StringTokenizer st;
		public FastReader()
		{
			br=new BufferedReader(new InputStreamReader(System.in));
		}
		String next()
		{
			while(st==null || !st.hasMoreElements())
			{
				try{
					st=new StringTokenizer(br.readLine());
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}
		int nextInt()
		{
			return Integer.parseInt(next());
		}
		long nextLong()
		{
			return Long.parseLong(next());
		}
		double nextDouble()
		{
			return Double.parseDouble(next());
		}
		String nextLine()
		{
			String str="";
			try
			{
				str=br.readLine();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			return str;
		}
	}
	public static void main(String[] args)
	{
	Permutations<Integer> obj=new Permutations<Integer>();
	Collection<Integer> input=new ArrayList<Integer>();
	FastReader in= new FastReader();
	int A=in.nextInt();
	int B=in.nextInt();
	int C=in.nextInt();
	int D=in.nextInt();
	int counter=0;
	input.add(A);
	input.add(B);
	input.add(C);
	input.add(D);
	Collection<List<Integer>> output=obj.permute(input);
	int k=0;
	Set<List<Integer>> pnr=null;
	for(int i=0;i<=input.size();i++)
	{
	pnr=new HashSet<List<Integer>>();
	for(List<Integer> integers : output)
	{
	pnr.add(integers.subList(i,integers.size()));
	}
	k=input.size()-i;
	//System.out.println("p(" + input.size()+ "," + k+"):" + "Count (" + pnr.size()+"):-" +pnr);
	//System.out.println(output.size());
	}
	for(int i=0;i<output.size();i++)
	{
		Integer[] arr1=output.get(i).toArray();
		Integer[] arr2=output.get(i +1).toArray();
		Arrays.sort(arr1);
		Arrays.sort(arr2);
		for(int j=0;j<4;j++)
		{
			if(arr1[j]==arr2[j])
			{
				counter ++;
			}
		}
		}
		System.out.println(counter/4);
	}
	public Collection<List<T>> permute(Collection<T> input)
	{
	Collection<List<T>> output=new ArrayList<List<T>>();
	if(input.isEmpty())
	{
	output.add(new ArrayList<T>());
	return output;
	}
	List<T> list=new ArrayList<T>(input);
	T head= list.get(0);
	List<T> rest=list.subList(1,list.size());
	for(List<T> permutations : permute(rest))
	{
	List<List<T>> sublists= new ArrayList<List<T>>();
	for(int i=0;i<=permutations.size();i++)
	{
	List<T> sublist=new ArrayList<T>();
	sublist.addAll(permutations);
	sublist.add(i,head);
	sublists.add(sublist);
	}
	output.addAll(sublists);
	}
	return output;
	}
	}