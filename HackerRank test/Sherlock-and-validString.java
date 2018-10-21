import java.util.*;
import java.math.*;

public class Solution {
   
    static boolean isValid(String s)
    {
        HashMap<Character,Integer> map=new HashMap<Character,Integer>();
        int max=Integer.MIN_VALUE;
        int min=Integer.MAX_VALUE;
        for(int i=0;i<s.length();i++)
        {
            if(map.containsKey(s.charAt(i)))
                map.put(s.charAt(i),map.get(s.charAt(i))+1);
            else
                map.put(s.charAt(i),1);
            max=Math.max(max,map.get(s.charAt(i)));
        }
 
        int count1=0,count2=0;
         for (Map.Entry m:map.entrySet())
             min=Math.min(min,(int)m.getValue());
        
           for (Map.Entry m:map.entrySet())
         {
             int val=(int)m.getValue();
            if(val==max)
                 count1++;
             else if(val==min)
                 count2++;
         }
        
        if(max-min==0 || (count1==map.size()-1 && min==1) || (count2==map.size()-1 && max-min==1))
            return true;
        
        return false;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.next();
        if(isValid(s))
        System.out.println("YES");
        else
        System.out.println("NO");    

    }
}