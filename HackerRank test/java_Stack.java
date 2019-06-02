import java.util.Stack;
import java.util.Scanner;

public class JavaStack {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
       /* Stack<String> stack = new Stack<>();
        stack.push("{}()");
        stack.push("({()})");
        stack.push("{}(");
        stack.push("[]");
        */
        while(in.hasNext())
        {
            String input = in.next();
               while(input.length() != (input = input.replaceAll("\\(\\)|\\[\\]|\\{\\}", "")).length());
    System.out.println(input.isEmpty());
            }
    }

  /*  private static boolean balancedString(String string){
        while(string.length() != (string = string.replaceAll("\\(\\)|\\[\\]|\\{\\}", "")).length());
        return (string.isEmpty());
    }
    */
}
