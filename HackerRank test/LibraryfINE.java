import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.util.concurrent.*;
public class Solution
{
  public static void main(String[] args)
  {
    Scanner in=new Scanner(System.in);
    int returnDay=in.nextInt();
    int returnMonth=in.nextInt();
    int returnYear=in.nextInt();
    
    int expectedDay=in.nextInt();
    int expectedMonth=in.nextInt();
    int expectedYear=in.nextInt();
    int fine=0;
    if(expectedYear <returnYear)
    {
      fine=10000;
    }
    else if(expectedYear == returnYear)
    {
      if(returnMonth> expectedMonth)
      {
        fine=(returnMonth - expectedMonth) * 500;
      }
      else if( returnMonth==expectedMonth)
      {
        if(returnDay> expectedDay)
        {
          fine=(returnDay - expectedDay) * 15;
        }
      }
    }
    System.out.println(fine);
  }
  }