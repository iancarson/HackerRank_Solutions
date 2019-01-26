import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

class Edge implements Comparable<Edge>{
    int a, b, cost;
    Edge(int a, int b, int cost){
        this.a = a; this.b = b; this.cost = cost;
    }
    
    public int compareTo(Edge x){
        return Integer.compare(this.cost, x.cost);
    }
}

public class Solution {
    static int[] ar;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), m = sc.nextInt();
        PriorityQueue<Edge> pq = new PriorityQueue<Edge>(n+1);
        while(m-- > 0){
            int x = sc.nextInt(), y = sc.nextInt(), cost = sc.nextInt();
            pq.add(new Edge(x, y, cost));
        }
        int s = sc.nextInt();
        ar = new int[n+1]; //for union find
        Arrays.fill(ar, -1);
        long sum = 0, nEdges = 0;
        boolean[] vis = new boolean[n+1];
        while(nEdges != n-1){               //number of edges not equal to v-1 
            Edge curr = pq.remove();
            int a = curr.a, b = curr.b;
            int parA = getPar(a), parB = getPar(b);
            if(parA != parB){
                sum = sum + curr.cost;
                union(parA, parB);
                nEdges++;
            }
        }
        System.out.println(sum);
    }
    
    static int getPar(int a){
        while(ar[a] != -1)
            a = ar[a];
        return a;
    }
    
    static void union(int a, int b){
        int parA = getPar(a), parB = getPar(b);
        ar[parB] = parA;
    }
}