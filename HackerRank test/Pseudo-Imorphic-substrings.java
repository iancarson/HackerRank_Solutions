import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    final static int LEFT_STR_IDX = 0;
    final static int RIGHT_STR_IDX = 1;
    final static int POS = 2;
    final static int LEFT_PTR = 3;
    final static int RIGHT_PTR = 4;
    final static int PARENT_PTR = 5;

    private char[] _inArr;

    // for each substring starting at j, pMapMap[j] has
    // the following entries:
    // entries 0..25 maps a char a..z (or rather intval(char-'a')) into  a..z
    // entry 26 is the largest char value in 0..25 so far
    int[][] _pMapMap;

    // 5 entries: leftId, rightId, position, leftForkIdx, rightForkIdx
    // position on left fork must always be > current position
    int[][] _forkMap;
    int[] _branchOffMap;

    int _nextFork;
    int _prevMatchIdx;


    private void buildPMap() {
    	int n = _inArr.length;
    	int[] lastSeenIdx = new int[26];
    	Arrays.fill(lastSeenIdx, -1);
    	for (int i = 0; i<n; i++) {
    		int thisChar = _inArr[i]-'a';
    		for (int j = lastSeenIdx[thisChar]+1; j <= i; j++) {
    			if (_pMapMap[j][thisChar] == 0) {
    				_pMapMap[j][thisChar] = _pMapMap[j][26]++ + 'a';
    			}
    		}
    		lastSeenIdx[thisChar] = i;
    	}
    }

    public void processStr(String inStr) throws Exception {
        _inArr = inStr.toCharArray();
        int totalLength = _inArr.length;
        _pMapMap = new int[totalLength][27];
        buildPMap();
        _branchOffMap = new int[totalLength];
        _forkMap = new int[totalLength][6];
        _nextFork = 1;

        int[] ctFrom = new int[totalLength];

        int uniqueMax = 0;
        long totalCt = 0;
        boolean totalMatch = false;

        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(java.io.FileDescriptor.out), "US-ASCII"), 512));

        for (int j=0; j < totalLength; j++) {
            // order important: increment only for old max strings
            if (!totalMatch) {
                int z = checkNewString(j);
                if (z == totalLength)
                    totalMatch = true;
                else
                    ctFrom[z]++;
            }
            uniqueMax += ctFrom[j];
            totalCt += uniqueMax;
            //System.out.println(totalCt);
            out.println(Long.toString(totalCt));
        }
        out.flush();
        out.close();
    }


    int checkNewString(int j) {
        if (j == 0) {
            return 0;
        }

        int totalLength = _inArr.length;

        // if string j-1 matched up to string k for z chars,
        // then string j will match string id=k+1 for at least
        // z-1 characters
        //
        // So we take the branch-off node of string k+1
        // if its POS>length(j), go up to parent until fork is before
        // length(j) - 1
        int z = _forkMap[j-1][POS];

        int currStrIdx = _branchOffMap[j-1] == 0 ? 0 : _branchOffMap[j-1] + 1;
        int forkId = currStrIdx;
        int[] fork = _forkMap[forkId];
        int dir = forkId == 0 ? LEFT_PTR:RIGHT_PTR;
        
        // move fork up if necessary
        while (fork[POS] >= z-1 && forkId > 0) {
            currStrIdx = fork[LEFT_STR_IDX];
            forkId = fork[PARENT_PTR];
            fork = _forkMap[forkId];
            dir = LEFT_PTR;
        }
        
        int p = fork[POS];
        if (fork[dir] == 0 && p<z)
        	p = z-1;
        else if (z>0 && _forkMap[fork[dir]][POS] >= z)
        	p = z-1;

        while (p < totalLength - j) {
            dir = currStrIdx == fork[LEFT_STR_IDX] ? LEFT_PTR : RIGHT_PTR;
            int thisChar = _pMapMap[j][_inArr[j+p]-'a'];
            int thatChar = _pMapMap[currStrIdx][_inArr[currStrIdx+p]-'a'];
            if (thisChar == thatChar) {
                p++;
                continue;
            }

            // bring the fork along curr str to the position <= p
            // this means we keep on the left
            while (fork[dir] > 0 && _forkMap[fork[dir]][POS] <= p) {
                forkId = fork[dir];
                fork = _forkMap[forkId];
                currStrIdx = fork[LEFT_STR_IDX];
                dir = LEFT_PTR;
            }

            // already have fork of currStrIdx with something at p? go right.
            if (fork[POS] == p) {
                // there is node on the right with same p
                if (fork[RIGHT_PTR] > 0 && _forkMap[fork[RIGHT_PTR]][POS] == p) {
                    forkId = fork[RIGHT_PTR];
                    fork = _forkMap[forkId];
                    currStrIdx = fork[LEFT_STR_IDX];
                    continue; // will try with currStr on the right, same position
                }
                // no more nodes on right, but we can try the right branch if
                // we haven't done it yet
                if (dir == LEFT_PTR) {
                    currStrIdx = fork[RIGHT_STR_IDX];
                    continue;
                }
                currStrIdx = fork[RIGHT_STR_IDX];
                dir = RIGHT_PTR;
            }

            // insert new fork
            int newForkIdx = _nextFork++;
            int[] newFork = _forkMap[newForkIdx];
            newFork[RIGHT_STR_IDX] = j;
            newFork[POS] = p;
            newFork[PARENT_PTR] = forkId;

            if(fork[POS] == p) {
                // already on fork; attach on right
                newFork[LEFT_PTR] = fork[RIGHT_PTR];
                if (newFork[LEFT_PTR] > 0)
                    _forkMap[newFork[LEFT_PTR]][PARENT_PTR] = newForkIdx;
                newFork[LEFT_STR_IDX] = fork[RIGHT_STR_IDX];
                fork[RIGHT_PTR] = newForkIdx;
            } else {
                // attach on dir; former dir side of fork goes on left,
                // new string always on right.
                newFork[LEFT_PTR] = fork[dir];
                newFork[LEFT_STR_IDX] = fork[dir-3];
                if (newFork[LEFT_PTR] > 0)
                    _forkMap[newFork[LEFT_PTR]][PARENT_PTR] = newForkIdx;
                fork[dir] = newForkIdx;
            }

            _branchOffMap[j] = currStrIdx;

            return p+j;
        }
        return totalLength;
    }

    public static void main(String[] args) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner sc = new Scanner(System.in);
        String inStr = sc.nextLine();
        sc.close();
        new Solution().processStr(inStr);
    }
}