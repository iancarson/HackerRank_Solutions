import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

import java.util.*;

public class Solution{
    static Node root = new Node(1);

    public static void main(String ... arg)
    {
        Scanner sc = new Scanner(System.in);
        int n,t,k;
        n = sc.nextInt();
        int[][] tree = new int[n][2];
        for(int i=0;i<n;i++)
        {
            tree[i][0] = sc.nextInt();
            tree[i][1] = sc.nextInt();
        } 
        root = ConstuctTree(tree);
        t = sc.nextInt();
        while(t-->0)
        {
            k = sc.nextInt();
            levelWise(root,k);
            InOrderRec(root);
            System.out.println();
        }
    }

    public static void levelWise(Node root,int k)
    {
        Stack<Node> currentlevel = new Stack<>();
        Stack<Node> nextlevel = new Stack<>();
        int level=1;
        Node temp;
        currentlevel.push(root);
        while(!currentlevel.empty())
        {
            temp = currentlevel.peek();
            currentlevel.pop();
            if(temp.left!=null)
                nextlevel.push(temp.left);
            if(temp.right!=null)
                nextlevel.push(temp.right);
            if(level%k == 0)
            {
                Node n = temp.left;
                temp.left = temp.right;
                temp.right = n;
            }
            if(currentlevel.empty())
            {
                Stack<Node> t = currentlevel;
                currentlevel = nextlevel;
                nextlevel = t;
                level++;
            }
        }
    }

    public static void InOrderRec(Node root)
    {
        if(root == null)
            return;
        InOrderRec(root.left);
        sout(root.data);
        InOrderRec(root.right);
    }

    public static Node ConstuctTree(int[][] tree)
    {
        Node root = new Node(1);
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        for(int i=0;i<tree.length;i++)
        {
            Node left,right;
            if(tree[i][0]!=-1)
                left = new Node(tree[i][0]);
            else
                left = null;
            if(tree[i][1]!=-1)
                right= new Node(tree[i][1]);
            else
                right = null;

            Node temp = q.remove();
            temp.left = left;
            temp.right = right;

            if(left!=null)
                q.add(left);
            if(right!=null)
                q.add(right);
        }
        return root;
    }


    public static void sout(int info)
    {
        System.out.printf("%d ",info);
    }
}

class Node{
    int data;
    Node left,right;
    Node(int item)
    {
        data = item;
        left = null;
        right = null;
    }
}
