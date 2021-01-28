import java.io.*;
import java.util.*;
import java.math.*;

public class Vic {

     static long Solve(int L, int A, int N, int D) {           

            if(D<2 || N>L)  // no constraint; use only element A
            	 return (long)L*A;
            else if(A<D || D>N) return -1; // no solution
            else { 
            	// a solution is always 
            	// bestsum of the best set of L elements where each subset 
              long bestsum = 0;
              for(int i = (int)Math.ceil((L-N+1.0)/(A-D+1.0)); i <= (N-1)/(D-1); i++){
            	int used = N-i*(D-2)-1; // number of A; used <= L.
            	long sum = (long)used*A;
            	if(D==2 && used>i){i=used;};//since i has not occurred
            	// the next num values A-1..A-num are taken min times
            	long num = (L-used)/i; //integer division will round down
            	sum += (num*i*(2*A-1-num))/2; 
            	used += num*i;
            	// a last value keeps the rest
            	sum += (L-used)*(A-num-1);
            	if(sum>bestsum) bestsum=sum;
              }
              return bestsum;
            }          	       		           	
        } //Solve

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        for(int a0 = 0; a0 < T; a0++){
            int L = in.nextInt();
            int A = in.nextInt();
            int N = in.nextInt();
            int D = in.nextInt();
            long res = Solve(L,A,N,D);
            if(res<=0)
            	System.out.println("SAD");
            else
            	System.out.println(res);
        } // for
    } //main
    
}