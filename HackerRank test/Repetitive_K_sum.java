import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RepetitiveKSums {

    private static int nextIndex(Map<Long, Long> expectedCounts, int currentIndex, long[] values) {
        long currentValue = values[currentIndex];
        long previousValue = currentValue;
        while (true) {
            if (expectedCounts.containsKey(previousValue) && expectedCounts.get(previousValue) != 0L) {
                currentIndex += expectedCounts.get(previousValue);
                expectedCounts.put(previousValue, 0L);
            } else {
                break;
            }

            if (previousValue == values[currentIndex]) {
                break;
            }
            previousValue = values[currentIndex];
        }
        return currentIndex;
    }

    private static void solve(long[] values, long[] sequence, long kSums, int sequenceIndex, int valueIndex,
            Map<Long, Long> expectedCounts) {
        if (sequenceIndex >= sequence.length) {
            return;
        }

        int index = nextIndex(expectedCounts, valueIndex, values);
        if (sequenceIndex == 0) {
            sequence[sequenceIndex] = values[index] / kSums;
        } else {
            sequence[sequenceIndex] = values[index] - (sequence[0] * (kSums - 1));
        }

        addExpectedCounts(sequence, sequenceIndex, expectedCounts, kSums - 1, sequence[sequenceIndex], 0);
        sequenceIndex++;

        solve(values, sequence, kSums, sequenceIndex, index, expectedCounts);
    }

    public static void addExpectedCounts(long[] sequence, int sequenceIndex, Map<Long, Long> expectedCounts,
            long numberInSum, long sumSoFar, int startIndex) {
        if (numberInSum == 0) {
            long expect = expectedCounts.containsKey(sumSoFar) ? expectedCounts.get(sumSoFar) + 1 : 1;
            expectedCounts.put(sumSoFar, expect);
            return;
        }

        for (int i = startIndex; i <= sequenceIndex; i++) {
            long innerSum = sumSoFar + sequence[i];

                addExpectedCounts(sequence, sequenceIndex, expectedCounts, numberInSum - 1, innerSum, i);

        }
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int cases = scn.nextInt();

        for (int i = 0; i < cases; i++) {
            int numberInSequence = scn.nextInt();
            long kSums = scn.nextLong();
            scn.nextLine();

            long[] values = Arrays.stream(scn.nextLine().trim().split(" ")).mapToLong(Long::valueOf).toArray();
            Arrays.sort(values);
            long[] sequence = new long[numberInSequence];
            solve(values, sequence, kSums, 0, 0, new HashMap<>());
            for (long val : sequence) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
        scn.close();
    }
}
