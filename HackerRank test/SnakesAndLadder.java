import java.util.Scanner;
import java.util.Stack;
import java.util.Arrays;
//import java.io.*;
public class Solution {
        public static void main(String[] args){
      Scanner in=new Scanner(System.in);
      int test=in.nextInt();
      for(int h=0;h<test;h++){
      int w[][]=new int[101][101];
      for(int i=1;i<=100;i++){
          for(int j=1;j<=100;j++){
              if(i!=j &&i<j && Math.abs(j-i)<=6){
                  w[i][j]=1;
              }
              else{w[i][j]=1000000;}
          }}
      int ladder=in.nextInt();
      for(int i=0;i<ladder;i++){
          int x=in.nextInt(),y=in.nextInt();
            w[x][y]=0;
      }
      int snake=in.nextInt();
      int sn[]=new int[snake];
      for(int i=0;i<snake;i++){
          int x=in.nextInt(),y=in.nextInt();sn[i]=x;
        w[x][y]=0;
      }
      Arrays.sort(sn);int cnt=0;
      for(int g=0;g<snake;g++){if(g>0 && sn[g]-sn[g-1]==1){++cnt;}else{cnt=0;}if(cnt>=6){break;}}
       Stack <Integer> t=new Stack<Integer>();
          int src=1,des=100;
        for(int i=1;i<=100;i++){
            if(i!=src){t.push(i);}}
        Stack <Integer> p=new Stack<Integer>();
        p.push(src);
          while(!t.isEmpty()){int min=989997979,loc=-1;
        for(int i=0;i<t.size();i++){
            w[src][t.elementAt(i)]=Math.min(w[src][t.elementAt(i)],w[src][p.peek()]+w[p.peek()][t.elementAt(i)]);
            if(w[src][t.elementAt(i)]<=min){
                min=(int) w[src][t.elementAt(i)];loc=i;}
        }
        
        p.push(t.elementAt(loc));t.removeElementAt(loc);}
           if(cnt>=6){System.out.println("-1");continue;}
             if(w[src][des]!=1000000){System.out.println(w[src][des]);}
             else {System.out.println("-1");}
        
  }}}

