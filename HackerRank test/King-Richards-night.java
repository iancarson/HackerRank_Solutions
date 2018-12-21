import java.util.Scanner;

/**
 * All squares (original & sub-squares) has these following characteristics:
 * [1]. sorted entries
 * + right entry - left entry = 1
 * + below entry - above entry = dimension
 * [2]. if we know smallest entry (T), dimension (N) & orientation -> we can know the rest entries
 */
public class Solution {
	public static void main(String[] args) {
	//Step 1. Find smallest entry of each input square. Time complexity: O(S)
	//The value at cell (i,j) of a great square whose smallest entry is T is T + i*N + j
		
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); //N: size of original square
        int s = scanner.nextInt(); //S: numbers of commands
        
        long[] a = new long[s+1]; //entered rows
        long[] b = new long[s+1]; //entered columns
        long[] d = new long[s+1]; //entered dimension
        long[] t = new long[s+1]; //T: found smallest entries
        
        //init the original square (already known)
        a[0] = 1;
        b[0] = 1;
        d[0] = n-1;
        t[0] = 0;
        
        //input and find smallest entries
        int orientationNum = 4;
        for (int i = 1; i <= s; i++) {
            a[i] = scanner.nextInt();
            b[i] = scanner.nextInt();
            d[i] = scanner.nextInt();
            
            /* for example: T0 = 0, N = 7
             * 0	1 	2 	3 	4 	5 	6
	     * 7	8 	9 	10 	11 	12 	13
	     * 14	15	16	17 	18 	19	20
	     * 21	22	23	24 	25 	26 	27
	     * 28	29	30	31 	32 	33 	34
	     * 35	36	37	38	39	40	41
	     * 42	43	44	45	46	47	48
	     * */
            
            //apply characteristic [2] and formula: V = T + i*N + j
            if (i % orientationNum == 1) {//TOP
            	
            	/* command (1,2,4) -> T1 = 0 + (1-1)*7 + (2-1) = 1 
            	/* %	29 	22 	15 	8 	1 	%
	         * %	30 	23 	16 	9 	2 	%
	         * %	31	24	17 	10 	3	%
	         * %	32	25	28 	11 	4 	%
	         * %	33	26	19 	12 	5 	%
	         * %	%	%	%	%	%	%
	         * %	%	%	%	%	%	%
	         * */
                t[i] = t[i-1] + (a[i]-a[i-1])*n + (b[i]-b[i-1]);
            } else if (i % orientationNum == 2) {//RIGHT

            	 /* command (2,3,3) -> T2 = 1 + ((2+4)-(3+3))*7 + (2-1) = 2 
            	 /* %	% 	% 	% 	% 	% 	%
	          * %	% 	26 	25 	24 	23	%
	          * %	%	19	18 	17 	16	%
	          * %	%	12	11 	10 	9 	%
	          * %	%	5	4 	3 	2 	%
	          * %	%	%	%	%	%	%
	          * %	%	%	%	%	%	%
	          * */
                t[i] = t[i-1]+((b[i-1]+d[i-1])-(b[i]+d[i]))*n+(a[i]-a[i-1]);
            } else if (i % orientationNum == 3) {//BOTTOM
                t[i] = t[i-1]+((a[i-1]+d[i-1])-(a[i]+d[i]))*n+((b[i-1]+d[i-1])-(b[i]+d[i]));
            } else if (i % orientationNum == 0) {//LEFT
                t[i] = t[i-1]+(b[i]-b[i-1])*n+((a[i-1]+d[i-1])-(a[i]+d[i]));
            }
        }
		
	//Step 2. Determine some values
	//Each square is contained in the previous one -> binary search. Time complexity: O(log S)
	//The location of a value v in a great square whose smallest entry is T is ((v-T)/N, (v-T)%N)
        
        int l = scanner.nextInt(); //L: number of queries
        long[] w = new long[l];
        for (int i = 0; i < l; i++) {
            w[i] = scanner.nextLong();
            int low = 0;
            int high = s;
            
            //find position of smallest entry
            while (low != high) {
                int mid = (low+high+1)/2;
                if (w[i] >= t[mid] && w[i] < t[mid]+(d[mid]+1)*n && w[i]%n >= t[mid]%n && w[i]%n <= (t[mid]%n)+d[mid]){
                    low = mid;
                }else{
                    high = mid-1;
                }
            }
            
            //find position of input number with formula: (i, j) = ((v-T)/N, (v-T)%N)
            long off1 = (w[i]-t[low])/n;
            long off2 = (w[i]-t[low])%n;
            
            if (low % orientationNum == 0) {//TOP
                System.out.println((a[low]+off1)+" "+(b[low]+off2));
            } else if (low % orientationNum == 1) {//RIGHT
            	System.out.println((a[low]+off2)+" "+(b[low]+d[low]-off1));
            } else if (low % orientationNum == 2) {//BOTTOM
                System.out.println((a[low]+d[low]-off1)+" "+(b[low]+d[low]-off2));
            } else if (low % orientationNum == 3) {//LEFT
                System.out.println((a[low]+d[low]-off2)+" "+(b[low]+off1));
            }
        }
        
        scanner.close();
	}
}