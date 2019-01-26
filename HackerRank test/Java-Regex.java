import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
class Solution
{
public static void main(String[] args)
{
Scanner in=new Scanner(System.in);
while(in.hasNext())
{String ip=in.next();
System.out.println(ip.matches(new MyRegex().pattern));
}
}
}
class MyRegex
{
public String pattern=" ";
MyRegex()
{
String zeroTo255="(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])";
pattern=zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 +"\\." + zeroTo255;
}
}