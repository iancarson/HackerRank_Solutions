import java.util.LinkedList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.io.InputStreamReader;
import java.io.IOException;


public class Floyd
{
    static LinkedList<Edge> edges;
    static int V;
    static class Edge
    {
        long src;
        long dest;
        int weight;    
    
    Edge(long src,long dest,int weight)
    {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
    }
    public void  addEdge(long src,long dest,int weight)
    {
        edges.add(new Edge(src,dest,weight));
    }
    long [][] floydWarshall(long graph[][])
    {
        long dist[][] = new long[V][V];
        int i,j,k;
        for(i = 0;i < V; i++)
            for(j = 0;j < V; j++)
                dist[i][j] = graph[i][j];//Initialize same as input graph to check the shortest path.
            for(k = 0;k < V; k++)
            {
                for(i = 0;i < V; i++)//Pick all vertices as source
                {
                    for(j = 0;j < V; j++)//pick all vertices as destination  fot the above picked vertices.
                    {
                        if(dist[i][k] + dist[k][j] < dist[i][j])
                            dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
            return dist;
    }
    static class FastReader
    {
        BufferedReader br;
        StringTokenizer st;
        FastReader()
        {
            br = new BufferedReader(new InputStreamReader(System.in));
        }
        String next()
        {
            while( st== null || !st.hasMoreElements())
                try
            {
                st = new StringTokenizer(br.readLine());
            } catch(IOException e)
            {
                e.printStackTrace();
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

    }
    public static void main(String[] args)
    {
        FastReader in = new FastReader();
        int node = in.nextInt();
        int edgesNo = in.nextInt();
        Floyd f =new Floyd();
        long [][] graph = new long[edgesNo][edgesNo];
        
        f.V = node;
        for(int i = 0,j = 0;i < edgesNo && j < edgesNo;i++,j++)
        {
            long src = in.nextLong();
            long dest = in.nextLong();
            int weight = in.nextInt();
            graph[i][0] =src;
            graph[0][j] = dest;
            f.edges.add(new Edge(src,dest,weight));
        }
        int t =in.nextInt();
         long [][] floydgraph = f.floydWarshall(graph);
        
            for(int i = 0; i< t ; i++ )
            {
                int ans = 0; 
                long srcquery = in.nextLong();
                long destquery = in.nextLong();
                for(int j = 0,k = 0;j < edgesNo && k < edgesNo ;j++,k++)
                {
                    if(floydgraph[j][0]  != srcquery && floydgraph[0][k] != destquery)//the path doesn't exist
                    {
                        ans = -1;

                    }
                    else
                    {
                        if(edges.get(i).src == srcquery && edges.get(i).dest == destquery)//if path exists.
                            ans = edges.get(i).weight;
                    }
                }
                System.out.println(ans);

            }
        
        
    }
}

