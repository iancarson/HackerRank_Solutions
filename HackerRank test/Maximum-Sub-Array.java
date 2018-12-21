import java.util.Iterator;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;
 
 
public class MaximumSubArraySum {
 
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int q = sc.nextInt();
		long[] res = new long[q];
		for(int i=0;i<q;i++){
			int n = sc.nextInt();
			long m = sc.nextLong();
			long[] arr = new long[n];
			for(int j=0;j<n;j++){
				arr[j]=sc.nextLong();
			}
			res[i]= getMaxSum(n,m,arr);
 
		}
		for(int i=0;i<q;i++){
			System.out.println(res[i]);
		}
	}
 
	private static long getMaxSum(int n, long m, long[] arr) {
		long maxSum=0;
				
		TreeSet<Long> prefix = new TreeSet<Long>();
		long currentSum=0;
		for(int i=0;i<n;i++){
			currentSum=(currentSum+arr[i]%m)%m;
			SortedSet<Long> set = prefix.tailSet(currentSum+1);
			Iterator<Long> itr = set.iterator();
			if(itr.hasNext()){
				
				maxSum= Math.max(maxSum, (currentSum-itr.next()+m)%m);
			}
			
			maxSum=Math.max(maxSum, currentSum);
			prefix.add(currentSum);
		}
		
		
		return maxSum;
	}
 
}