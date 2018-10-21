import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        int i=1;
        Scanner in=new Scanner(System.in);
        while(in.hasNext())
        {
            System.out.println((i++) + " " + in.nextLine());
        }
    }
}