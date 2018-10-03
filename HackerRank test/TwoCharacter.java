import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int len = in.nextInt();
        String s = in.next();
        int maxPattern = 0;
        
        if(s.length() == 1)//Edge case where length is 1
        {
            System.out.println(maxPattern);
            System.exit(0);
        }
        
        //Loop through all letter pairs
        for(int i = 0; i < 26; i++)
        {
            nextLetter:
            for(int j = i + 1; j < 26; j++)
            {
                char one = (char) ('a' + i); //First char in pair
                char two = (char) ('a' + j); //Second char in pair
                char lastSeen = '\u0000';
                int patternLength = 0;
                
                for(char letter : s.toCharArray())
                {
                    if(letter == one || letter == two)
                    {
                        if(letter == lastSeen)//Duplicate found
                        {
                            continue nextLetter;
                        }
                        //Not a duplicate
                        patternLength++;
                        lastSeen = letter;
                    }
                }//for char : s
                
                maxPattern = (patternLength > maxPattern) ? patternLength : maxPattern; //Keep a running max
                
            }//for j
        }//for i
        
        System.out.println(maxPattern);
        
    }
}