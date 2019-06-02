import java.util.HashSet;
import java.util.Scanner;

class HashSet
{
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		Set<String> set = new HashSet<String>();
		for(int i = 0; i < n; i++)
		{
			set.add(in.next() + " " + in.next());
			System.out.println(set.size());
		}
	}
}