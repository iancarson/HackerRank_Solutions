import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.io.BufferedReader;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Solution {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        OutputWriter out = new OutputWriter(outputStream);
        MinimumMSTGraph solver = new MinimumMSTGraph();
        int testCount = Integer.parseInt(in.next());
        for (int i = 1; i <= testCount; i++)
            solver.solve(i, in, out);
        out.close();
    }

    static class MinimumMSTGraph {
        public int MAGIC = 100;

        public void solve(int testNumber, InputReader in, OutputWriter out) {
            long n = in.nextLong();
            long m = in.nextLong();
            long s = in.nextLong();
            m -= n - 1;
            long add1 = (n - 1) * (n - 2) / 2 - (n - 2);
            long take = Math.min(m, add1);
            m -= take;
            long light = 1;
            long heavy = s - light * (n - 2);
            BigInteger ans = new BigInteger(String.valueOf(s));
            ans = ans.add(new BigInteger(String.valueOf(light)).multiply(new BigInteger(String.valueOf(take))));
            ans = ans.add(new BigInteger(String.valueOf(heavy)).multiply(new BigInteger(String.valueOf(m))));
            if (m == 0) {
                out.println(ans);
                return;
            }
            m += take + n - 1;
            long lo = 1;
            long hi = n - 1;
            long iter = MAGIC;
            while (hi >= lo && iter-- > 0) {
                BigInteger w = getCost(hi, s, m, n);
                if (w.compareTo(ans) < 0) ans = w;
                hi--;
            }
            iter = MAGIC;
            while (lo <= hi && iter-- > 0) {
                BigInteger w = getCost(lo, s, m, n);
                if (w.compareTo(ans) < 0) ans = w;
                lo++;
            }
            out.println(ans);
        }

        public BigInteger getCost(long i, long s, long m, long n) {
            long need = s - (n - 1);
            BigInteger ccost = new BigInteger(String.valueOf(m));
            long n1 = m - i * (i - 1) / 2;
            long d1 = n - i;
            ccost = ccost.add(new BigInteger(String.valueOf((need / d1))).multiply(new BigInteger(String.valueOf(n1))));
            need %= d1;
            if (need > 0) {
                long ax = n - need;
                BigInteger a1 = new BigInteger(String.valueOf(m - ax * (ax - 1) / 2));
                BigInteger a2 = new BigInteger(String.valueOf(need)).multiply(new BigInteger(String.valueOf(m - (n - 1) * (n - 2) / 2)));
                if (a1.compareTo(a2) < 0) ccost = ccost.add(a1);
                else ccost = ccost.add(a2);
            }
            return ccost;
        }

    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

    }

    static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void println(Object... objects) {
            print(objects);
            writer.println();
        }

        public void close() {
            writer.close();
        }

    }
}
