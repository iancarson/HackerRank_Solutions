import java.io.*;
import java.util.*;
public class Trie
{
  static final int modulo=100000;//the maximum size of the array can be
  
  static class TrieNode
  {
    TrieNode [] children= new TrieNode[modulo];
    boolean isEndOfWord;//to indicate if the node is the end
    TrieNode(){
      isEndOfWord=false;
      for(int i=0;i<children.length;i++)
        children[i]=null;
      }
    };
    static TrieNode root;
    //if not present keys into the trie
    //If the key is a prefix of trie node,
    //just marks as a leaf node;
    static void insert(String s)
    {
      int level;
      int length=s.length();
      int index;
      TrieNode pcrawl=root;
      for(level=0;level<length;level++)
      {
        index=s.charAt(level)-'a';
      if( pcrawl.children[index]==null)
        pcrawl.children[index]=new TrieNode();
        pcrawl=pcrawl.children[index];
      
        pcrawl.isEndOfWord=true;
      }
    }
    //Returns true if the key is present in the Trie,else false
    static boolean search(String key)
    {
      int level;
      int length=key.length();
      int index;
      TrieNode pcrawl=root;
      for(level=0;level<length;level++)
      {
        index=key.charAt(level)-'a';
        if(pcrawl.children[index]==null)
          return false;
        pcrawl=pcrawl.children[index];
      }
      return (pcrawl !=null && pcrawl.isEndOfWord);
    }
    public static void main(String[] args)
    {
      Scanner in=new Scanner(System.in);
      int t=in.nextInt();
      int count=1;
      for(int i=0;i<t;i++)
      {
        String s=in.next();
        root=new TrieNode();
        insert(s);
        char [] sarr=s.toCharArray();
        int k;
        int mod;
        for(k=0;k<sarr.length;k++)
        { 
          if(((int)sarr[k])%2==0)
          {
          mod=(int)sarr[k];
          if(search(String.valueOf(mod)))
            count +=count;
          }
        }
        if(count>1)
          System.out.println(count);
        else
          System.out.println("0");
      }
    }
  }


        
    
  