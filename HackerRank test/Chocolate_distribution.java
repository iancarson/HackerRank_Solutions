import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.math.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class soluion
{
	static int  combinationUtil(int arr[], int n, int r, int index, int data[], int i)
	{
		int max = 0;
		Arrays.sort(data);
		if( index == r)
		{
			for(int j = 0; j < r; j++)
				max = data[data.length] - data[0];
			return max;

		}
		//When no more elements are there to put in data[]
		if( i >= n)
			return max;
		//current is included, put next at next location
		data[index] = arr[i];
		int temp = combinationUtil(arr, n, r, index +  1, data, i + 1);
		max = Math.max(max,temp);
		//current is excluded, replace it with next
		//(Note that i + 1 is passed, but index is not changed)
		int temp2 = combinationUtil(arr,n, r, index, data, i + 1);
		return Math.max(max, temp2);
	}
	static void printCombination(int arr[], int n, int r)
	{
		int data[] = new int[r];
		int max = combinationUtil(arr, n, r,0, data, 0);
		System.out.println(max);
	}
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = br.read();
		while( T -> 0)
		{
			String [] temp = br.readLine().split(" ");
			int [] arr = new int[temp.length]
			for(int i = 0; i < temp.length; i ++)
			{
				arr[i] = Integer.parseInt(temp[i]);
			}
			int M = br.read();
			printCombination(arr, arr.length,M);
		}
	}
}