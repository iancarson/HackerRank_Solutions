import java.util.ArrayList;
import java.util.List;

class Graph
{
	int V;//number of vertices
	List<Integer> adj[];
	public Graph(int V)
	{
		this.V = V;
		adj = new ArrayList[V];
		for(int i = 0; i < V;i++)
		{
			adj[i] = new ArrayList<Integer>();
		}
	}
	public void addEdge(int u, int v)
	{
		adj[u].add(v);
	}
	//prints a Topological Sort of the complete graph
	public void topologicalSort()
	{
		//Create an array to store indegrees of all
		//vertices .Initialize all indegress as 0
		int indegree[] = new int[V];
		//Traverse adjacency lists to fill indegrees of
		//vertices.Takes O(V + E) time
		for(int i = 0; i < V; i++)
		{
			ArrayList<Integer> temp = (ArrayList<Integer>)adj[i];
			for(int node : temp)
			{
				indegree[node]++;
			}
		}
		//Create a queue and enqueue all vertices with indegree 0
		Queue<Integer> q = new LinkedList<Integer>();
		for(int i = 0;i < V; i++)
		{
			if (indegree[i] == 0) {
				q.add(i);
				
			}
		}
		//Initialize count of visited vertices
		int cnt = 0;
		//Create a avector to store result(A topological ordering
		//of the vertices)
		Vector<Integer> topOrder =  new Vector<Integer>();
		while(!q.isEmpty())

		{
			//Extract front of queue(or perform dequeue)
			//and add it to topological order
			int u = q.poll();
			topOrder.add(u);
			//Iterate through all of its neighbouring vertices
			//of dequeued node u and decrease their indegree by 1
			for(int node : adj[u])
			{
				//If in-degree becomes zero, add it to queue
				if(--indegree[node] == 0)
					q.add(node);
			}
			cnt++; 
		}
		//Check if there was a cycle
		if(cnt != V)
		{
			System.out.println("There exists a cycle");
			return;
		}
		//print topological order
		for(int i : topOrder)
		{
			System.out.print(i + "");
		}
	}
}
// Driver program to test above functions 
class Main 
{ 
    public static void main(String args[]) 
    { 
        // Create a graph given in the above diagram 
        Graph g=new Graph(6); 
        g.addEdge(5, 2); 
        g.addEdge(5, 0); 
        g.addEdge(4, 0); 
        g.addEdge(4, 1); 
        g.addEdge(2, 3); 
        g.addEdge(3, 1); 
        System.out.println("Following is a Topological Sort"); 
        g.topologicalSort(); 
  
    } 
} 