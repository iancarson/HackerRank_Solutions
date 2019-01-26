import java.util.*;
import java.lang.*;
import java.io.*;

class AllpairShortestPath
{
	final static int INF =99999, V=4;
	void floydwarshall(int graph[][])
	{
		int dist[][] =new int[][];
		int i,j,k;
		/*Initialize the solution matrix same as input graph matrix
		or we can say the initial values of shortest distances are based
		on shortest paths considering no intermediate vertex */
		for(i=0;i<V;i++)
			for(j=0;j<V;j++)
				dist[i][j]=graph[i][j];
			/*Add all vertices one by one to the set of intermediate 
			vertices.
			---->Before start of an iteration,we have shortest
			distances between all pairs of vertices such that 
			shortest distances considder only the vertices 
			set{0,1,2 ...k} */
			for(k=0;k<V;k++)
			{
				//Pick all vertices as source one by one
				for(i=0;i<V;i++)
				{
					//pick all vertices as destination for the above picked
					//source
					for(j=0;j<V;j++)
					{
						//If vertex k is on the shortest path from
						//i to j,then update the value of dist[i][j]
						if(dist[i][k] + dist[k][j] < dist[i][j])
							dist[i][j]= dist[i][k] + dist[k][j];
					}
				}
			}
			printSolution(dist);
	}
	void printSolution(int dist[][])
	{
		System.out.println("The following matrix shows the shortest" +
			"distances between every pair of vertices");
		for(int i=0;i<V;++i)
		{
			for(int j=0;j<V;++j)
			{
				if(dist[i][j]==INF)
					System.out.println("INF");
				else
					System.out.print(dist[i][j] + " ");
			}
			System.out.println();
		}
	}
	//Driver program to test above function
	public static void main(String[] args)
	{
		int graph [][] ={{0,5,INF,10},
		{INF,0,3,INF},
		{INF,INF,0,1},
		{INF,INF,INF,0}};
		AllpairShortestPath a= new AllpairShortestPath();
		a.floydwarshall(graph);
	}
}