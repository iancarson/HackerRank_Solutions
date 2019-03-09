import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.util.Comparator;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.math.BigInteger;
import java.io.InputStream;


public class Solution
{
	public static void main(String [] args)
	{
		InputStream inputstream = System.in;
		OutputStream ouputstream = System.out;
		InputReader in = new InputReader(inputstream);
		OutputWriter out = new OutputWriter(outputstream);
		JackGoesToRapture solver = new JackGoesToRapture();
		solver.solve(1, in, out);
		out.close();
	}
}
class JackGoesToRapture {
	public void solve(int testNumber,InputReader in, OutputWriter out)
	{
		int count = in.readInt();
		int edgecount = in.readInt();
		int [] from = new int[edgecount];
		int [] to = new int[edgecount];
		int [] cost = new int[edgecount];
		IOUtils.readIntArrays(in,from,to ,cost);
		MiscUtils.decreaseByOne(cost,from,to);
		ArrayUtils.orderBy(cost, from, to);
		IndependentSystem setsystem = new RecursiveIndependentSetSystem(count);
		for(int i=0; i < edgecount; i++)
		{
			setSystem.join(from[i],to[i]);
			if(setSystem.get(0) == setSystem.get(count - 1))
			{
				out.printLine(cost[i]);
				return;
			}
		}
		out.printLine("NO PATH EXISTS");
	}
}