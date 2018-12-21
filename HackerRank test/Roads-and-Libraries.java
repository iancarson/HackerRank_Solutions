import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        for(int a0 = 0; a0 < q; a0++){
            int n = in.nextInt();
            Graph g=new Graph(n,false);
            int m = in.nextInt();
            long clib = in.nextLong();
            long croad = in.nextLong();
            for(int a1 = 0; a1 < m; a1++){
                int city_1 = in.nextInt();
                int city_2 = in.nextInt();
                g.insertEdge(city_1-1,city_2-1);
            }
            System.out.println(getCost(g,clib,croad));
        }
    }
    private static long getCost(Graph g,long clib,long croad){
        if(clib<croad){
            return clib*g.v;
        }
        boolean visited[]=new boolean[g.v];
        long cost=0;
        for(int i=0;i<g.v;i++){
            if(!visited[i]){
                int x=DFSUtil(g,visited,i);
                cost+=(x-1)*croad;
                cost+=clib;
            }
        }
        return cost;
    }
    private static int DFSUtil(Graph g,boolean visited[],int current){
        int count=1;
        visited[current]=true;
        for(int a:g.list[current]){
            if(!visited[a]){
                count+=DFSUtil(g,visited,a);
            }
        }
        return count;
    }
}
class Graph {
    int v;
    LinkedList<Integer> list[];
    private final boolean directed;
    /** Constructor of Graph, takes no. of vertex and boolean value to specify whether graph is directed or not */
    Graph(int v,boolean directed){
        this.v=v;
        this.directed=directed;
        list=new LinkedList[v];
        for(int i=0;i<v;i++){
            list[i]=new LinkedList<Integer>();
        }
    }
    
    /* vertex numbering start with 0 as well as adjacency list no. start with 0 */
    
     public void insertEdge(int a,int b){
         list[a].add(b);
         if(!directed){
             list[b].add(a);
         }
     }
     
}