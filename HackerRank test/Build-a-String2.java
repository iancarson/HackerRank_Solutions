import java.util.Scanner;

public class Solution {

public static void main(String[] args) {
    // TODO Auto-generated method stub

    Scanner sc = new Scanner(System.in);

    int num = sc.nextInt();
    for(int i=0;i<num;i++){
        int N,A,B;
        String requiredStr;
        N = sc.nextInt();
        A = sc.nextInt();
        B = sc.nextInt();
        requiredStr = sc.next();
        String requiredStrCopy = requiredStr;
        String formedStr = null; StringBuffer sbFormedStr = new StringBuffer();
        int cost=0;
        while(requiredStrCopy.length()>0){
            if(requiredStrCopy.length() == requiredStr.length()){
                sbFormedStr.append(requiredStrCopy.charAt(0));
                cost=cost+A;
                requiredStrCopy= requiredStrCopy.substring(1);
            }else{
                String returnedStr = returnSubStringIfAvailableAndCheaper(requiredStrCopy,sbFormedStr.toString(),A,B);
                if(returnedStr!=null){
                    sbFormedStr.append(returnedStr);
                    cost=cost+B;
                    requiredStrCopy= requiredStrCopy.substring(returnedStr.length());
                }else{
                    sbFormedStr.append(requiredStrCopy.charAt(0));
                    cost=cost+A;
                    requiredStrCopy= requiredStrCopy.substring(1);
                }
            }
        }
        System.out.println(cost);
    }

}



private static String returnSubStringIfAvailableAndCheaper(String pending, String completed, int A, int B) {
    String subStr=null;
    int tempSubStrLen=0;
    while(tempSubStrLen<=pending.length()-1){
        int index = completed.indexOf(pending.substring(0, tempSubStrLen+1));
        if(index>-1){
            subStr = pending.substring(0, tempSubStrLen+1);
            tempSubStrLen++;
        }else
            break;
    }
    if(subStr!=null && B<(subStr.length()*A)){
        return subStr;
    }else{
        return null;
    }
}

}