import java.io.BufferedReader;
import java.util.*;
import java.io.IOException;
import java.io.InputStreamReader;
public class suffixArray
{
  private String [] text;
  private int length;
  private int [] index;
  private String [] suffix;
  public suffixArray(String text)
  {
    this.text=new String[text.length()];
    for(int i=0;i<text.length();i++)
    {
      this.text[i]=text.substring(i,i+1);
    }
    this.length=text.length();
    this.index= new int[length];
    for(int i=0;i<length;i++)
    {
      index[i]=i;
    }
    suffix=new String[length];
  }
  public String createSuffixArray()
  {
    for(int index=0;index<length;index++)
    {
      String text="";
      for(int text_index=index;text_index<length;text_index++)
      {
        text +=this.text[text_index];
      }
      suffix[index]=text;
    }
    int back;
    String s="";
    for(int iteration=1;iteration<length;iteration ++)
    {
      String key=suffix[iteration];
      int KeyIndex=index[iteration];
      for(back=iteration- 1;back>=0;back--)
      {
        if(suffix[back].compareTo(key)>0)
        {
          suffix[back + 1]=suffix[back];
          index[back + 1]=index[back];
        }
        else
        {
          break;
        }
      }
      suffix[back + 1]=key;
      index[back + 1]=KeyIndex;
    }
      s=suffix.toString();
      return s;
  }
  public static void main(String[] args) throws IOException
  {
    BufferedReader reader= new BufferedReader(new InputStreamReader(System.in));
    int t=Integer.parseInt(reader.readLine());
    
    for(int i=0;i<t;i++)
    {
      String S=reader.readLine(0);
      int n=Integer.parseInt(reader.readLine());
      suffixArray sa=new suffixArray(S);
      String s=sa.createSuffixArray();
      System.out.println(s.charAt(n));
    }
  }
}
      
   
      
                     