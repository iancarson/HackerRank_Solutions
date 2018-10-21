import java.util.TreeMap;
import java.util.Arrays; 
import java.util.Scanner;

public class Maximum_Palindromes 
{

static long Factorial(int n) {
    if(n < 0)
        return 0;
    else if(n == 1 || n == 0)
        return 1;
    else
        return n * Factorial(n - 1);
}

static int Mod_Number = (int)Math.pow(10, 9) + 7;
static long Maximum_Length_Palindromes(String S) {
    int counter = 1, Even_Total = 0, max = -1, max_count = 0;

    char[] Arr = S.toCharArray();
    Arrays.sort(Arr);
   TreeMap<Character, Integer> TM_Odd = new TreeMap<Character, Integer>();
    TreeMap<Character, Integer> TM_Even = new TreeMap<Character, Integer>();

    for(int i = 0; i < Arr.length; i++)
        if(i < Arr.length - 1 && Arr[i] == Arr[i + 1])
            counter++;
        else {
            if(counter % 2 == 0) {
                TM_Even.put(Arr[i], counter);
                Even_Total += counter;
            }
            else {
                TM_Odd.put(Arr[i], counter);
                if(counter > max)   {   max = counter;  max_count = 1;  }
                else if(counter == max) max_count++;
            }
            counter = 1;
        }
      long value = 0;
    if(Even_Total > 0) {
        int temp = Even_Total / 2;
        if(max > 0) temp += (max - 1) / 2;
        if(max_count == 0)  max_count = 1;

        value = Factorial(temp) * max_count;
    }
    else  {
        for(char c : TM_Odd.keySet())
            if(TM_Odd.get(c) == 1)  value++;
            else    value += Factorial(max - TM_Odd.get(c));
    }

    if(max > 0) value /= Factorial((max - 1) / 2);

    for(int i : TM_Even.values())
        value /= Factorial(i / 2);

    return value % Mod_Number;
}

public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    String S = scan.next();
    int queries = scan.nextInt();

    while(queries-- > 0) {
        int l = scan.nextInt() - 1;
        int r = scan.nextInt();

        System.out.println(Maximum_Length_Palindromes(S.substring(l, r)));
    }

    scan.close();
}

}
