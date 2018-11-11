import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {

    private static final int modulus = 1000000007;
    private static int[] knowns;              // known numbers, (input order)
    private static int[] gkArr;               // gkArr[k] number of knowns after k in x s.t k>i_i
    private static int[] guArr;               // guArr[k] number of unknowns s.t. k>u
    private static int[] remainingUnknownsArr;// number of unknowns in x[n-1-i:x.length]
    private static long[] factorials;         // factorials[i] = i! % modulus
    private static long[] runningDiffs;        // sum over remaining k of (U.len-guArr)
    private static long[] EO;                 // Expected ordinals
    //O(n+klgk) sum = |U|!(1+sum over n of n!*EO_n) % modulus
    static long solve(int[] x) { //O(n + klgk)
        int n = x.length;
        long sum = 1L;
        factorialsInit(n); //O(n)
        int[] U = getUnknownInts(n, x); //O(n) - relies on x
        knownsInit(x); //O(n) - relies on x
        gkInit(n); //O(klgk) - relies on knowns, x
        guInit(n, U); //O(n) - relies on unknowns, x
        unknownsRemainingInit(x); //O(n) -relies on x
        runningDiffsInit(x, U); //O(n) -relies on gu, x
        EOInit(x, n, U); //O(n) - relies on knowns, unknowns, gk, gu, running sums, x
        for(int i = 1; i < n; i++) //O(n)                   
            sum = addMod(sum, mulMod(EO[i], factorials[i]));
        sum = mulMod(sum, factorials[U.length]);
        return sum;
    }
    //O(klgk) setup GOOD
    private static void gkInit(int n) {
        gkArr = new int[n+1];
        int[][] arrs = new int[2][knowns.length];
        arrs[0] = Arrays.copyOfRange(knowns, 0, knowns.length); 
        int subLen = 1;
        int b = 0;
        do {
            int i = 0;
            subLen *= 2;
            int j = (subLen >>> 1);
            int endSub = subLen;
            int counter = 0;
            int imin = Math.min(knowns.length, endSub - (subLen>>>1));
            int jmin = Math.min(knowns.length, endSub);
            while(counter < knowns.length) {
                if(j < jmin && i < imin && arrs[b][i] < arrs[b][j]) {
                    arrs[(b+1)%2][counter] = arrs[b][i];
                    gkArr[arrs[b][i]] += Math.max(0, (counter++)-i++);
                } else if(j < jmin) {
                    arrs[(b+1)%2][counter] = arrs[b][j];
                    gkArr[arrs[b][j]] += Math.max(0, (counter++)-j++);
                } else if(i < imin) {
                    arrs[(b+1)%2][counter] = arrs[b][i];
                    gkArr[arrs[b][i]] += Math.max(0, (counter++)-i++);
                } else {
                    endSub += subLen;
                    i = j;
                    j += (subLen>>>1);
                    imin = Math.min(knowns.length, endSub - (subLen>>>1));
                    jmin = Math.min(knowns.length, endSub);
                }
            }
            b = (b+1)%2;
        } while (subLen < knowns.length);
    }
    //O(n) setup
    private static void runningDiffsInit(int[] x, int[] U) { //Sum over k of U_g
        runningDiffs = new long[x.length];
        runningDiffs[0] = (x[x.length-1] == 0) ? 0 : U.length - guArr[x[x.length-1]]; 
        for(int i = 1; i < x.length; i++) {
            if(x[x.length-1-i] != 0)
                runningDiffs[i] = U.length - guArr[x[x.length-1-i]];
            runningDiffs[i] = addMod(runningDiffs[i], runningDiffs[i-1]);
        }
    }
    //O(n) setup GOOD
    private static void unknownsRemainingInit(int[] x) {
        remainingUnknownsArr = new int[x.length];
        int u = 0;
        for(int i = x.length-1; i >= 0; i--)
            remainingUnknownsArr[i] = (x[i] == 0) ? u++ : u; //INCLUSIVE
    }
    //O(n) setup GOOD
    private static void guInit(int n, int[] U) {
        guArr = new int[n+1];
        int k = 0;
        int u = 0;
        for(int i = 0; i < U.length; i++) {
            while( k <= U[i])
                guArr[k++] = u;
            u++;
        }
         while( k < guArr.length)
                guArr[k++] = u;
    }
    //O(n) setup
    private static void EOInit(int[] x, int n , int[] U) {
        EO = new long[x.length];
        long d = 0L;
        long invertedUlen = binaryExpMod(U.length, Solution.modulus-2L);
        for(int i = 1; i < n; i++) {
            if(x[n-1-i] == 0) {
                //from unknown perms 
                EO[i] = mulMod(remainingUnknownsArr[n-1-i], 500000004L); // div by 2
                //from knowns DP 
                d = mulMod(runningDiffs[i], invertedUlen);
                EO[i] = addMod(EO[i], d);
            } else {
                //fraction of unknowns larger
                d = mulMod(guArr[x[n-1-i]], invertedUlen);
                EO[i] = addMod(EO[i], mulMod(remainingUnknownsArr[n-1-i], d));
                //number of knowns larger
                EO[i] = addMod(EO[i], gkArr[x[n-1-i]]);
            }
        }
    }
    //O(lgn) GOOD
    private static long binaryExpMod(long l, long pow) { //l^(modulus-2) mod modulus
        if (l == 0L && pow != 0L)
            return 0L;
        long[] squares = new long[30];         //30 = ciel(lg(modulus-2)) > ciel(lg(n))
        squares[0] = l % Solution.modulus;
        for(int i = 1; i < 30; i++) 
            squares[i] = mulMod(squares[i-1], squares[i-1]);
        long result = 1L;
        int i = 0;
        while(pow != 0L) {
            if((pow & 1L) == 1L)
                result = mulMod(result, squares[i]);
            i++;
            pow >>>= 1;
        }
        return result;
    }
    //O(n) setup 
    private static void factorialsInit(int n) {
        factorials = new long[n+1];
        factorials[0] = 1L;
        factorials[1] = 1L;
        for(int i = 2; i <= n; i++)
            factorials[i] = Solution.mulMod(factorials[i-1], i);
    }
    //O(1) GOOD
    private static long mulMod(long result, long l) {
        return ( (result%Solution.modulus) * (l%Solution.modulus) )%Solution.modulus;
    }
    //O(1) GOOD
    private static long addMod(long result, long l) {
        return ( (result%Solution.modulus) + (l%Solution.modulus) )%Solution.modulus;
    }
    //O(n) setup GOOD
    private static int[] getUnknownInts(int n, int[] x) { //O(n) but setup so insignif
        int[] ints = new int[n];    
        for(int i = 1; i <= n; i++)
            ints[i-1] = i;
        for(int i: x)
            if(i != 0) {
                ints[i-1] = 0;
                n--;
            }
        int[] intsOut = new int[n];
        n = 0; //becomes index
        for(int i: ints) 
            if(i != 0)
                intsOut[n++] = i;
        return intsOut;
    }
    //O(n) setup GOOD
    private static void knownsInit(int[] x) {
        int counter = 0;
        for(int a: x) 
            if(a > 0)
                counter++;
        knowns = new int[counter];
        counter = 0;
        for(int a: x)
            if(a > 0)
                knowns[counter++] = a;
    } 
    
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] a = new int[n];

        String[] aItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int aItem = Integer.parseInt(aItems[i]);
            a[i] = aItem;
        }

        long result = solve(a);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
