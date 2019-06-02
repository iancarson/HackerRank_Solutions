import java.util.Scanner;
import java.util.Arrays;
import java.lang.IndexOutOfBoundsException;

public class Solution
{
    private static boolean isSolvable(int m, int[] arr, int i)
    {
        if(i < 0 || arr[i] == 1) return false;
        if((i == arr.length -1) || i + m > arr.length - 1) return true;
        arr[i] = 1;
        return isSolvable(m, arr, i + 1) || isSolvable(m, arr, i -1) || isSolvable(m, arr, i + m);
    }
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        for(int i = 0; i < T ; i++)
        {
            int size = in.nextInt();
            int leap = in.nextInt();
            int arr [] = new int[size];
            for(int j = 0; j < size; j++)
            {
                arr[j] = in.nextInt();
            }
            int start = 0;
            if(isSolvable(leap,arr,start))
                System.out.println("YES");
                else
            System.out.println("NO");
        }
    }
}
