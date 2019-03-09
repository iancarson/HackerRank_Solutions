import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner in = new Scanner(System.in);
        BigInteger a = in.nextBigInteger(); 
        BigInteger b = in.nextBigInteger();
        System.out.println(a.add(b));
        System.out.println(a.multiply(b));
    }
}
