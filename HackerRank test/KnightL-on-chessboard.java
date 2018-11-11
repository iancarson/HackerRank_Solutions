import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {
    static class node{
        private int x;
        private int y;
        private int t;
        public node(int x, int y, int t){
            this.x = x;
            this.y = y;
            this.t = t;
        }
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        // your code goes here
        int[][] moves = new int[n-1][n-1];
        for(int i=1;i<n;i++){
            for(int j=1;j<n;j++){
                if(j>=i){
                    moves[i-1][j-1] = runBfs(i,j,n);
                }else{
                    moves[i-1][j-1] = moves[j-1][i-1];
                }
            }
        }
        for(int i=1;i<n;i++){
            for(int j=1;j<n;j++){
                System.out.print(moves[i-1][j-1] + " ");
            }
            System.out.print("\n");
        }
    }
    
    static int runBfs(int i,int j,int n){
        LinkedList<node> list = new LinkedList<node>();
        list.add(new node(0,0,0));
        if(list.size() == 0) return 0;
        boolean[][] visited = new boolean[n][n];
        while(list.size() != 0){
            node p = list.remove();
            int x = p.x;
            int y = p.y;
            int t = p.t;
            if(x-i>=0){
                if(y-j>=0){
                    addPoint(x-i,y-j,t+1,list,visited);
                }
                if(y+j < n){
                    addPoint(x-i,y+j,t+1,list,visited);
                }
            }
            if(x+i <n){
                if(y-j>=0){
                    addPoint(x+i,y-j,t+1,list,visited);
                }
                if(y+j < n){
                    if(x+i==n-1 && y+j==n-1) return t+1;
                    addPoint(x+i,y+j,t+1,list,visited);
                }
            }
            if(x-j>=0){
                if(y-i>=0){
                    addPoint(x-j,y-i,t+1,list,visited);
                }
                if(y+i < n){
                    addPoint(x-j,y+i,t+1,list,visited);
                }
            }
            if(x+j <n){
                if(y-i>=0){
                    addPoint(x+j,y-i,t+1,list,visited);
                }
                if(y+i < n){
                    if(x+j==n-1 && y+i==n-1) return t+1;
                    addPoint(x+j,y+i,t+1,list,visited);
                }
            }
            
        }
        return -1;
    }
    static void addPoint(int x, int y, int t, LinkedList<node> list, boolean[][] visited){
        if(visited[x][y]) return;
        list.add(new node(x,y,t));
        visited[x][y] = true;
    }
}