import java.io.InputStreamReader;
import java.util.Scanner;

public class Solution {
    private static int[] allPrefixes(String p) {
        int[] Z = new int[p.length()];

        Z[0] = p.length();
        int a = 0, b = 0;
        for (int i = 1; i < p.length(); ++i) {
            int k = i < b ? Math.min(b - i, Z[i - a]) : 0;
            while (i + k < p.length() && p.charAt(i + k) == p.charAt(k))
                ++k;
            Z[i] = k;
            if (i + k > b) {
                a = i;
                b = i + k;
            }
        }
        return Z;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(new InputStreamReader(System.in));

        int tests = s.nextInt();
        for (int t = 0; t < tests; t++) {
            String p = s.next();
            String v = s.next();
            int count=0;
            int len = v.length();
          
            if(!p.contains(v))
            {
                System.out.println("No Match!");
                 
            }
            else{
            // prepend v and a delimiter to p, then use Z algorithm from String Similarity    
            int[] Z = allPrefixes(v + "@" + p);
            int[] Zrev = allPrefixes(new StringBuffer(v).reverse().append("@").append(new StringBuffer(p).reverse())
                    .toString());

            // copy Z arrays to remove v and delimiter entries to make indices easier to understand
            int[] prefixLengths = new int[p.length()];
            System.arraycopy(Z, len + 1, prefixLengths, 0, p.length());
            int[] suffixLengths = new int[p.length()];
            System.arraycopy(Zrev, len + 1, suffixLengths, 0, p.length());

            boolean hasOutput = false;
            for (int i = 0; i < p.length() - len + 1; i++) {
                int prefixLength = prefixLengths[i];
                int suffixLength = suffixLengths[p.length() - i - len];

                if (prefixLength >= len - 1) {
                    System.out.print((hasOutput ? " " : "") + i);
                    hasOutput = true;
                } else {
                    if (suffixLength == len - prefixLength - 1) {
                        System.out.print((hasOutput ? " " : "") + i);
                        hasOutput = true;
                    }
                }
            
            }
            }
            System.out.print("\n");
        }
    }
}
