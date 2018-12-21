import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.io.IOException;
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
        FrogInMaze solver = new FrogInMaze();
        solver.solve(1, in, out);
        out.close();
    }

    static class FrogInMaze {
        public int[] dx = {-1, 0, 1, 0};
        public int[] dy = {0, -1, 0, 1};
        public int[][] ts;

        public void solve(int testNumber, InputReader in, OutputWriter out) {
            int n = in.nextInt(), m = in.nextInt(), k = in.nextInt();
            char[][] grid = new char[n][m];
            for (int i = 0; i < n; i++) {
                grid[i] = in.next().toCharArray();
            }
            int[][][] neighbor = new int[n][m][];
            ts = new int[k][4];
            for (int i = 0; i < k; i++) {
                for (int j = 0; j < 4; j++)
                    ts[i][j] = in.nextInt() - 1;
                neighbor[ts[i][0]][ts[i][1]] = new int[]{ts[i][2], ts[i][3]};
                neighbor[ts[i][2]][ts[i][3]] = new int[]{ts[i][0], ts[i][1]};
            }

            double[][] mat = new double[n * m][n * m + 1];
            int sx = 0, sy = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    mat[i * m + j][i * m + j] = 1;

                    if (grid[i][j] == '%') {
                        mat[i * m + j][n * m] = 1;
                        continue;
                    }
                    if (grid[i][j] == '*' || grid[i][j] == '#') {
                        mat[i * m + j][n * m] = 0;
                        continue;
                    }

                    if (grid[i][j] == 'A') {
                        sx = i;
                        sy = j;
                    }


                    int avail = 0;
                    for (int r = 0; r < 4; r++) {
                        int ni = i + dx[r], nj = j + dy[r];
                        if (ni >= 0 && ni < n && nj >= 0 && nj < m && grid[ni][nj] != '#') {
                            avail++;
                        }
                    }

                    for (int r = 0; r < 4; r++) {
                        int ni = i + dx[r], nj = j + dy[r];
                        if (ni >= 0 && ni < n && nj >= 0 && nj < m && grid[ni][nj] != '#') {
                            if (neighbor[ni][nj] != null) {
                                int[] x = neighbor[ni][nj];
                                ni = x[0];
                                nj = x[1];
                            }
                            mat[i * m + j][ni * m + nj] -= 1.0 / avail;
                        }
                    }

                }
            }

            RowReduce.rref(mat);

            for (int i = 0; i < n * m; i++) {
                if (mat[i][sx * m + sy] > 1e-8) {
                    out.printf("%.10f\n", mat[i][n * m]);
                    return;
                }
            }
            out.println(0);
        }

    }

    static class RowReduce {
        public static void rref(double[][] M) {
            int row = M.length;
            if (row == 0)
                return;

            int col = M[0].length;

            int lead = 0;
            for (int r = 0; r < row; r++) {
                if (lead >= col)
                    return;

                int k = r;
                while (M[k][lead] == 0) {
                    k++;
                    if (k == row) {
                        k = r;
                        lead++;
                        if (lead == col)
                            return;
                    }
                }
                double[] temp = M[r];
                M[r] = M[k];
                M[k] = temp;

                double lv = M[r][lead];
                for (int j = 0; j < col; j++)
                    M[r][j] /= lv;

                for (int i = 0; i < row; i++) {
                    if (i != r) {
                        lv = M[i][lead];
                        for (int j = 0; j < col; j++)
                            M[i][j] -= lv * M[r][j];
                    }
                }
                lead++;
            }
        }

    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int read() {
            if (this.numChars == -1) {
                throw new InputMismatchException();
            } else {
                if (this.curChar >= this.numChars) {
                    this.curChar = 0;

                    try {
                        this.numChars = this.stream.read(this.buf);
                    } catch (IOException var2) {
                        throw new InputMismatchException();
                    }

                    if (this.numChars <= 0) {
                        return -1;
                    }
                }

                return this.buf[this.curChar++];
            }
        }

        public int nextInt() {
            int c;
            for (c = this.read(); isSpaceChar(c); c = this.read()) {
                ;
            }

            byte sgn = 1;
            if (c == 45) {
                sgn = -1;
                c = this.read();
            }

            int res = 0;

            while (c >= 48 && c <= 57) {
                res *= 10;
                res += c - 48;
                c = this.read();
                if (isSpaceChar(c)) {
                    return res * sgn;
                }
            }

            throw new InputMismatchException();
        }

        public String next() {
            int c;
            while (isSpaceChar(c = this.read())) {
                ;
            }

            StringBuilder result = new StringBuilder();
            result.appendCodePoint(c);

            while (!isSpaceChar(c = this.read())) {
                result.appendCodePoint(c);
            }

            return result.toString();
        }

        public static boolean isSpaceChar(int c) {
            return c == 32 || c == 10 || c == 13 || c == 9 || c == -1;
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

        public void printf(String format, Object... objects) {
            writer.printf(format, objects);
        }

        public void close() {
            writer.close();
        }

        public void println(int i) {
            writer.println(i);
        }

    }
}
