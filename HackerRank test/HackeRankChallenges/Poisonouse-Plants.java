import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Solution {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		int N = sc.nextInt();
		int[] P = new int[N + 2];
		int[] prev = new int[N + 2];
		int[] next = new int[N + 2];
		next[0] = 1;
		prev[N + 1] = N;
		for (int i = 1; i <= N; ++i) {
			P[i] = Integer.parseInt(sc.next());
			prev[i] = i - 1;
			next[i] = i + 1;
		}
		ArrayList<Integer> killer = new ArrayList<>();
		for (int i = 1; i < N; ++i) {
			if (P[i] < P[i + 1]) {
				killer.add(i);
			}
		}
		int day = 0;
		while (!killer.isEmpty()) {
			++day;
			ArrayList<Integer> nk = new ArrayList<>();
			for (int i = killer.size() - 1; i >= 0; --i) {
				int k = killer.get(i);
				int killed = next[k];
				prev[next[killed]] = k;
				next[k] = next[killed];
				if (!nk.isEmpty() && nk.get(nk.size() - 1) == killed) nk.remove(nk.size() - 1);
				if (next[k] <= N && P[k] < P[next[k]]) {
					nk.add(k);
				}
			}
			Collections.reverse(nk);
			killer = nk;
		}
		System.out.println(day);
	}
}
