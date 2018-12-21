
import java.io.BufferedReader; 
import java.io.InputStreamReader; 
import java.util.Vector; 
  
class Test 
{ 
    static final int MOD = 1000000007; 
      
    // for input 
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
      
    // capList[i]'th vector contains the list of persons having a cap with id i 
    // id is between 1 to 100 so we declared an array of 101 vectors as indexing 
    // starts from 0. 
    static Vector<Integer> capList[] = new Vector[101]; 
      
       
    // dp[2^10][101] .. in dp[i][j], i denotes the mask i.e., it tells that 
    // how many and which persons are wearing cap. j denotes the first j caps 
    // used. So, dp[i][j] tells the number ways we assign j caps to mask i 
    // such that none of them wears the same cap 
    static int dp[][] = new int[1025][101]; 
       
    // This is used for base case, it has all the N bits set 
    // so, it tells whether all N persons are wearing a cap. 
    static int allmask; 
       
    // Mask is the set of persons, i is cap-id (OR the  
    // number of caps processed starting from first cap). 
    static long countWaysUtil(int mask, int i) 
    { 
        // If all persons are wearing a cap so we 
        // are done and this is one way so return 1 
        if (mask == allmask) return 1; 
       
        // If not everyone is wearing a cap and also there are no more 
        // caps left to process, so there is no way, thus return 0; 
        if (i > 100) return 0; 
       
        // If we already have solved this subproblem, return the answer. 
        if (dp[mask][i] != -1) return dp[mask][i]; 
       
        // Ways, when we don't include this cap in our arrangement 
        // or solution set. 
        long ways = countWaysUtil(mask, i+1); 
       
        // size is the total number of persons having cap with id i. 
        int size = capList[i].size(); 
       
        // So, assign one by one ith cap to all the possible persons 
        // and recur for remaining caps. 
        for (int j = 0; j < size; j++) 
        { 
            // if person capList[i][j] is already wearing a cap so continue as 
            // we cannot assign him this cap 
            if ((mask & (1 << capList[i].get(j))) != 0) continue; 
       
            // Else assign him this cap and recur for remaining caps with 
            // new updated mask vector 
            else ways += countWaysUtil(mask | (1 << capList[i].get(j)), i+1); 
            ways %= MOD; 
        } 
       
        // Save the result and return it. 
        return dp[mask][i] = (int) ways; 
    } 
       
    // Reads n lines from standard input for current test case 
    static void countWays(int n) throws Exception 
    { 
        //----------- READ INPUT -------------------------- 
        String str; 
        String split[]; 
        int x; 
                
        for (int i=0; i<n; i++) 
        { 
            str = br.readLine(); 
            split = str.split(" "); 
            
            // while there are words in the split[] 
            for (int j = 0; j < split.length; j++) { 
                 // add the ith person in the list of cap if with id x 
                x = Integer.parseInt(split[j]); 
                capList[x].add(i); 
            } 
            
        } 
        //---------------------------------------------------- 
       
        // All mask is used to check of all persons 
        // are included or not, set all n bits as 1 
        allmask = (1 << n) - 1; 
       
        // Initialize all entries in dp as -1 
        for (int[] is : dp) { 
            for (int i = 0; i < is.length; i++) { 
                is[i] = -1; 
            } 
        } 
       
        // Call recursive function count ways 
        System.out.println(countWaysUtil(0, 1)); 
    } 
  
    // Driver method 
    public static void main(String args[]) throws Exception 
    { 
        int n;   // number of persons in every test case 
          
        // initializing vector array 
        for (int i = 0; i < capList.length; i++) 
            capList[i] = new Vector<>(); 
          
          
        n = Integer.parseInt(br.readLine()); 
        countWays(n); 
    } 
}