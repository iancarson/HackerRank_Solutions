import java.io.*;
import java.util.*;
public class Solution
{
  public static void main(String[] args) {
    /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
    Scanner sc = new Scanner (System.in);
    int T = sc.nextInt();
    for (int i = 0; i < T; i++) {
        Set<Character> charSet1 = toCharSet(sc.next());
        Set<Character> charSet2 = toCharSet(sc.next());

        charSet1.retainAll(charSet2);

        if (charSet1.size() > 0) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}

public static Set<Character> toCharSet(String word) {
    Set<Character> charSet = new HashSet<Character>();

    for (int i = 0; i < word.length(); i++) {
        charSet.add(word.charAt(i));
    }

    return charSet;

}
}