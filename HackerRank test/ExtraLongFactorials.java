import java.util.*;
import java.io.*;
import java.util.regex.*;
import java.math.*;
public class extraLongFactorials
{
  static BigInteger factorial(int n)
  {
    BigInteger fact=new BigInteger("1");
    for(int i=2;i<=n;i++)
     fact=fact.multiply(BigInteger.valueOf(i));
    return fact;
  }
  public static void main(String[] args)
  {
    Scanner in=new Scanner(System.in);
    int n=in.nextInt();
    factorial(n);
    System.out.println(factorial(n));
  }
}