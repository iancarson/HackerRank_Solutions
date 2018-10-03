import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;
import java.text.*;
public class Solution {
public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    String grid[] = new String[n];
    for(int grid_i=0; grid_i < n; grid_i++)
    {
        grid[grid_i] = in.next();
    }
for(int i=1; i<n-1; i++)
{
        for(int j=1; j<n-1; j++)
        {
            int left = Character.getNumericValue(grid[i].charAt(j-1));
            int right = Character.getNumericValue(grid[i].charAt(j+1));
            int up = Character.getNumericValue(grid[i-1].charAt(j));
            int down = Character.getNumericValue(grid[i+1].charAt(j));
            int middle = Character.getNumericValue(grid[i].charAt(j));
            char mid = grid[i].charAt(j);

            if(left<middle && up<middle && right<middle && down<middle)
            {
                StringBuilder sb = new StringBuilder(grid[i]);
                    sb.setCharAt(j, 'X');
                    grid[i] = sb.toString();
            }
        }
    }
    for(int grid_i=0; grid_i < n; grid_i++)
    {
        System.out.println(grid[grid_i]);
    }
}

}
