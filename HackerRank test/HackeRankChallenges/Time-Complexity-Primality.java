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
     * Complete the 'primality' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts INTEGER n as parameter.
     */

    public static String primality(int n) {
    // Write your code here
    if( n == 1)
    return "Not prime";
    int i = 2;
    while( i * i <= n)
    {
        if(n % i == 0 )
        return "Not prime";
        i += 1;
    }
    return "Prime";
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int p = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, p).forEach(pItr -> {
            try {
                int n = Integer.parseInt(bufferedReader.readLine().trim());

                String result = Result.primality(n);

                bufferedWriter.write(result);
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
