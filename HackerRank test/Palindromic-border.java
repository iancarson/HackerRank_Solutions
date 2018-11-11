import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    
    static final long Q = 1000000007L;

    /** Trie node. */
    static class Node {
        public Node[] children;  // Default null; if initialized, will have length 8, representing a,b,...,h
        public Node parent;  // Default null
        public int length;  // Default 0
        public long count;  // Default 0.  In initial pass, will be number of palindromes represented by this trie node.
                            // In pass for summing, will be number of substrings represented by this trie node.

        public Node getChild(int index, List<Node>[] list) {
            if (list[length + 1] == null) {
                list[length + 1] = new ArrayList<>();
            }
            if (children == null) {
                children = new Node[8];
            }
            if (children[index] == null) {
                children[index] = new Node();
                list[length + 1].add(children[index]);  // Add to list for each new Node() creation (besides root)
            }
            children[index].parent = this;
            children[index].length = length + 1;
            return children[index];
        }
    }

    public static void main(String[] args) {
        
        // http://stackoverflow.com/questions/19801081/find-all-substrings-that-are-palindromes
        // http://codepad.org/vAGMSNKG
        // http://articles.leetcode.com/2011/11/longest-palindromic-substring-part-ii.html
        // http://en.wikipedia.org/wiki/Trie
        
        //long startTime = new Date().getTime();
        Scanner in = new Scanner(System.in);
        /*char[] s = new char[100002];
        s[0] = '@';
        for (int u = 1; u < s.length - 1; u++) {
            s[u] = 'a';
        }
        s[s.length - 1] = '#';*/
        char[] s = ("@" + in.nextLine() + "#").toCharArray();
        int N = s.length - 2;

        int i, j, k;   // iterators
        int diff;

        // Original string's characters are at indices 1 through N.
        // R[0][i] = trie node of longest string w s.t. w = [chars i through ...] and reverse(w) = [chars ... through i-1].
        // R[1][i] = trie node of longest string w s.t. w = [chars i through ...] and reverse(w) = [chars ... through i].
        // Node.length is 'palindrome radius' = ceiling((length of palindrome) / 2).
        Node[][] R = new Node[2][N + 1];

        // lists[i][j] contains the distinct elements of R[i] with length j
        List<Node>[][] lists = new List[2][(N + 3) / 2];

        Node root = new Node();
        Node curr;
        Node curr2;
        
        // Even-length palindromes
        i = 1;
        curr = root;
        boolean endEarly;
        while (i <= N) {
            // curr is assumed to be part of the longest palindrome around i;
            // keep going to find longest palindrome around i
            while (s[i - 1 - curr.length] == s[i + curr.length]) {
                curr = curr.getChild(s[i + curr.length] - 'a', lists[0]);
            }
            R[0][i] = curr;
            R[0][i].count++;
            k = 1;
            endEarly = false;
            while (k < curr.length) {
                diff = R[0][i - k].length - (curr.length - k);
                if (diff < 0) {
                    R[0][i + k] = R[0][i - k];
                    R[0][i + k].count++;
                }
                else {
                    curr2 = R[0][i - k];
                    // Shorten curr2 so that it is the longest palindrome around i that is deducible from R[0][i - k]
                    for (j = 0; j < diff; j++) {
                        curr2 = curr2.parent;
                    }
                    if (diff == 0) {  // Only case that R[0][i + k] is not yet determined
                        curr = curr2;  // It is known that palindrome around i + k is at least curr2
                        endEarly = true;
                        break;
                    }
                    else {  // Because R[0][i] is not any longer than it is, curr2 is actually the longest palindrome around i
                        R[0][i + k] = curr2;
                        R[0][i + k].count++;
                    }
                }
                k++;
            }
            i += k;
            if (!endEarly) {  // If curr was not initialized as curr2 above, initialize it now
                curr = root;
            }
        }

        // Odd-length palindromes
        i = 1;
        root = new Node();
        curr = root.getChild(s[i] - 'a', lists[1]);
        while (i <= N) {
            while (s[i - curr.length] == s[i + curr.length]) {
                curr = curr.getChild(s[i + curr.length] - 'a', lists[1]);
            }
            R[1][i] = curr;
            R[1][i].count++;
            k = 1;
            endEarly = false;
            while (k < curr.length) {
                diff = R[1][i - k].length - (curr.length - k);
                if (diff < 0) {
                    R[1][i + k] = R[1][i - k];
                    R[1][i + k].count++;
                }
                else {
                    curr2 = R[1][i - k];
                    for (j = 0; j < diff; j++) {
                        curr2 = curr2.parent;
                    }
                    if (diff == 0) {  // Only case that R[1][i + k] is not yet determined
                        curr = curr2;  // It is known that palindrome around i + k is at least curr2
                        endEarly = true;
                        break;
                    }
                    else {
                        R[1][i + k] = curr2;
                        R[1][i + k].count++;
                    }
                }
                k++;
            }
            i += k;
            if (!endEarly && i <= N) {  // If curr was not initialized as curr2 above, initialize it now
                curr = root.getChild(s[i] - 'a', lists[1]);
            }
        }
        
        /*for(j = 0; j < 2; j++)  {
        for(i = 1; i <= N; i++)  {
            System.out.println(i + " " + R[j][i].length + " " + R[j][i].count);
        }
        }*/

        long sum = 0L;
        for (j = 0; j < 2; j++) {
            for (i = lists[j].length - 1; i > 0; i--) {  // Skip root
                if (lists[j][i] != null) {
                    for (Node node : lists[j][i]) {
                        // Add number of substrings with a palindromic border that is the palindrome
                        // constructed from the string represented by node
                        sum = (sum + ((node.count * (node.count - 1)) / 2) % Q) % Q;
                        // Update parent's count so that when eventually parent is considered as node,
                        // its count will be the number of substrings that equals the palindrome
                        // constructed from the string represented by it
                        node.parent.count += node.count;
                    }
                }
            }
        }
        System.out.println(sum);
        
        //System.out.println(new Date().getTime() - startTime);
    } 
}