import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

 // Returns sum of arr[0..index]. This function assumes
    // that the array is preprocessed and partial sums of
    // array elements are stored in BITree[].
       static int getSum(int BITree[], int index)
    {
        int sum = 0; // Initialize result
     
        // Traverse ancestors of BITree[index]
        while (index > 0)
        {
            // Add current element of BITree to sum
            sum = sum + BITree[index];
     
            // Move index to parent node in getSum View
            index = index - (index & (-index));
        }
        return sum;
    }
     
    // Updates a node in Binary Index Tree (BITree) at given index
    // in BITree.  The given value 'val' is added to BITree[i] and
    // all of its ancestors in tree.
       static void updateBIT(int BITree[], int n, int index, int val)
    {
        // Traverse all ancestors and add 'val'
        while (index <= n)
        {
           // Add 'val' to current node of BI Tree
           BITree[index] = BITree[index] + val;
     
           // Update index to that of parent in update View
           index = index + (index & (-index));
        }
    }
     
    // Returns inversion count arr[0..n-1]
       static long getInvCount(int arr[], int n)
    {
        long invcount = 0; // Initialize result
     
        // Find maximum element in arr[]
        int maxElement = 0;
        for (int i=0; i<n; i++){
            if (maxElement < arr[i])
                maxElement = arr[i];
        }
        // Create a BIT with size equal to maxElement+1 (Extra
        // one is used so that elements can be directly be
        // used as index)
      int BIT[] = new int [maxElement+1];
        
      
      for (int i=1; i<=maxElement; i++){
            BIT[i] = 0;
      }
        // Traverse all elements from right.
        for (int i=n-1; i>=0; i--)
        {
            // Get count of elements smaller than arr[i]
            invcount = invcount + getSum(BIT, arr[i]-1);
     
            // Add current element to BIT
            updateBIT(BIT, maxElement, arr[i], 1);
        }
     
        return invcount;
    }
     
    // Driver program
    public static void main(String[]args){
    {
        Scanner s = new Scanner(System.in);
        
        //Total times element want to sorted
        int totalTime = s.nextInt();
        
        long inversion[] = new long[totalTime];
        
        int length = 0;
        
        
        for(int i=1;i<=totalTime;i++){
            //how many elements want to enter
            int formArray = s.nextInt();
            
            int data[] = new int[formArray];
            
            length = data.length;
            for(int j=0;j<formArray;j++){
                
                data[j] = s.nextInt();
                
            }//inner loop
        
            inversion[i-1] =    getInvCount(data,length);
            
            
        
        
        
        }//outer loop
        
        for(long k : inversion)
        System.out.println(k);      
    }
    }
}