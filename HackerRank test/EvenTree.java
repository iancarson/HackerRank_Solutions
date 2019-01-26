import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

public class EvenTree
{
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		int N=in.nextInt();
		int M=in.nextInt();
		boolean children [] =new boolean[N +1];
		int [] count =new int [N +1];
		HashMap<Integer,ArrayList<Integer>> map= new HashMap<>();
		for(int i=0;i<M;i++)
		{
			int child =in.nextInt();
			int parent =in.nextInt();
			children[child] =true;
			ArrayList<Integer> list =map.get(parent);
			if(list==null)
			{
				list=new ArrayList<Integer>();
			}
			list.add(child);
			map.put(parent,list);
		}int roots =0;
		for(int i=1;i<N;i++)
		{
			if(!children[i])
			{
				setCount(map,count,i);
				roots++;
			}
		}
		int sum=0;
		for(int i=1;i<N;i++)
		{
			if(count[i]>1 && count[i]%2==0)
			{
				sum++;
			}
		}
		sum -=roots;
		System.out.println(sum);
	}
	public static int setCount(HashMap<Integer,ArrayList<Integer>> map,int [] count,int node)
	{
		if(!map.containsKey(node))
		{
			count[node]=1;return 1;
		}
		ArrayList<Integer> list=map.get(node);
		int sum=1;
		for(int value:list)
		{
			sum+=setCount(map,count,value);
		}
		count[node]=sum;
		return count[node];
	}
}