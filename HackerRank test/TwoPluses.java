import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {

// Complete the twoPluses function below.
static int twoPluses(String[] grid) {

   List<Set<String>> list = new ArrayList();

    int r = grid.length;
    int c = grid[0].length();
    for(int i=0;i<r;i++){
        for(int j=0;j<c;j++){
             getMaxPlus(i,j,r,c,grid,list);
        }
    }

    int max = 0;
    for(int i = 0; i < list.size(); i++) {
        Set<String> p1 = list.get(i);

        for(int j = 0; j < list.size(); j++) {
            Set<String> p2 = list.get(j);
            int prod = p1.size() * p2.size(); 

            if(prod > max && !overlaps(p1,p2)) {
                max = prod;
            }  
        }
    }

    return max;

}

private static boolean overlaps(Set<String> p1, Set<String> p2){

    for(String s: p2){
        if(p1.contains(s)) return true;
    }

    return false;

}

private static void getMaxPlus(int i,int j,int r,int c, String[] grid,List<Set<String>> list){
     List<Set<String>> tmp = new ArrayList();
    char a = grid[i].charAt(j);

     Set<String> set = new HashSet();
    if(a == 'B') return;

    int left = j -1;
    int right = j + 1;

    int top = i -1;
    int down = i + 1;
    set.add("" + i + j);
    tmp.add(set);
    while(true){

        if(left < 0) break;
        if(right >= c) break;
         if(top < 0) break;
         if(down >= r) break;

         char leftC = grid[i].charAt(left);
    if(leftC == 'B') break;

    char rightC = grid[i].charAt(right);
    if(rightC == 'B') break;

    char topC = grid[top].charAt(j);
    if(topC == 'B') break;

    char downC = grid[down].charAt(j);
    if(downC == 'B') break;

        set = new HashSet();
        tmp.add(set);


        for(Set<String> s1:tmp){
            for(String s:s1){
                set.add(s);
            }
        }

        set.add("" + i + left);
         set.add("" + i + right);
         set.add("" + top + j);
         set.add("" + down + j);


        left = left -1;
         right = right + 1;
        top = top - 1;
        down = down + 1;

    }

    list.addAll(tmp);


}

private static final Scanner scanner = new Scanner(System.in);

public static void main(String[] args) throws IOException {
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

    String[] nm = scanner.nextLine().split(" ");

    int n = Integer.parseInt(nm[0]);

    int m = Integer.parseInt(nm[1]);

    String[] grid = new String[n];

    for (int i = 0; i < n; i++) {
        String gridItem = scanner.nextLine();
        grid[i] = gridItem;
    }

    int result = twoPluses(grid);

    bufferedWriter.write(String.valueOf(result));
    bufferedWriter.newLine();

    bufferedWriter.close();

    scanner.close();
}

}
