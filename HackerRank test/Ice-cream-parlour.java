import java.io.*;
import java.util.*;
public class Solution
{
 public static void main(String[] args){
	Scanner scan = new Scanner(System.in);
	int tryNumber = scan.nextInt();
	for(int z = 0;z<tryNumber;z++){
		int money = scan.nextInt();
		int options = scan.nextInt();
		List <Integer>list = new ArrayList<Integer>();
		for(int i =0; i<options;i++){
		list.add(scan.nextInt());	
			
		}
		for(int i =0; i<list.size();i++){
			for(int ii=i+1;ii<list.size();ii++){
			if(money==(int)list.get(i)+(int)list.get(ii)){
				System.out.println((i+1)+" "+(ii+1));
				break;
				}
			}
		}
	}
}
}