import java.io.*;
import java.util.*;
import java.math.*;
public class GenaPlayingHanoi {
    private static final int TOWERS = 4;
    private static final int MAX = 100;

    private static boolean isGoal(Integer[] state) {
        for (int i = 1; i < state.length; i++) {
            if (!Integer.valueOf(0).equals(state[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * Given a tower-disc allocation [0, 3, 0] returns a Set<Integer[]> of valid directly subsequent states.
     */
    private static Set<Integer[]> validMovesFromState(Integer[] state) {
        Set<Integer[]> ans = new HashSet<>();

        Integer[] minForTower = minForTower(state.length - 1, state);

        for (int t = 0; t < TOWERS; t++) {
            int discToMove = minForTower[t];

            for (int t2 = 0; t2 < TOWERS; t2++) {
                if (t2 != t && minForTower[t2] > discToMove) {
                    // move i from tower t to tower t2
                    Integer[] newState = state.clone();
                    newState[discToMove] = t2;
                    ans.add(newState);
                }
            }
        }

        return ans;
    }

    /**
     * Returns array int[] a = int[TOWERS+1] with t[i] holding the size of the
     * top (smallest) disc located at tower i
     * 
     */
    private static Integer[] minForTower(int n, Integer[] t) {
        Integer[] ans = { MAX, MAX, MAX, MAX, MAX };

        for (int i = 1; i <= n; i++) {
            ans[t[i]] = Math.min(ans[t[i]], i);
        }

        return ans;
    }

    /**
     * Given a state e.g. [1, 4, 1] returns an integer representing that state. Bit 2i and 2i+1 represent the tower (tower 0 = 00, tower 1 = 01, tower 2 = 10, tower 3 = 11).
     * 
     * For [1, 4, 1] the method returns 12 = 00-11-00
     * 
     */
    private static int key(int n, Integer[] t) {
        int ans = 0;

        for (int disc = 1; disc <= n; disc++) {
            int tower = t[disc];
            int towerBits = tower << 2 * (disc - 1);
            ans |= towerBits;
        }

        return ans;
    }


    /**
     * BFS
     */
    private static void solve(int n, Integer[] initialState) {
        Queue<Integer[]> queue = new LinkedList<Integer[]>();
        int[] visited = new int[(int) Math.pow(4, n)];

        int key = key(n, initialState);
        initialState[0] = 0;
        visited[key] = 0;
        queue.add(initialState);

        int min = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            Integer[] current = queue.poll();

            if (isGoal(current)) {
                min = Math.min(min, current[0]);
            } else {
                Set<Integer[]> next = validMovesFromState(current);
                for (Integer[] state : next) {
                    key = key(n, state);
                    if (visited[key] == 0 || visited[key] > current[0] + 1) {
                        // state[0] is used to hold the number of steps it took us to get there
                        state[0] = current[0] + 1;
                        visited[key] = current[0] + 1;
                        queue.add(state);
                    }
                }
            }
        }

        System.out.println(min);
    }

    public static void main(String[] args) throws FileNotFoundException {
        //System.setIn(new FileInputStream(System.getProperty("user.home") + "/" + "in.txt"));
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        Integer[] t = new Integer[n + 1];
        for (int i = 1; i <= n; i++) {
            t[i] = in.nextInt() - 1;
        }

        solve(n, t);
    }
}

