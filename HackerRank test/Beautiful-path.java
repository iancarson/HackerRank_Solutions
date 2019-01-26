import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution_13
{
	private static class Graph{
		private HashMap<Integer,Node> nodes= new HashMap<Integer,Node>();
		private enum Color {
			WHITE,GREY,BLACK;
		}
		private final int infinity =10000007;
		private class Node{
			int key;
			int distance= infinity;
			Color color=Color.WHITE;
			ArrayList<Pair<Node,Integer>>siblings;
			Node(int key)
			{
				this.key=key;
				this.siblings=new ArrayList<>();
			}
			public boolean equals(Object obj)
			{
				return ((Node)obj).key==this.key;
			}
		}
		private class Pair<T,V>
		{
			T first;
			V second;
			Pair(T first,V second)
			{
				this.first=first;
				this.second=second;
			}
		}
		public void addEdge(int u,int v,int w)
		{
			Node ui=nodes.get(u);
			Node vi=nodes.get(v);
			vi.siblings.add(new Pair<Node,Integer>(ui,w));
			ui.siblings.add(new Pair<Node,Integer>(vi,w));
		}
		public void init(int n)
		{
			for(int i=1;i<=n;i++)
				nodes.put(i,new Node(i));
		}
		public void dijkistra(int source)
		{
			//Init source s
			Node s = nodes.get(source);
			s.distance=0;
			//Form priority Queue
			PriorityQueue<Node>priorityqueue =new PriorityQueue<Node>(new Comparator<Node>(){
				public int compare(Node u,Node v)
				{
					return u.distance<=v.distance ? 1:0;
				}
			});
			for(Node u :nodes.values())
				priorityqueue.add(u);
			int newDist;
			Node sibling;
			while(!priorityqueue.isEmpty())
			{
				s=priorityqueue.poll();
				s.color=Color.BLACK;
				for(Pair<Node,Integer> pair : s.siblings)
				{
					newDist = calcDist(pair.second,s.distance);
					sibling= pair.first;
					if(sibling.distance >newDist)
					{
						priorityqueue.remove(sibling);
						sibling.distance=newDist;
						priorityqueue.add(sibling);
					}
				}
			}
		}
		private int calcDist(int a,int b)
		{
			return a|b;
		}
		public void showResult(int z)
		{
			int dist =nodes.get(z).distance;
			if(dist !=infinity)
			{
				System.out.println(dist);

			}
			else
				system.out.println("-1");
		}
	}
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		int n,m,x,y,r,s,z;
		Graph graph=new Graph();
		n=in.nextInt();
		graph.init(n);
		m=in.nextInt();
		for(int i=0;i<m;i++)
		{
			x=in.nextInt();
			y=in.nextInt();
			r=in.nextInt();
			graph.addEdge(x,y,r);
		}
		s=in.nextInt();
		z=in.nextInt();
		graph.dijkistra(s);
		graph.showResult(z);
	}
}