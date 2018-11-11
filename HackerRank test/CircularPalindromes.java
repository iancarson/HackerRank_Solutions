import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Solution {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int len_orig = sc.nextInt();
        String text = sc.next();
        sc.close();
        // ---
        final char[] chars = preProcess(text);
		final int[] temp = new int[chars.length];
        final int[] first_max_palindrome = longestPalindrome(chars, temp);
        int[] solve = null;
        double var = calculate_variance(temp);
        if(var < 2) {
			solve = solve_small_variance(chars, text.length(), temp, first_max_palindrome);
		} else {
            solve = solve_large_variance(chars, text.length(), temp);
        }
        // ---
        for(int i = 0; i < solve.length; i++) {
            System.out.println(solve[i]);
        }
	}

    private static double calculate_variance(int[] temp) {
		double summSqr = 0;
		double summVal = 0;
		for (int i = 0; i < temp.length; i++) {
            double val = (double)temp[i];
			summSqr += val * val;
			summVal += val;
		}
        double len_prcd = (double)temp.length;
		double avgSqr = summSqr / len_prcd;
		double avgVal = summVal / len_prcd;
		avgVal = avgVal * avgVal;
		double var = Math.abs(avgSqr - avgVal);
        return var;
    }
    
    private static final int[] solve_small_variance(char[] chars, int len_orig, int[] temp, final int[] first_max_palindrome) {
		
		first_max_palindrome[0] = Math.min(first_max_palindrome[0], len_orig);
		final int _first_max_palindrome = first_max_palindrome[0];
		final int[] toReturn = new int[len_orig];
		Arrays.fill(toReturn, _first_max_palindrome);
		int end = len_orig << 1;
		int beg_lim = end + 2;
		final int double_length = end;
		int midSearch, curLim, maxPal, left, right, beg = 2;
		while(beg < beg_lim) {
			int last_in_range_index = _in_range(temp, _first_max_palindrome, beg, end);
			if(last_in_range_index != -1) {
				beg = last_in_range_index - (_first_max_palindrome - 1);
				end = (beg - 2) + (len_orig << 1);
			} else {
				midSearch = (end + beg) >> 1;
				curLim = len_orig;
				maxPal = Math.min(temp[midSearch], curLim--);
				left = midSearch - 1;
				right = midSearch + 1;
				while(maxPal < curLim) {
					final int rightVal = temp[right];
					final int leftVal = temp[left];
					int val = Math.max(rightVal, leftVal);
					if(val > maxPal) {
						if(val < curLim) {
							maxPal = val;
						} else {
							maxPal = curLim;
						}
					} 
					--curLim;
					--left;
					++right;
				}
				toReturn[(beg - 2) >> 1] = maxPal;
				beg += 2;
				end += 2;
			}
		}
		return toReturn;
	}
    
    private static final int[] solve_large_variance(char[] chars, int len_orig, int[] temp) {
		longestPalindrome(chars, temp);
		final int[] toReturn = new int[len_orig];
        int ix = 0;
        
        // ------
        
        int mid = out_ix_to_mid(ix, len_orig);
		toReturn[ix++] = max_palindrome(mid, len_orig, temp);
		toReturn[ix++] = max_palindrome(mid + 2, len_orig, temp);
		mid += 4;
		int dy1 = toReturn[ix - 1] - toReturn[ix - 2];
		//
		int addToMidShiftInit = 24;
		int addToMidShiftLim = 8;
		int addToMidShift = addToMidShiftInit;
		while(ix < len_orig) {
			int tryMid = mid + (addToMidShift == 0 ? 0 : (1 << addToMidShift));
			int tryIx = mid_to_out_ix(tryMid, len_orig);
			if(tryIx >= len_orig) {
				addToMidShift--;
				continue;
			}
			int expMp = toReturn[ix - 1] + (dy1 * (tryIx - (ix - 1)));
 			int actMp = 0;
			if(toReturn[tryIx] == 0) {
				actMp = max_palindrome(tryMid, len_orig, temp);
				toReturn[tryIx] = actMp;
			} else {
				actMp = toReturn[tryIx];
			}
			if(expMp == actMp) {
				do {
					toReturn[ix] = toReturn[ix - 1] + dy1; 
					ix++;
					mid += 2;
				} while(mid <= tryMid && ix < len_orig);
				addToMidShift = addToMidShiftInit; // @TODO IMPROOVE - adjust according to remaining length
			} else if(addToMidShift > addToMidShiftLim) {
				addToMidShift--;
				continue;
			} else {
				int mid_lim = tryMid;
				for (; mid < mid_lim && ix < len_orig; mid += 2) {
					if(toReturn[ix] == 0) {
						toReturn[ix] = max_palindrome(mid, len_orig, temp);
					}
					ix++;
				}
				while(ix < len_orig && toReturn[ix] != 0) {
					mid += 2;
					ix++;
				}
				dy1 = toReturn[ix - 1] - toReturn[ix - 2];
				addToMidShift = addToMidShiftInit;
			}
		}
		return toReturn;
	}
    
    static int[] longestPalindrome(char[] chars_preprocessed, int[] longest_palindrome) {
		int n = chars_preprocessed.length;
		int C = 0, R = 0;
		for (int i = 1; i < n - 1; i++) {
			int i_mirror = 2 * C - i;
		    longest_palindrome[i] = (R > i) ? Math.min(R - i, longest_palindrome[i_mirror]) : 0;
		    while (chars_preprocessed[i + 1 + longest_palindrome[i]] == 
                   chars_preprocessed[i - 1 - longest_palindrome[i]]) {
		    	longest_palindrome[i]++;
		    }
		    if (i + longest_palindrome[i] > R) {
		    	C = i;
		    	R = i + longest_palindrome[i];
		    }
		}
		int maxLen = 0;
		int maxi = -1;
		for (int i = 1; i < n - 1; i++) { 
			if (longest_palindrome[i] > maxLen) {
				maxLen = longest_palindrome[i];
				maxi = i;
			}
		}
		return new int[] { maxLen, maxi };
	}
    
    private static final char[] preProcess(String s) {
		char[] chars = s.toCharArray();
		int len = chars.length;
		char[] ret = new char[1 + (len << 2)];
		ret[0] = '^';
		ret[1] = '#';
		ret[ret.length - 1] = '$';
		ret[len << 1] = chars[len - 1];
		ret[(len << 1) + 1] = '#';
		for (int i = len - 2, l = (len << 1) - 1, r = (len << 2) - 1; i > -1;) {	
			ret[l--] = ret[r--] = '#';
			ret[l--] = ret[r--] = chars[i--];
		}
		return ret;
	}
    
    private static final int max_palindrome(int mid, int len_orig, int[] temp) {
		int limit = len_orig;
		int maxPalindrome = Math.min(limit--, temp[mid]);
		int right_lim = mid + len_orig;
		int left = mid - 1;
		int right = mid + 1;
		while(maxPalindrome < limit) {
			final int rightVal = temp[right];
			final int leftVal = temp[left];
			final int val = Math.max(rightVal, leftVal);
			if(val > maxPalindrome) {
				if(val < limit) {
					maxPalindrome = val;
				} else {
					maxPalindrome = limit;
				}
			} 
			--limit;
			--left;
			++right;
		}
		return maxPalindrome;
	}

    static int _in_range(int[] max_palindrome_val, int max_palindrome, int beg, int end) {
		int i_start = end - (max_palindrome - 1);
		int i_lim = (beg + max_palindrome) - 1;
		for(int i = i_start; i > i_lim; --i) {
			if(max_palindrome_val[i] >= max_palindrome) {
				return i;
			}
		}
		return -1; 
	}
    
    private static final int mid_to_out_ix(int mid, int len_orig) {
		return (mid - (len_orig + 1)) >> 1;
	}
	
	private static final int out_ix_to_mid(int ix, int len_orig) {
		return (ix << 1) + len_orig + 1;
	}

}