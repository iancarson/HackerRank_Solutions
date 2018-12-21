import java.io.*;
import java.util.*;
public class Permutations<T>
{
	public static void main(String[] args)
	{
	Permutations<Integer> obj=new Permutations<Integer>();
	Collection<Integer> input=new ArrayList<Integer>();
	Scanner in= new Scanner(System.in);
	int A=in.nextInt();
	int B=in.nextInt();
	int C=in.nextInt();
	int D=in.nextInt();
	int counter=0;
	input.add(A);
	input.add(B);
	input.add(C);
	input.add(D);
	Collection<List<Integer>> output=obj.permute(input);
	int k=0;
	Set<List<Integer>> pnr=null;
	for(int i=0;i<=input.size();i++)
	{
	pnr=new HashSet<List<Integer>>();
	for(List<Integer> integers : output)
	{
	pnr.add(integers.subList(i,integers.size()));
	}
	k=input.size()-i;
	//System.out.println("p(" + input.size()+ "," + k+"):" + "Count (" + pnr.size()+"):-" +pnr);
	System.out.println(output.size());
	}
		}
	public Collection<List<T>> permute(Collection<T> input)
	{
	Collection<List<T>> output=new ArrayList<List<T>>();
	if(input.isEmpty())
	{
	output.add(new ArrayList<T>());
	return output;
	}
	List<T> list=new ArrayList<T>(input);
	T head= list.get(0);
	List<T> rest=list.subList(1,list.size());
	for(List<T> permutations : permute(rest))
	{
	List<List<T>> sublists= new ArrayList<List<T>>();
	for(int i=0;i<=permutations.size();i++)
	{
	List<T> sublist=new ArrayList<T>();
	sublist.addAll(permutations);
	sublist.add(i,head);
	sublists.add(sublist);
	}
	output.addAll(sublists);
	}
	return output;
	}
	}