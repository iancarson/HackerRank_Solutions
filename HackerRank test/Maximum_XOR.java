import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {

    // Complete the maxXor function below.
   static class Bit {
        final Bit[] bits = new Bit[2];
    }
    
    static int[] maxXor(int[] arr, int[] queries) {
        int[] maxXor = new int[queries.length];
        Bit msb = new Bit();
        
        for (int a : arr) {
            Bit bit = msb;
            for (int s = 31; s >= 0; s--) {
                int b = (a >> s) & 1;
                if (bit.bits[b] == null)
                    bit.bits[b] = new Bit();
                bit = bit.bits[b];
            }
        }
        
        for (int q = 0; q < queries.length; q++) {
            int a = 0;
            Bit bit = msb;
            for (int s = 31; s >= 0; s--) {
                int b = (queries[q] >> s) & 1;
                if (bit.bits[1 - b] != null)
                    b = 1 - b;
                a = (a << 1) | b;
                bit = bit.bits[b];
            }
            
            maxXor[q] = a ^ queries[q];
        }
        
        return maxXor;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] arr = new int[n];

        String[] arrItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int arrItem = Integer.parseInt(arrItems[i]);
            arr[i] = arrItem;
        }

        int m = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] queries = new int[m];

        for (int i = 0; i < m; i++) {
            int queriesItem = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
            queries[i] = queriesItem;
        }

        int[] result = maxXor(arr, queries);

        for (int i = 0; i < result.length; i++) {
            bufferedWriter.write(String.valueOf(result[i]));

            if (i != result.length - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
