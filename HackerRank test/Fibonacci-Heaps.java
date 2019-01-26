//Java program to implement FibonacciHeap
import java.util.*;
//Fibonacci Heap Node
class FibonacciHeapNode
{
	FibonacciHeapNode child,left,right,parent;
	int element;
	//Constructor
	public FibonacciHeapNode(int element)
	{
		this.right=this;
		this.left =this;
		this.element =element;
	}
}
class FibonacciHeap
{
	private FibonacciHeapNode root;
	private int count;
	public FibonacciHeap()
	{
		root=null;
		count=0;
	}
	//lets check if our heap is empty
	public boolean isEmpty()
	{
		return root ==null;
	}
	//to make heap empty
	public void clear()
	{
		root=null;
		count=0;
	}
	//insert Function
	public void insert(int element)
	{
		FibonacciHeapNode node =new FibonacciHeapNode(element);
		node.element =element;
		if( root !=null)
		{
			node.left=root;
			node.right = root.right;
			root.right =node;
			node.right.left =node;
			if(element<root.element)
				root = node;
		}
		else
		{
			root =node;
			count ++;
		}
		public void display()
		{
			System.out.print("\nHeap =");
			FibonacciHeapNode ptr =root;
			if(ptr==null)
			{
				System.out.println("Empty\n");
				return ;
			}
			do
			{
				System.out.print(ptr.element + " ");
				ptr =ptr.right;
			}
			while(ptr != root && ptr.right !=null);
			System.out.println();
		}
	}
}
//class FibonacciHeapTest
public class FibonacciHeapTest
{
	public static void main(String [] args)
	{
		Scanner in=new Scanner(System.in);
		System.out.println("FibonacciHeap Test\n\n");
		FibonacciHeap fh = new FibonacciHeap();
		char ch;
		//perform fibonacci operations
		do
		 {
            System.out.println("\nFibonacciHeap Operations\n");
            System.out.println("1. insert element ");
            System.out.println("2. check empty");            
            System.out.println("3. clear");
 
            int choice = scan.nextInt();            
            switch (choice)
            {
            case 1 : 
                System.out.println("Enter element");
                fh.insert( scan.nextInt() );                                    
                break;                          
            case 2 : 
                System.out.println("Empty status = "+ fh.isEmpty());
                break;   
            case 3 : 
                fh.clear();
                break;           
            default : 
                System.out.println("Wrong Entry \n ");
                break;   
            }           
            fh.display();
 
            System.out.println("\nDo you want to continue (Type y or n) \n");
            ch = scan.next().charAt(0);                        
        } while (ch == 'Y'|| ch == 'y'); 
	}
}