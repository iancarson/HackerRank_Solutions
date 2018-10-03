import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        for(int a0 = 0; a0 < T; a0++){
            int n = in.nextInt();
            int a = in.nextInt();
            int b = in.nextInt();
            
            for(int i=n-1; i>-1; i--){
                if(a<b)
                    System.out.print(((a*i)+(b*(n-1-i)))+" ");
                else if(a>b)
                    System.out.print(((b*i)+(a*(n-1-i)))+" ");
                else{
                    System.out.print(((a*i)+(b*(n-1-i))));
                    break;
                }
            }
            System.out.println();
        }
        in.close();
    }
}