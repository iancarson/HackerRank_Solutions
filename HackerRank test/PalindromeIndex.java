import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner input = new Scanner(System.in);
        int n = input.nextInt(); input.nextLine();
        
        tests:
        for(int t = 0; t < n; t++)
        {
            
            String s = input.nextLine();
            int outputIndex = -1;
            boolean removal = false;
            
            for(int i = 0, j = s.length()-1; i < j; i++, j--)
            {
                
                if(s.charAt(i) != s.charAt(j))
                {

                    if(removal)
                    {
                        removal = false;
                        outputIndex = -1;
                        break;
                    }
                    
                    if(s.charAt(i+1) == s.charAt(j))
                    {
                        removal = true; 
                        outputIndex = i;
                        i++;
                    }
                    else if(s.charAt(i) == s.charAt(j-1))
                    {
                        removal = true; 
                        outputIndex = j;
                        j--;
                    }
                    else
                    {
                        removal = false;
                        outputIndex = -1;
                        break;
                    }
                }
            }
            if(outputIndex != -1)
            {
                System.out.println(outputIndex); continue tests;    
            }
            
            
            //Case where we made the wrong removal decision and we could have made a palindrome
            
            for(int i = 0, j = s.length()-1; i < j; i++, j--)
            {
                
                if(s.charAt(i) != s.charAt(j))
                {

                    if(removal)
                    {
                        System.out.println(-1); continue tests;
                    }
                    
                    if(s.charAt(i) == s.charAt(j-1))
                    {
                        removal = true; 
                        outputIndex = j;
                        j--;
                    }
                    else if(s.charAt(i+1) == s.charAt(j))
                    {
                        removal = true; 
                        outputIndex = i;
                        i++;
                    }
                    else
                    {
                        System.out.println(-1); continue tests;
                    }
                }
            }
            System.out.println(outputIndex);
        }
    }
}