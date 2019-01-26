import java.io.*;
import java.util.*;

public class Solution 
{
    static double PI = 3.1415926535;
    private static long mod = 1000000007;
    static PrintWriter out = new PrintWriter(System.out);
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st = new StringTokenizer("");
    public static void main(String[] args)  throws IOException
    {
        int t=Integer.parseInt(br.readLine());
        while(t-->0)
        {
            st=new StringTokenizer(br.readLine());
            int n=Integer.parseInt(st.nextToken());
            int m=Integer.parseInt(st.nextToken());
            int arr[][]=new int[n][n];
            for(int i=0;i<n;i++)
            {
                for(int j=0;j<n;j++)
                {
                    arr[i][j]=Integer.MAX_VALUE;
                }
            }
            for(int i=0;i<m;i++)
            {
                st=new StringTokenizer(br.readLine());
                int u=Integer.parseInt(st.nextToken())-1;
                int v=Integer.parseInt(st.nextToken())-1;
                int w=Integer.parseInt(st.nextToken());
                if(arr[u][v]>w || arr[v][u]>w)
                {
                    arr[u][v]=w;
                    arr[v][u]=w;
                }
            }
            int src=Integer.parseInt(br.readLine())-1;
            int dist[]=new int[n];
            boolean sptset[]=new boolean[n];
            for(int i=0;i<n;i++)
            {
                sptset[i]=false;
                dist[i]=Integer.MAX_VALUE;
            }
            dist[src]=0;
            for(int c=0;c<n-1;c++)
            {
                int u=findMin(dist,sptset);
                sptset[u]=true;
                //System.out.println("u "+u+" "+"dist "+dist[u]);
                for(int i=0;i<n;i++)
                {
                    if(!sptset[i] && arr[u][i]!=Integer.MAX_VALUE && 
                       dist[u]!=Integer.MAX_VALUE && dist[u]+arr[u][i]<dist[i])
                    {
                        dist[i]=dist[u]+arr[u][i];
                    }
                }
            }
            for(int i=0;i<n;i++)
            {
                if(i!=src)
                {
                    if(dist[i]==Integer.MAX_VALUE) System.out.print("-1 ");
                    else System.out.print(dist[i]+" ");
                }
            }
            System.out.println();
        }
    }
    public static int findMin(int dist[],boolean sptset[])
    {
        int min=Integer.MAX_VALUE;
        int index=-1;
        for(int i=0;i<dist.length;i++)
        {
            if(!sptset[i] && dist[i]<=min)
            {
                min=dist[i];
                index=i;
            }
        }
        return index;
    }
}