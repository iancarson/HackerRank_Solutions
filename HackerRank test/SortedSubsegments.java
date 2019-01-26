import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.io.IOException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        OutputWriter out = new OutputWriter(outputStream);
        SortedSubsegments solver = new SortedSubsegments();
        solver.solve(1, in, out);
        out.close();
    }

    static class SortedSubsegments {
        int[] ones;
        int[] cnt0;
        int[] as;
        int[] len;

        public void solve(int testNumber, InputReader in, OutputWriter out) {
            int n = in.readInt(), q = in.readInt(), k = in.readInt();
            int[] a = in.readIntArray(n);
            int[] l = new int[q], r = new int[q];
            for (int i = 0; i < q; i++) {
                l[i] = in.readInt();
                r[i] = in.readInt();
            }
            int[] z = a.clone();
            Arrays.sort(z);
            int cc = 1;
            for (int i = 1; i < z.length; i++) {
                if (z[i] != z[cc - 1]) {
                    z[cc++] = z[i];
                }
            }
            z = Arrays.copyOf(z, cc);
            for (int i = 0; i < n; i++) {
                a[i] = lowerBound(z, a[i]);
            }
            cnt0 = new int[n * 4 + 100];
            as = new int[n * 4 + 100];
            len = new int[n * 4 + 100];
            ones = new int[n];
            int ll = -1, rr = z.length;
            while (rr - ll > 1) {
                int mm = (ll + rr) / 2;
                if (check(a, l, r, mm, k)) {
                    ll = mm;
                } else {
                    rr = mm;
                }
            }
            out.printLine(z[ll]);
        }

        void make_rmq(int i, int l, int r) {
            len[i] = r - l + 1;
            as[i] = -1;
            if (l == r) {
                cnt0[i] = 1 ^ ones[l];
                return;
            }
            make_rmq(i * 2, l, (l + r) / 2);
            make_rmq(i * 2 + 1, (l + r) / 2 + 1, r);
            cnt0[i] = cnt0[i * 2] + cnt0[i * 2 + 1];
        }

        void push(int i) {
            if (as[i] != -1) {
                as[i * 2] = as[i * 2 + 1] = as[i];
                cnt0[i] = as[i] == 1 ? 0 : len[i];
                as[i] = -1;
            }
        }

        int calc0(int i) {
            return as[i] == -1 ? cnt0[i] : (as[i] == 1 ? 0 : len[i]);
        }

        int getValue(int i, int ll, int rr, int l, int r) {
            if (ll > r || rr < l) {
                return 0;
            }
            if (l <= ll && rr <= r) {
                return calc0(i);
            }
            push(i);
            return getValue(i * 2, ll, (ll + rr) / 2, l, r) + getValue(i * 2 + 1, (ll + rr) / 2 + 1, rr, l, r);
        }

        void update(int i, int ll, int rr, int l, int m, int r) {
            if (ll > r || rr < l) {
                return;
            }
            if (l <= ll && rr < m) {
                as[i] = 0;
                return;
            }
            if (m <= ll && rr <= r) {
                as[i] = 1;
                return;
            }
            push(i);
            update(i * 2, ll, (ll + rr) / 2, l, m, r);
            update(i * 2 + 1, (ll + rr) / 2 + 1, rr, l, m, r);
            cnt0[i] = calc0(i * 2) + calc0(i * 2 + 1);
        }

        boolean check(int[] a, int[] l, int[] r, int x, int k) {
            int n = a.length, q = l.length;
            for (int i = 0; i < n; i++) {
                ones[i] = a[i] >= x ? 1 : 0;
            }
            make_rmq(1, 0, n - 1);
            for (int i = 0; i < q; i++) {
                int cnt = getValue(1, 0, n - 1, l[i], r[i]);
                update(1, 0, n - 1, l[i], l[i] + cnt, r[i]);
            }
            return getValue(1, 0, n - 1, k, k) == 0;
        }

        int lowerBound(int[] a, int x) {
            int l = -1, r = a.length;
            while (r - l > 1) {
                int m = (l + r) / 2;
                if (a[m] >= x) {
                    r = m;
                } else {
                    l = m;
                }
            }
            return r;
        }

    }

    static class InputReader {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public InputReader(Reader reader) {
            this.reader = new BufferedReader(reader);
        }

        public InputReader(InputStream stream) {
            this(new InputStreamReader(stream));
        }

        public String nextLine() {
            try {
                return reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public String readWord() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(nextLine());
            }
            return tokenizer.nextToken();
        }

        public int readInt() {
            return Integer.parseInt(readWord());
        }

        public int[] readIntArray(int size) {
            int[] result = new int[size];
            for (int i = 0; i < size; i++) {
                result[i] = readInt();
            }
            return result;
        }

    }

    static class OutputWriter {
        private PrintWriter writer;

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public OutputWriter(OutputStream stream) {
            this(new OutputStreamWriter(stream));
        }

        public void print(Object... args) {
            for (Object arg : args) {
                writer.print(arg);
            }
        }

        public void printLine(Object... args) {
            print(args);
            writer.println();
        }

        void close() {
            writer.close();
        }

    }
}