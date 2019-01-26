import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*Solution assumes  "<" wont be printed
*/
public class Solution
{
	public static void main(Sting[] args)
	{
		Scanner in=new Scanner(System.in);
		int t = Integer.parseInt(in.nextLine());
		while(t --> 0)
		{
			String input=in.nextLine();
			boolean match = false;
			Pattern r = Pattern.compile("<(.+)>([^<]+)</\\1>");
			Matcher m = r.matcher(input);
			while(m.find())
			{
				System.out.println(m.group(2));
				match = true;
			}
			if( !match)
			{
				System.out.println("None");
			}
		}
	}
}