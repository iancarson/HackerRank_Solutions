import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.io.InputStreamReader;
import java.util.TreeSet;
import java.util.HashSet;
import java.io.Writer;
import java.io.BufferedReader;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        FastScanner in = new FastScanner(inputStream);
        FastPrinter out = new FastPrinter(outputStream);
        HackerRank1 solver = new HackerRank1();
        solver.solve(1, in, out);
        out.close();
    }

    static class HackerRank1 {
        static long mod =  1000000007 ;
        static long pp = 29;
        static long pp1 = 37;
        public void solve(int testNumber, FastScanner in, FastPrinter out) {
            int t = in.nextInt();
            long[] pow = new long[1205];
            pow[0] = 1;
            
            for(int i=1;i<pow.length;i++)
             {
                pow[i] = pow[i-1]*pp;
                if(pow[i]>=mod)
                    pow[i]%=mod;
            }
           
            long[] pow1 = new long[1205];
            pow1[0] = 1;
            
            for(int i=1;i<pow1.length;i++)
             {
                pow1[i] = pow1[i-1]*pp1;
                if(pow1[i]>=mod)
                    pow1[i]%=mod;
            }
           
        
            for(int z=0;z<t;z++)
            {
                int n = in.nextInt();
                String s = in.next();
                long[][] a = new long[n][2];
                for(int i=0;i<n;i++)
                    a[i][0] = (long)s.charAt(i)-(long)'a';
                s = in.next();
                for(int i=0;i<n;i++)
                    a[i][1] = (long)s.charAt(i)-(long)'a';
                
                HashSet<Long> hs = new HashSet<Long>();
               
                
                long[][] toLeft = new long[n][2];
                long[][] toRight = new long[n][2];
                toLeft[0][0] = 0;
                toLeft[0][1] = 0;
                
                for(int j=1;j<n;j++)
                {  
                    toLeft[j][0] = a[j-1][1] * pow[2*j-1] + toLeft[j-1][0] * pow[1] + a[j-1][0];
                    toLeft[j][1] = a[j-1][0] * pow[2*j-1]+ toLeft[j-1][1] * pow[1] + a[j-1][1];
                    if(toLeft[j][0]>=mod)
                        toLeft[j][0]%=mod;
                    if(toLeft[j][1]>=mod)
                        toLeft[j][1]%=mod;
                }
                toRight[n-1][0] = 0;
                toRight[n-1][1] = 0;
                for(int j=n-2;j>=0;j--)
                 {
                   toRight[j][0] = a[j+1][0]*pow[2*(n-1-j)-1] + toRight[j+1][0]*pow[1] + a[j+1][1];
                   toRight[j][1] = a[j+1][1]*pow[2*(n-1-j)-1] + toRight[j+1][1]*pow[1] + a[j+1][0];
                       if(toRight[j][0]>=mod)
                        toRight[j][0]%=mod;
                    if(toRight[j][1]>=mod)
                        toRight[j][1]%=mod;
                 
                }
                
                  long[][] toLeft1 = new long[n][2];
                long[][] toRight1 = new long[n][2];
                toLeft1[0][0] = 0;
                toLeft1[0][1] = 0;
                
                for(int j=1;j<n;j++)
                {  
                    toLeft1[j][0] = a[j-1][1] * pow1[2*j-1] + toLeft1[j-1][0] * pow1[1] + a[j-1][0];
                    toLeft1[j][1] = a[j-1][0] * pow1[2*j-1]+ toLeft1[j-1][1] * pow1[1] + a[j-1][1];
                    if(toLeft1[j][0]>=mod)
                        toLeft1[j][0]%=mod;
                    if(toLeft1[j][1]>=mod)
                        toLeft1[j][1]%=mod;
                }
                toRight1[n-1][0] = 0;
                toRight1[n-1][1] = 0;
                for(int j=n-2;j>=0;j--)
                 {
                   toRight1[j][0] = a[j+1][0]*pow1[2*(n-1-j)-1] + toRight1[j+1][0]*pow1[1] + a[j+1][1];
                   toRight1[j][1] = a[j+1][1]*pow1[2*(n-1-j)-1] + toRight1[j+1][1]*pow1[1] + a[j+1][0];
                       if(toRight1[j][0]>=mod)
                        toRight1[j][0]%=mod;
                    if(toRight1[j][1]>=mod)
                        toRight1[j][1]%=mod;
                 
                }
                
                for(int st=0;st<n;st++)
                {
                    long l1 = toLeft[st][1];
                    long l2 = toLeft[st][0];
                    long l11 = toLeft1[st][1];
                    long l21 = toLeft1[st][0];
                    for(int en=st;en<n;en++)
                    {
                     
                      //out.println(sb1.toString());
                        
                        // down
                      {  {
                            if((en-st)%2==0)
                             {l1= l1*pow[2]+ a[en][1]*pow[1]+a[en][0];
                              if(l1>=mod)
                                  l1%=mod;
                              }
                            else
                             {
                              l1= l1*pow[2]+ a[en][0]*pow[1]+a[en][1];
                              if(l1>=mod)
                                   l1%=mod;
                             }
                        }
                        long f1;
                        if((en-st+1)%2==1)
                        {
                            f1 = l1*pow[2*(n-1-en)]+toRight[en][0];
                            if(f1>=mod)
                                f1%=mod;
                        }
                        else
                         {
                           f1 = l1*pow[2*(n-1-en)]+toRight[en][1];
                           if(f1>=mod)
                                f1%=mod;
                        }
                       
                        {
                            if((en-st)%2==0)
                             {l11= l11*pow1[2]+ a[en][1]*pow1[1]+a[en][0];
                              if(l11>=mod)
                                  l11%=mod;
                              }
                            else
                             {
                              l11= l11*pow1[2]+ a[en][0]*pow1[1]+a[en][1];
                              if(l11>=mod)
                                   l11%=mod;
                             }
                        }
                        long f11;
                        if((en-st+1)%2==1)
                        {
                            f11 = l11*pow1[2*(n-1-en)]+toRight1[en][0];
                            if(f11>=mod)
                                f11%=mod;
                        }
                        else
                         {
                           f11 = l11*pow1[2*(n-1-en)]+toRight1[en][1];
                           if(f11>=mod)
                                f11%=mod;
                        }
                        //out.println(sb1.toString());
                        hs.add(f1*f11);
                        //hs.add(sb1.reverse().toString());
                      }
                        // up
                         {  {
                            if((en-st)%2==1)
                             {l2= l2*pow[2]+ a[en][1]*pow[1]+a[en][0];
                              if(l2>=mod)
                                  l2%=mod;
                              }
                            else
                             {
                              l2= l2*pow[2]+ a[en][0]*pow[1]+a[en][1];
                              if(l2>=mod)
                                   l2%=mod;
                             }
                        }
                        long f2;
                        if((en-st+1)%2==1)
                        {
                            f2 = l2*pow[2*(n-1-en)]+toRight[en][1];
                            if(f2>=mod)
                                f2%=mod;
                        }
                        else
                         {
                           f2 = l2*pow[2*(n-1-en)]+toRight[en][0];
                           if(f2>=mod)
                                f2%=mod;
                        }
                        //out.println(sb1.toString());
                        {
                            if((en-st)%2==1)
                             {l21= l21*pow1[2]+ a[en][1]*pow1[1]+a[en][0];
                              if(l21>=mod)
                                  l21%=mod;
                              }
                            else
                             {
                              l21= l21*pow1[2]+ a[en][0]*pow1[1]+a[en][1];
                              if(l21>=mod)
                                   l21%=mod;
                             }
                        }
                        long f21;
                        if((en-st+1)%2==1)
                        {
                            f21 = l21*pow1[2*(n-1-en)]+toRight1[en][1];
                            if(f21>=mod)
                                f21%=mod;
                        }
                        else
                         {
                           f21 = l21*pow1[2*(n-1-en)]+toRight1[en][0];
                           if(f21>=mod)
                                f21%=mod;
                        }  
                          
                        hs.add(f2*f21);
                        //hs.add(sb1.reverse().toString());
                      }
                    
                    
                }
                }
                
                for(int i=0;i<n;i++)
                {
                    long l=0;
                    long l1=0;
                    for(int j=i;j<n;j++)
                    {   l*=pow[1];
                        l+=a[j][1];
                      if(l>=mod)l%=mod;  
                      l1*=pow1[1];
                        l1+=a[j][1];
                      if(l1>=mod)l1%=mod;  
                    }
                    for(int j=n-1;j>=0;j--)
                      {   l*=pow[1];
                        l+=a[j][0];
                      if(l>=mod)l%=mod;  
                        l1*=pow1[1];
                        l1+=a[j][0];
                      if(l1>=mod)l1%=mod;  
                    }
                    for(int j=0;j<i;j++)
                        {   l*=pow[1];
                        l+=a[j][1];
                      if(l>=mod)l%=mod;  
                          l1*=pow1[1];
                        l1+=a[j][1];
                      if(l1>=mod)l1%=mod;  
                    }
                       
                     hs.add(l*l1);
               //      hs.add(sb.reverse().toString());
                }
                
                for(int i=0;i<n;i++)
                {
                   long l=0;
                    long l1=0;
                    for(int j=i;j<n;j++)
                     {   l*=pow[1];
                        l+=a[j][0];
                      if(l>=mod)l%=mod; 
                      l1*=pow1[1];
                        l1+=a[j][0];
                      if(l1>=mod)l1%=mod; 
                    }for(int j=n-1;j>=0;j--)
                     {   l*=pow[1];
                        l+=a[j][1];
                      if(l>=mod)l%=mod; 
                       l1*=pow1[1];
                        l1+=a[j][1];
                      if(l1>=mod)l1%=mod; 
                    }for(int j=0;j<i;j++)
                     {   l*=pow[1];
                        l+=a[j][0];
                      if(l>=mod)l%=mod; 
                       l1*=pow1[1];
                        l1+=a[j][0];
                      if(l1>=mod)l1%=mod; 
                    }  
                    hs.add(l*l1);
            //         hs.add(sb.reverse().toString());
                }
                    
                      for(int i=0;i<n;i++)
                {
                    long l=0;
                    long l1=0;
                    for(int j=i;j>=0;j--)
                    {   l*=pow[1];
                        l+=a[j][1];
                      if(l>=mod)l%=mod;
                      l1*=pow1[1];
                        l1+=a[j][1];
                      if(l1>=mod)l1%=mod; 
                    }
                    for(int j=0;j<n;j++)
                      {   l*=pow[1];
                        l+=a[j][0];
                      if(l>=mod)l%=mod; 
                        l1*=pow1[1];
                        l1+=a[j][0];
                      if(l1>=mod)l1%=mod; 
                    }
                    for(int j=n-1;j>i;j--)
                        {   l*=pow[1];
                        l+=a[j][1];
                      if(l>=mod)l%=mod; 
                          l1*=pow1[1];
                        l1+=a[j][1];
                      if(l1>=mod)l1%=mod; 
                    }
                       
                     hs.add(l*l1);
               //      hs.add(sb.reverse().toString());
                }
                
                for(int i=0;i<n;i++)
                {
                   long l=0,l1=0;
                    for(int j=i;j>=0;j--)
                     {   l*=pow[1];
                        l+=a[j][0];
                      if(l>=mod)l%=mod;  
                       l1*=pow1[1];
                        l1+=a[j][0];
                      if(l1>=mod)l1%=mod; 
                    }for(int j=0;j<n;j++)
                     {   l*=pow[1];
                        l+=a[j][1];
                      if(l>=mod)l%=mod; 
                       l1*=pow1[1];
                        l1+=a[j][1];
                      if(l1>=mod)l1%=mod; 
                    }for(int j=n-1;j>i;j--)
                     {   l*=pow[1];
                        l+=a[j][0];
                      if(l>=mod)l%=mod;  
                       l1*=pow1[1];
                        l1+=a[j][0];
                      if(l1>=mod)l1%=mod; 
                    }  
                    hs.add(l*l1);
            //         hs.add(sb.reverse().toString());
                }
              //////////////////////////////lllll
                for(int i=0;i<n/2;i++)
                 {
                    for(int j=0;j<2;j++)
                     {
                        long ttt = a[i][j];
                        a[i][j]=a[n-1-i][j];
                        a[n-1-i][j]=ttt;
                    }
                }
                
                toLeft[0][0] = 0;
                toLeft[0][1] = 0;
                
                for(int j=1;j<n;j++)
                {  
                    toLeft[j][0] = a[j-1][1] * pow[2*j-1] + toLeft[j-1][0] * pow[1] + a[j-1][0];
                    toLeft[j][1] = a[j-1][0] * pow[2*j-1]+ toLeft[j-1][1] * pow[1] + a[j-1][1];
                    if(toLeft[j][0]>=mod)
                        toLeft[j][0]%=mod;
                    if(toLeft[j][1]>=mod)
                        toLeft[j][1]%=mod;
                }
                toRight[n-1][0] = 0;
                toRight[n-1][1] = 0;
                for(int j=n-2;j>=0;j--)
                 {
                   toRight[j][0] = a[j+1][0]*pow[2*(n-1-j)-1] + toRight[j+1][0]*pow[1] + a[j+1][1];
                   toRight[j][1] = a[j+1][1]*pow[2*(n-1-j)-1] + toRight[j+1][1]*pow[1] + a[j+1][0];
                       if(toRight[j][0]>=mod)
                        toRight[j][0]%=mod;
                    if(toRight[j][1]>=mod)
                        toRight[j][1]%=mod;
                 
                }
                
                toLeft1[0][0] = 0;
                toLeft1[0][1] = 0;
                
                for(int j=1;j<n;j++)
                {  
                    toLeft1[j][0] = a[j-1][1] * pow1[2*j-1] + toLeft1[j-1][0] * pow1[1] + a[j-1][0];
                    toLeft1[j][1] = a[j-1][0] * pow1[2*j-1]+ toLeft1[j-1][1] * pow1[1] + a[j-1][1];
                    if(toLeft1[j][0]>=mod)
                        toLeft1[j][0]%=mod;
                    if(toLeft1[j][1]>=mod)
                        toLeft1[j][1]%=mod;
                }
                toRight1[n-1][0] = 0;
                toRight1[n-1][1] = 0;
                for(int j=n-2;j>=0;j--)
                 {
                   toRight1[j][0] = a[j+1][0]*pow1[2*(n-1-j)-1] + toRight1[j+1][0]*pow1[1] + a[j+1][1];
                   toRight1[j][1] = a[j+1][1]*pow1[2*(n-1-j)-1] + toRight1[j+1][1]*pow1[1] + a[j+1][0];
                       if(toRight1[j][0]>=mod)
                        toRight1[j][0]%=mod;
                    if(toRight1[j][1]>=mod)
                        toRight1[j][1]%=mod;
                 
                }
                
                for(int st=0;st<n;st++)
                {
                    long l1 = toLeft[st][1];
                    long l2 = toLeft[st][0];
                    long l11 = toLeft1[st][1];
                    long l21 = toLeft1[st][0];
                    for(int en=st;en<n;en++)
                    {
                     
                      //out.println(sb1.toString());
                        
                        // down
                      {  {
                            if((en-st)%2==0)
                             {l1= l1*pow[2]+ a[en][1]*pow[1]+a[en][0];
                              if(l1>=mod)
                                  l1%=mod;
                              }
                            else
                             {
                              l1= l1*pow[2]+ a[en][0]*pow[1]+a[en][1];
                              if(l1>=mod)
                                   l1%=mod;
                             }
                        }
                        long f1;
                        if((en-st+1)%2==1)
                        {
                            f1 = l1*pow[2*(n-1-en)]+toRight[en][0];
                            if(f1>=mod)
                                f1%=mod;
                        }
                        else
                         {
                           f1 = l1*pow[2*(n-1-en)]+toRight[en][1];
                           if(f1>=mod)
                                f1%=mod;
                        }
                       
                        {
                            if((en-st)%2==0)
                             {l11= l11*pow1[2]+ a[en][1]*pow1[1]+a[en][0];
                              if(l11>=mod)
                                  l11%=mod;
                              }
                            else
                             {
                              l11= l11*pow1[2]+ a[en][0]*pow1[1]+a[en][1];
                              if(l11>=mod)
                                   l11%=mod;
                             }
                        }
                        long f11;
                        if((en-st+1)%2==1)
                        {
                            f11 = l11*pow1[2*(n-1-en)]+toRight1[en][0];
                            if(f11>=mod)
                                f11%=mod;
                        }
                        else
                         {
                           f11 = l11*pow1[2*(n-1-en)]+toRight1[en][1];
                           if(f11>=mod)
                                f11%=mod;
                        }
                        //out.println(sb1.toString());
                        hs.add(f1*f11);
                        //hs.add(sb1.reverse().toString());
                      }
                        // up
                         {  {
                            if((en-st)%2==1)
                             {l2= l2*pow[2]+ a[en][1]*pow[1]+a[en][0];
                              if(l2>=mod)
                                  l2%=mod;
                              }
                            else
                             {
                              l2= l2*pow[2]+ a[en][0]*pow[1]+a[en][1];
                              if(l2>=mod)
                                   l2%=mod;
                             }
                        }
                        long f2;
                        if((en-st+1)%2==1)
                        {
                            f2 = l2*pow[2*(n-1-en)]+toRight[en][1];
                            if(f2>=mod)
                                f2%=mod;
                        }
                        else
                         {
                           f2 = l2*pow[2*(n-1-en)]+toRight[en][0];
                           if(f2>=mod)
                                f2%=mod;
                        }
                        //out.println(sb1.toString());
                        {
                            if((en-st)%2==1)
                             {l21= l21*pow1[2]+ a[en][1]*pow1[1]+a[en][0];
                              if(l21>=mod)
                                  l21%=mod;
                              }
                            else
                             {
                              l21= l21*pow1[2]+ a[en][0]*pow1[1]+a[en][1];
                              if(l21>=mod)
                                   l21%=mod;
                             }
                        }
                        long f21;
                        if((en-st+1)%2==1)
                        {
                            f21 = l21*pow1[2*(n-1-en)]+toRight1[en][1];
                            if(f21>=mod)
                                f21%=mod;
                        }
                        else
                         {
                           f21 = l21*pow1[2*(n-1-en)]+toRight1[en][0];
                           if(f21>=mod)
                                f21%=mod;
                        }  
                          
                        hs.add(f2*f21);
                        //hs.add(sb1.reverse().toString());
                      }
                    
                    
                }
                }
           
                
             
                    
                
                
               out.println(hs.size());  
                
                
                
                
            }
                
        }

    }

    static class FastPrinter extends PrintWriter {

    public FastPrinter(OutputStream out) {
        super(out);
    }

    public FastPrinter(Writer out) {
        super(out);
    }

    public void printArray(int[] a) {
        for (int i = 0; i < a.length; i++) {
            if (i > 0) {
                print(' ');
            }
            print(a[i]);
        }
        println();
    }

    public void close()
    {
        super.close();
    }


}


    static class FastScanner extends BufferedReader {

    boolean isEOF;

    public FastScanner(InputStream is) {
        super(new InputStreamReader(is));
    }

    @Override
    public int read() {
        try {
            int ret = super.read();
//            if (isEOF && ret < 0) {
//                throw new InputMismatchException();
//            }
//            isEOF = ret == -1;
            return ret;
        } catch (IOException e) {
            throw new InputMismatchException();
        }
    }

    public String next() {
        StringBuilder sb = new StringBuilder();
        int c = read();
        while (isWhiteSpace(c)) {
            c = read();
        }
        if (c < 0) {
            return null;
        }
        while (c >= 0 && !isWhiteSpace(c)) {
            sb.appendCodePoint(c);
            c = read();
        }
        return sb.toString();
    }

    static boolean isWhiteSpace(int c) {
        return c >= 0 && c <= 32;
    }

    public String nextToken() {
        return next();
    }

    public int nextInt() {
        int c = read();
        while (isWhiteSpace(c)) {
            c = read();
        }
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = read();
        }
        int ret = 0;
        while (c >= 0 && !isWhiteSpace(c)) {
            if (c < '0' || c > '9') {
                throw new NumberFormatException("digit expected " + (char) c
                        + " found");
            }
            ret = ret * 10 + c - '0';
            c = read();
        }
        return ret * sgn;
    }

    public char nextChar() {
        int c = read();
        while (isWhiteSpace(c)) {
            c = read();
        }
        return (char) c;
    }

    public long nextLong() {
        return Long.parseLong(next());
    }

    public double nextDouble() {
        return Double.parseDouble(next());
    }

    public String nextLine() {
        int c = read();
        String ret = readLine();
        if (ret == null) {
            return ret;
        }
        if (c != 13) {
            return (char) c + ret;
        }
        return ret;
    }

    public String readLine() {
        try {
            return super.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public int readTimeHM(char delim) {
        String s = next();
        int pos = s.indexOf(delim);
        if (pos < 0) {
            throw new NumberFormatException("no delim found");
        }
        return Integer.parseInt(s.substring(0, pos)) * 60 + Integer.parseInt(s.substring(pos + 1));
    }

    public int readTimeHMS(char delim1, char delim2) {
        String s = next();
        int pos = s.indexOf(delim1);
        if (pos < 0) {
            throw new NumberFormatException("no delim found");
        }
        int pos2 = s.indexOf(delim2, pos + 1);
        if (pos2 < 0) {
            throw new NumberFormatException("no second delim found");
        }
        return Integer.parseInt(s.substring(0, pos)) * 3600 + Integer.parseInt(s.substring(pos + 1, pos2)) * 60
                + Integer.parseInt(s.substring(pos2 + 1));
    }

    public int readTimeHMS(char delim) {
        return readTimeHMS(delim, delim);
    }

    public int[] readIntArray(int n) {
        int[] ret = new int[n];
        for (int i = 0; i < n; i++) {
            ret[i] = nextInt();
        }
        return ret;
    }

    public int[][] readInt2DArray(int n, int m) {
        int[][] ret = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ret[i][j] = nextInt();
            }
        }
        return ret;
    }

    public long[] readLongArray(int n) {
        long[] ret = new long[n];
        for (int i = 0; i < n; i++) {
            ret[i] = nextLong();
        }
        return ret;
    }

    public double[] readDoubleArray(int n) {
        double[] ret = new double[n];
        for (int i = 0; i < n; i++) {
            ret[i] = nextDouble();
        }
        return ret;
    }

    public String[] readTokenArray(int n) {
        String[] ret = new String[n];
        for (int i = 0; i < n; i++) {
            ret[i] = next();
        }
        return ret;
    }

    public char[][] readCharacterFieldTokens(int n, int m) {
        char[][] ret = new char[n][];
        for (int i = 0; i < n; i++) {
            ret[i] = next().toCharArray();
            if (ret[i].length != m) {
                throw new AssertionError("length expected " + m + ", found " + ret[i].length);
            }
        }
        return ret;
    }

}
}