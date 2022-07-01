import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {

    /*
     * Complete the 'whatFlavors' function below.
     *
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY cost
     *  2. INTEGER money
     */

    public static void whatFlavors(List<Integer> cost, int money) {
    // Write your code here
    Map<Integer, Integer> trips = new HashMap<>();
    int index = 0;
    int iindex = 0;
    for(int i = 0; i < cost.size(); i ++)
    {
        trips.put(cost.get(i), i);
    }
    for ( int i = 0; i < cost.size(); i++)
    {
        int complement = money - cost.get(i);
        if(trips.containsKey(complement) && trips.get(complement) != i)
        {
            //int arr [] = new int []{i, trips.get(complement)};
             index = i + 1;
            iindex = trips.get(complement) + 1;
            
        }
    }
    if(index < iindex)
    System.out.println(index+ " " + iindex);
    else
     System.out.println(iindex+ " " + index);

    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        for (int tItr = 0; tItr < t; tItr++) {
            int money = Integer.parseInt(bufferedReader.readLine().trim());

            int n = Integer.parseInt(bufferedReader.readLine().trim());

            String[] costTemp = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

            List<Integer> cost = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                int costItem = Integer.parseInt(costTemp[i]);
                cost.add(costItem);
            }

            Result.whatFlavors(cost, money);
        }

        bufferedReader.close();
    }
}
