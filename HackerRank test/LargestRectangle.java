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
     * Complete the 'largestRectangle' function below.
     *
     * The function is expected to return a LONG_INTEGER.
     * The function accepts INTEGER_ARRAY h as parameter.
     */

  public static long largestRectangle(List<Integer> h) {
    // Write your code here
    Stack<Integer> st1 = new Stack<>();
    int i = 0;
    int topIndex = 0;
    long area;
    long finalArea=0;
    while(i<h.size()){
      if(st1.isEmpty() || h.get(st1.peek()) <= h.get(i)){
        st1.push(i++);
      }else{
        topIndex = st1.peek();
        st1.pop();
        
        area = h.get(topIndex) * (st1.isEmpty() ? i : i-1-st1.peek());
        if(area > finalArea ){
          finalArea = area;
        }
      }
    }
    while(st1.isEmpty()==false){
      topIndex = st1.peek();
      st1.pop();
      area = h.get(topIndex) * (st1.isEmpty() ? i : i-1-st1.peek());
        if(area > finalArea ){
          finalArea = area;
        }
    }
    return finalArea;

    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> h = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        long result = Result.largestRectangle(h);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
