import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;
import java.util.stream.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/*

 NOTATION: for a current maximum distance of 30 and c is 200

 First pair ->  First point ai = 10     Second point bi = 100
                               |                        |
                         0     |                        |                               200
                Indexes  |-----|------|----------|------|------|-----------------|-------|
                                      |          |             |                 |
                                      |          |             |                 |
   First point suitable pairs ->     41 <======> 69           131 <===========> 179
                                      |          |             |                 |
                                     /            \           /                   \
                                    / First range  \         /     Second range    \
                                   / suitable pairs \       /     suitable pairs    \
                                  |                  |     |                         |
                                First             Second  Third                    Last
                                index             index   index                    index
 */
public class Solution {
    
    private static class InputData {
        private int n = 0;
        private int c = 0;
        private int maximumPossibleDistance = 0;
        private int correctMaximumDistance = 0;
        private Pair[] pairs = new Pair[n];
        private boolean[] enoughDistancePair = new boolean[n];
    }

    private static class EdgePoints {
        private int minimumFirstPoint = Integer.MAX_VALUE;
        private int maximumFirstPoint = Integer.MIN_VALUE;
        private int minimumSecondPoint = Integer.MAX_VALUE;
        private int maximumSecondPoint = Integer.MIN_VALUE;

        private void updateEdgePoints(Pair pair) {
            int firstPointNewPair = pair.firstPoint;
            if (firstPointNewPair < minimumFirstPoint)
                minimumFirstPoint = firstPointNewPair;
            if (firstPointNewPair > maximumFirstPoint)
                maximumFirstPoint = firstPointNewPair;

            int secondPointNewPair = pair.secondPoint;
            if (secondPointNewPair < minimumSecondPoint)
                minimumSecondPoint = secondPointNewPair;
            if (secondPointNewPair > maximumSecondPoint)
                maximumSecondPoint = secondPointNewPair;
        }

        private boolean equalEdgePoints() {
            return minimumFirstPoint == maximumFirstPoint 
                && minimumSecondPoint == maximumSecondPoint;
        }
        
        private boolean areSecondPointsBetterOption() {
            int maximumFirstPointsDistance = Math.abs(minimumFirstPoint - maximumFirstPoint);
            int maximumSecondPointsDistance = Math.abs(minimumSecondPoint - maximumSecondPoint);
            return maximumSecondPointsDistance < maximumFirstPointsDistance * 0.80;
        }
    }

    private static class Pair {
        private int firstPoint;
        private int secondPoint;
        private int distance;

        public Pair(int firstPoint, int secondPoint, int distance) {
            this.firstPoint = firstPoint;
            this.secondPoint = secondPoint;
            this.distance = distance;
        }

        public int getFirstPoint() {
            return firstPoint;
        }

        public int getSecondPoint() {
            return secondPoint;
        }

        public int getDistance() {
            return distance;
        }
    }
    
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        final EdgePoints edgePoints = new EdgePoints();
        final InputData inputData = new InputData();
        inputData.n = scanner.nextInt();
        inputData.c = scanner.nextInt();
        Pair[] pairs = new Pair[inputData.n];
        for (int i = 0; i < inputData.n; i++) {
            int firstPoint = scanner.nextInt();
            int secondPoint = scanner.nextInt();
            int distance = distance(firstPoint, secondPoint, inputData.c);
            Pair newPair;
            if (firstPoint <= secondPoint) 
                newPair = new Pair(firstPoint, secondPoint, distance);
            else
                newPair = new Pair(secondPoint, firstPoint, distance);
            pairs[i] = newPair;
            edgePoints.updateEdgePoints(newPair);
        }
        inputData.pairs = pairs;
        inputData.maximumPossibleDistance = IntStream.of(inputData.c / 4,
                Math.abs(edgePoints.minimumFirstPoint - edgePoints.maximumFirstPoint),
                Math.abs(edgePoints.minimumSecondPoint - edgePoints.maximumSecondPoint))
            .min().getAsInt();
        inputData.enoughDistancePair = new boolean[inputData.n];
        Arrays.fill(inputData.enoughDistancePair, true);

        int maximumDistance;
        if (edgePoints.equalEdgePoints()) {
            maximumDistance = 0;
        } else {
            if (edgePoints.areSecondPointsBetterOption())
                switchPoints(inputData.pairs);
            maximumDistance = computeMaximumDistance(inputData);
        }
        System.out.println(maximumDistance);
        scanner.close();
    }
    
    private static void switchPoints(Pair[] pairs) {
        for (Pair pair : pairs) {
            int tempPoint = pair.firstPoint;
            pair.firstPoint = pair.secondPoint;
            pair.secondPoint = tempPoint;
        }
    }
    
    private static int computeMaximumDistance(InputData inputData) {
        int maximumPossibleDistance = inputData.maximumPossibleDistance;
        Pair[] pairs = inputData.pairs;
        int lastIndex = pairs.length - 1;
        int from = 0;
        int to = lastIndex;
        int ai = -1;
        boolean isFilterByDistanceDone = false;
        Pair previousFirstPair = null;
        Pair firstPair = null;

        Arrays.sort(pairs, Comparator.comparing(Pair::getFirstPoint));

        int maximumDistance = -1;
        while (from <= to) {
            if (nonNull(firstPair) && firstPair.equals(previousFirstPair) && from < lastIndex)
                ++from;
            else
                previousFirstPair = firstPair;
            firstPair = pairs[from];

            if (inputData.enoughDistancePair[from]) {
                int firstPairDistance = firstPair.distance;
                if (firstPairDistance > maximumDistance) {
                    ai = firstPair.firstPoint;
                    int firstIndexSuitablePairs = findRightFirstSuitablePair(ai, pairs, 
                            maximumDistance, from + 1, to);
                    if (firstIndexSuitablePairs >= 0) {
                        Pair firstSuitablePair = pairs[firstIndexSuitablePairs];
                        int aj = firstSuitablePair.firstPoint;
                        int bi = firstPair.secondPoint;
                        if (Math.abs(bi - aj) > maximumDistance && aj < bi) {
                            int secondIndexSuitablePairs = findLeftFirstSuitablePair(bi, pairs, 
                                    maximumDistance, firstIndexSuitablePairs, to);
                            maximumDistance = computeMaximumDistance(firstPair, pairs, 
                                    firstIndexSuitablePairs, secondIndexSuitablePairs, 
                                    maximumDistance, maximumPossibleDistance, inputData);
                            if (maximumDistance == maximumPossibleDistance)
                                break;
                            firstIndexSuitablePairs = secondIndexSuitablePairs;
                        }
                        int thirdIndexSuitablePairs = findRightFirstSuitablePair(bi, pairs, 
                                maximumDistance, firstIndexSuitablePairs, to);
                        if (thirdIndexSuitablePairs >= 0) {
                            int lastIndexSuitablePairs = findLeftFirstSuitablePair(inputData.c, 
                                    pairs, maximumDistance - ai, firstIndexSuitablePairs, to);
                            if (lastIndexSuitablePairs >= 0 && thirdIndexSuitablePairs 
                                    <= lastIndexSuitablePairs)
                                maximumDistance = computeMaximumDistance(firstPair, pairs, 
                                    thirdIndexSuitablePairs, lastIndexSuitablePairs, 
                                    maximumDistance, maximumPossibleDistance, inputData);
                        }
                    } else if (maximumDistance == -1)
                        maximumDistance = 0;
                }
            }

            boolean foundMaximumDistance = ai > inputData.c / 2 
                && maximumDistance > maximumPossibleDistance / 2;
            if (maximumDistance == maximumPossibleDistance || foundMaximumDistance)
                break;

            if (!isFilterByDistanceDone && maximumDistance >= maximumPossibleDistance * 0.99
                    && nonNull(firstPair) && firstPair.firstPoint > maximumPossibleDistance / 4) 
            {
                isFilterByDistanceDone = true;
                Arrays.sort(pairs, from, to + 1, Comparator.comparing(Pair::getDistance));
                if (from < lastIndex) {
                    int previous = from;
                    from = findFirstPairWithGreaterDistance(pairs, maximumDistance, from, to);
                    if (previous == from)
                        ++from;
                    int nextIndex = findFirstPairWithGreaterDistance(pairs, 
                            inputData.c - (3 * maximumDistance), from, to);
                    if (nextIndex >= 0) {
                        Arrays.sort(pairs, nextIndex, to + 1, 
                                    Comparator.comparing(Pair::getDistance).reversed());
                        int finalIndex = findFirstPairWithLessDistance(pairs, 
                                    2 * (maximumDistance + 1), nextIndex, to);
                        if (finalIndex >= 0)
                            to = finalIndex;
                    }
                    Arrays.sort(pairs, from, to + 1, Comparator.comparing(Pair::getFirstPoint));
                    Arrays.fill(inputData.enoughDistancePair, true);
                } else
                    ++from;
            } else
                ++from;
        }
        return maximumDistance;
    }
    
    private static int distance(int firstPoint, int secondPoint, int c) {
        int distance = Math.abs(firstPoint - secondPoint);
        return Math.min(distance, c - distance);
    }

    private static int findRightFirstSuitablePair(int referencePoint, Pair[] pairs, 
            int maximumDistance, int from, int to) {
        if (isNull(pairs) || pairs.length == 0)
            return -1;

        int end = to;
        while (from <= end) {
            int mid = (from + end) / 2;
            Pair secondPair = pairs[mid];
            int aj = secondPair.firstPoint;
            if (aj <= referencePoint)
                from = mid + 1;
            else {
                int distance = Math.abs(aj - referencePoint);
                if (distance <= maximumDistance) {
                    from = mid + 1;
                } else if (distance > maximumDistance) {
                    end = mid - 1;
                }
            }
        }
        if (from == pairs.length)
            return -1;
        return from;
    }

    private static int findLeftFirstSuitablePair(int referencePoint, Pair[] pairs, 
            int maximumDistance, int from, int to) {
        if (isNull(pairs) || pairs.length == 0)
            return -1;

        int start = from;
        while (start <= to) {
            int mid = (start + to) / 2;
            Pair secondPair = pairs[mid];
            int aj = secondPair.firstPoint;
            if (referencePoint <= aj)
                to = mid - 1;
            else {
                int distance = Math.abs(aj - referencePoint);
                if (distance <= maximumDistance) {
                    to = mid - 1;
                } else if (distance > maximumDistance) {
                    start = mid + 1;
                }
            }
        }
        return to;
    }

    private static int findFirstPairWithGreaterDistance(Pair[] pairs, int maximumDistance, 
            int from, int to) {
        if (isNull(pairs) || pairs.length == 0)
            return -1;

        while (from <= to) {
            int mid = (from + to) / 2;
            Pair secondPair = pairs[mid];
            int secondPairDistance = secondPair.distance;
            if (secondPairDistance <= maximumDistance)
                from = mid + 1;
            else
                to = mid - 1;
        }
        if (from == pairs.length)
            return -1;
        return from;
    }

    private static int findFirstPairWithLessDistance(Pair[] pairs, int minimumDistance, int from, 
            int to) {
        if (isNull(pairs) || pairs.length == 0)
            return -1;

        while (from <= to) {
            int mid = (from + to) / 2;
            Pair secondPair = pairs[mid];
            int secondPairDistance = secondPair.distance;
            if (secondPairDistance >= minimumDistance)
                from = mid + 1;
            else
                to = mid - 1;
        }
        if (from == pairs.length)
            return -1;
        return from;
    }

    private static int pairsDistance(int ai, int bi, int firstDistance, int aj, int bj, 
            int secondDistance, int c) {
        int minDistance = firstDistance;
        int currentDistance = secondDistance;
        if (currentDistance < minDistance)
            minDistance = currentDistance;
        currentDistance = distance(ai, aj, c);
        if (currentDistance < minDistance)
            minDistance = currentDistance;
        currentDistance = distance(ai, bj, c);
        if (currentDistance < minDistance)
            minDistance = currentDistance;
        currentDistance = distance(bi, aj, c);
        if (currentDistance < minDistance)
            minDistance = currentDistance;
        currentDistance = distance(bi, bj, c);
        if (currentDistance < minDistance)
            minDistance = currentDistance;
        return minDistance;
    }

    private static int computeMaximumDistance(Pair firstPair, Pair[] pairs, int from, int to, 
            int currentMaximumDistance, int maximumPossibleDistance, InputData inputData) {
        while (from <= to) {
            if (inputData.enoughDistancePair[from]) {
                Pair secondPair = pairs[from];
                int secondPairDistance = secondPair.distance;
                if (secondPairDistance > currentMaximumDistance) {
                    int distance = pairsDistance(firstPair.firstPoint, firstPair.secondPoint, 
                            firstPair.distance, secondPair.firstPoint, secondPair.secondPoint, 
                            secondPairDistance, inputData.c);
                    if (distance > currentMaximumDistance)
                        currentMaximumDistance = distance;
                    if (currentMaximumDistance == maximumPossibleDistance)
                        break;
                } else inputData.enoughDistancePair[from] = false;
            }
            ++from;
        }
        return currentMaximumDistance;
    }
}