import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'maxRegion' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts 2D_INTEGER_ARRAY grid as parameter.
     */

    static int count(int x, int y, int[][] g) {
  if(x < 0 || x >= g.length || y < 0 || y >= g[0].length || g[x][y] == 0)
    return 0;
    
  g[x][y] = 0;
  return 1 +
    count(x-1, y-1, g) + count(x, y-1, g) + count(x+1, y-1, g) +
    count(x-1, y, g) + count(x+1, y, g) +
    count(x-1, y+1, g) + count(x, y+1, g) + count(x+1, y+1, g);
}

static int solution(int[][] g, int n, int m) {
  int maxC = 0;
  for(int x=0; x < n; x++){
    for(int y=0; y < m; y++) {
      maxC = Math.max(maxC, count(x, y, g));
    }
  }
  return maxC;
}

}

public class Solution {
    public static void main(String[] args) throws IOException {
       Scanner in = new Scanner(System.in);
       int n = in.nextInt();
       int m = in.nextInt();
       int grid [] [] = new int [n][m];
       for(int i = 0; i < n; i++)
       {
           for(int j = 0; j < m; j ++)
           {
               grid[i][j] = in.nextInt();
           }
       }
       Result result = new Result();
    
    int res = result.solution(grid, n, m);
    System.out.println(res);
    System.out.println();
}
}
