import java.util.Scanner;
import java.util.TreeSet;

public class MinimumLoss {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int cases = scn.nextInt();
		TreeSet<Long> values = new TreeSet<>();
		values.add(scn.nextLong());
		long min = Long.MAX_VALUE;
		for (int i = 1; i < cases; i++) {
			long value = scn.nextLong();
			Long higher = values.higher(value);
			if (higher != null) {
				min = Math.min(min, Math.abs(higher - value));
			}
			values.add(value);
		}
		System.out.println(min);
		scn.close();
	}
}