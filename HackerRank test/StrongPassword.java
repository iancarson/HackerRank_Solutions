import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    static int minimumNumber(int n, String password) {
        int digitCount = 0;
        int lowerCount = 0;
        int upperCount = 0;
        int specialCount = 0;
        
        int addCount = 0;
        
        for(char c : password.toCharArray()){
            if(Character.isDigit(c)){
                digitCount++;
            }
            else if(Character.isLowerCase(c)){
                lowerCount++;
            }
            else if(Character.isUpperCase(c)){
                upperCount++;
            }
            else{
                specialCount++;
            }
        }
        
        if(digitCount == 0){
            // add a digit
            addCount++;
            digitCount++;
        }
        
        if(lowerCount == 0){
            // add a lower case character
            addCount++;
            lowerCount++;
        }
        
        if(upperCount == 0){
            // add a lower case character
            addCount++;
            upperCount++;
        }
        
        if(specialCount == 0){
            // add a special character
            addCount++;
            specialCount++;
        }
        
        
        int total = digitCount + lowerCount + upperCount + specialCount;
        
        // check if total length is 6 or less
        if(total - 6 < 0){
            addCount += 6 - total;
        }
        
        return addCount;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        String password = in.next();
        int answer = minimumNumber(n, password);
        System.out.println(answer);
        in.close();
    }
}