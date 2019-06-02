import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.TreeMap;

public class Solution {
	private static class G extends ArrayList<Integer> implements Comparable<G> {
		private static final long serialVersionUID = 1L;
		int d;
		public G(int d) {
			this.d = d;
		}
		@Override
		public int compareTo(G g) {
			return d - g.d;
		}
		/**
		 * Assumes the list is sorted
		 * @param t
		 * @return
		 */
		public double removeFrom(double t) {
			if(isEmpty())
				return t;
			int lt = get(size() - 1);
			if(lt < t)
				return t;
			while(!isEmpty() && get(size() - 1) >= t)
				remove(size() - 1);
			return lt;
		}
	}
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		TreeMap<Integer, G> m = new TreeMap<Integer, G>();
		for(int i = 0; i < N; i++) {
			int t = in.nextInt(), f = in.nextInt(), d = f - t;
			G g = m.get(d);
			if(g == null) {
				g = new G(d);
				m.put(d, g);
			}
			g.add(t);
		}
		Collection<G> lg = m.values();
		G[] l = new G[lg.size()];
		l = lg.toArray(l);
		int ll = l.length, c = 0;
		while(ll > 0) {
			int li = --ll;
			if(l[li].size() <= 0)
				continue;
			double t = l[li].removeFrom(0);
			while(li >= 1) {
				t += (l[li].d - l[li - 1].d) / 2.0;
				t = l[--li].removeFrom(t);
			}
			c++;
		}
		System.out.println(c);
	}
}