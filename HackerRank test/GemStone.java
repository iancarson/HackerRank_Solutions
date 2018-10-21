import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        input.nextLine();
        
        Set<Character> gemstones = stringToSet(input.nextLine()); //Set of gemstones
        
        for(int i=1; i<n ;i++){
            gemstones.retainAll(stringToSet(input.nextLine())); //Perform intersection
        }
        System.out.print(gemstones.size());
    }
    
    
    
    
    public static Set<Character> stringToSet(String s) //Converts String to Character set
    {
        Set<Character> set = new HashSet<Character>(26);
        for (char c : s.toCharArray())
            set.add(Character.valueOf(c));
        return set;
    }
}