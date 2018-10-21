import java.util.Scanner;

public class Solution{
       public static void main(String[] args) {
              Scanner sc = new Scanner(System.in);
              int T = sc.nextInt();
              for (int count = 0; count < T; count++) {
                     char[] characters= sc.next().toCharArray();
                      int AlterCount=0;
                     for(int i=0;i<characters.length-1;i++) {
                           if(characters[i]==characters[i+1]) {
                                  AlterCount++;
                           }
                     }
                     System.out.println(AlterCount);
              }
              sc.close();
       }
}