import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class MainClass31 {
	public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int tries = in.nextInt();
        for(int i=0;i<tries;i++) {
        	int numbers=in.nextInt();
        	int[] arrayNumbs=new int[numbers];
        	for (int y=0;y<numbers;y++) {
        		arrayNumbs[y]=in.nextInt();
        	}
        	
        	int matches=1;
        	while(matches>0) {
        		int action=0;
        		for (int y=0;y<(numbers-2);y++) {
            		int smallestNumber=0;
            		if ( arrayNumbs[y]<arrayNumbs[y+1] ) {
            			smallestNumber=arrayNumbs[y];
            		} else {
            			smallestNumber=arrayNumbs[y+1];
            		}
            		
            		if ( smallestNumber>arrayNumbs[y+2] ) {
            			smallestNumber=arrayNumbs[y+2];
            		}
            		
            		int firstN=arrayNumbs[y];
    				int secondN=arrayNumbs[y+1];
    				int thirdN=arrayNumbs[y+2];
            		
        			if (smallestNumber==arrayNumbs[y+1]) {
        				action++;
    					arrayNumbs[y]=secondN;
    					arrayNumbs[y+1]=thirdN;
    					arrayNumbs[y+2]=firstN;
    					
        			} else if (smallestNumber==arrayNumbs[y+2]) {
        				action++;
        				arrayNumbs[y]=thirdN;
    					arrayNumbs[y+1]=firstN;
    					arrayNumbs[y+2]=secondN;
        			}
            	}
        		matches=action;
        	}
        	
        	if ( arrayNumbs[arrayNumbs.length-1]>arrayNumbs[arrayNumbs.length-2] ) {
        		System.out.print("YES\n");
        	} else {
        		System.out.print("NO\n");
        	}
//        	System.out.print("\n");
//        	System.out.print(Arrays.toString(arrayNumbs));
        }
        
	}
}