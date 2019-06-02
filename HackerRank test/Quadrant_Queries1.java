import java.io.*;
import java.util.*;

public class Solution {
	
	//public static PrintWriter out;
	
	public static void main (String[] args) throws IOException{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("test.in"));
		//out = new PrintWriter(new FileWriter("ans.out"));
		int[] points = new int[Integer.parseInt(in.readLine())];
		for (int i=0; i<points.length; i++){
			StringTokenizer st = new StringTokenizer(in.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			if (x<0)
				points[i]++;
			if (y<0)
				points[i] = 3-points[i];
		}
		Node root = new Node(0, points.length-1, points);
		
		int queries = Integer.parseInt(in.readLine());
		for (int i=0; i<queries; i++){
			StringTokenizer st = new StringTokenizer(in.readLine());
			char query = st.nextToken().charAt(0);
			int start = Integer.parseInt(st.nextToken())-1;
			int end = Integer.parseInt(st.nextToken())-1;
			
			//output(root);
			
			switch(query){
			case 'X': root.reflectX(start, end);
				break;
			case 'Y': root.reflectY(start, end);
				break;
			case 'C': print(root.countQuad(start, end));
				break;
			}
		}
	}
	
	public static void output(Node root){
		if (root==null) return;
		System.out.print(root.start + " " + root.end + " :");
		for (int i=0; i<4; i++)
			System.out.print(" "+root.quadCount[i]);
		System.out.println();
		output(root.left);
		output(root.right);
	}
	
	public static void print(int[] toPrint){
		System.out.print(toPrint[0]);
		for (int i=1; i<toPrint.length; i++)
			System.out.print(" "+toPrint[i]);
		System.out.println();
	}
}

class Node{
	public int start;
	public int end;
	
	public boolean flipX;
	public boolean flipY;
	
	public Node left;
	public Node right;
	
	public int[] quadCount;
	
	public Node(int begin, int finish, int[] coords){
		start = begin;
		end = finish;
		
		flipX = false;
		flipY = false;
		
		left = null;
		right = null;	
		quadCount = new int[4];
		if (start==end)
			quadCount[coords[start]] = 1;
		else{
			left = new Node(start, (start+end)/2, coords);
			right = new Node(left.end+1, end, coords);
			updateQuadCount();
		}
	}
	
	public void reflectX(int begin, int finish){
		if (begin == start && finish == end){
			flipX = !flipX;
			swap(quadCount, 0,3);
			swap(quadCount, 1,2);
			return;
		}
		if (flipY){
			flipY = false;
			left.reflectY(start, left.end);
			right.reflectY(right.start, end);
		}
		if (begin>left.end){
			if (flipX){
				flipX = false;
				left.reflectX(start, left.end);
				if (begin>right.start)
					right.reflectX(right.start, begin-1);
				if (finish<right.end)
					right.reflectX(finish+1, right.end);
			}
			else
				right.reflectX(begin, finish);
		}
		else if (finish<right.start){
			if (flipX){
				flipX = false;
				right.reflectX(right.start, end);
				if (begin>left.start)
					left.reflectX(left.start, begin-1);
				if (finish<left.end)
					left.reflectX(finish+1, left.end);
			}
			else
				left.reflectX(begin, finish);
		}
		else{
			if (flipX){
				flipX = false;
				if (begin>left.start)
					left.reflectX(left.start, begin-1);
				if (finish<right.end)
					right.reflectX(finish+1, right.end);
			}
			else{
				left.reflectX(begin, left.end);
				right.reflectX(right.start, finish);
			}
		}	
		updateQuadCount();
	}
	
	public void reflectY(int begin, int finish){
		if (begin == start && finish == end){
			flipY = !flipY;
			swap(quadCount, 0,1);
			swap(quadCount, 2,3);
			return;
		}
		if (flipX){
			flipX = false;
			left.reflectX(start, left.end);
			right.reflectX(right.start, end);
		}
		if (begin>left.end){
			if (flipY){
				flipY = false;
				left.reflectY(start, left.end);
				if (begin>right.start)
					right.reflectY(right.start, begin-1);
				if (finish<right.end)
					right.reflectY(finish+1, right.end);
			}
			else
				right.reflectY(begin, finish);
		}
		else if (finish<right.start){
			if (flipY){
				flipY = false;
				right.reflectY(right.start, end);
				if (begin>left.start)
					left.reflectY(left.start, begin-1);
				if (finish<left.end)
					left.reflectY(finish+1, left.end);
			}
			else
				left.reflectY(begin, finish);
		}
		else{
			if (flipY){
				flipY = false;
				if (begin>left.start)
					left.reflectY(left.start, begin-1);
				if (finish<right.end)
					right.reflectY(finish+1, right.end);
			}
			else{
				left.reflectY(begin, left.end);
				right.reflectY(right.start, finish);
			}
		}	
		updateQuadCount();
	}
	
	public int[] countQuad(int begin, int finish){
		if (flipX){
			flipX = false;
			if (left!=null){
				left.reflectX(start, left.end);
				right.reflectX(right.start, end);
			}
		}
		if (flipY){
			flipY=false;
			if (left!=null){
				left.reflectY(start, left.end);
				right.reflectY(right.start, end);
			}
		}
		
		if (begin==start && finish==end)
			return quadCount;
		else if (begin>left.end)
			return right.countQuad(begin, finish);
		else if (finish<right.start)
			return left.countQuad(begin, finish);
		else{
			int[] leftCount = left.countQuad(begin, left.end);
			int[] rightCount = right.countQuad(right.start, finish);
			int[] ret = new int[leftCount.length];
			for (int i=0; i<ret.length; i++)
				ret[i] = leftCount[i]+rightCount[i];
			return ret;
		}
	}
	
	private void updateQuadCount(){
		for (int i=0; i<quadCount.length; i++)
			quadCount[i] = left.quadCount[i]+right.quadCount[i];
	}
	
	private void swap(int[] arr, int ind1, int ind2){
		int temp = arr[ind1];
		arr[ind1]=arr[ind2];
		arr[ind2]=temp;
	}
}