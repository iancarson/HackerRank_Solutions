import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    static int[] shiftI = {-2, -2, 0, 2, 2, 0};
    static int[] shiftJ = {-1, 1, 2, 1, -1, -2};
    static String[] let = {"UL", "UR", "R", "LR", "LL", "L"};
    
    static void printShortestPath(int n, int i_start, int j_start, int i_end, int j_end) {
        String[][] path = new String[n][n];
        int[][] steps = new int[n][n];
        path[i_start][j_start] = "";
        steps[i_start][j_start] = 0;
        int qi[] = new int[40000];
        int qj[] = new int[40000];
        qi[0] = i_start;
        qj[0] = j_start;
        int size = 1;
        
        for (int k = 0; k < size; k++)
        {
            int i = qi[k];
            int j = qj[k];
            
            for (int t = 0; t < 6; t++)
            {
                int ni = i + shiftI[t];
                int nj = j + shiftJ[t];
                
                if (ni >= 0 && ni < n && nj >= 0 && nj < n &&
                   path[ni][nj] == null)
                {
                    path[ni][nj] = (path[i][j].length() == 0 ? let[t] : path[i][j] + " " + let[t]);
                    steps[ni][nj] = steps[i][j] + 1;
                    qi[size] = ni;
                    qj[size] = nj;
                    size++;
                }
            }
        }
        
        if (path[i_end][j_end] == null)
        {
            System.out.println("Impossible");
        }
        else
        {
            System.out.println(steps[i_end][j_end]);
            System.out.println(path[i_end][j_end]);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int i_start = in.nextInt();
        int j_start = in.nextInt();
        int i_end = in.nextInt();
        int j_end = in.nextInt();
        printShortestPath(n, i_start, j_start, i_end, j_end);
        in.close();
    }
}