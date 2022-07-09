import java.util.*;

public class Solution {
    public static void main(String[] args) {
        MyQueue<Integer> queue = new MyQueue<Integer>();

        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();

        for (int i = 0; i < n; i++) {
            int operation = scan.nextInt();
            if (operation == 1) { // enqueue
                queue.enqueue(scan.nextInt());
            } else if (operation == 2) { // dequeue
                queue.dequeue();
            } else if (operation == 3) { // print/peek
                System.out.println(queue.peek());
            }
        }
        scan.close();
    }
}
class MyQueue <T>
{
    Stack<T> Newest = new Stack<T>();
    Stack<T> old = new Stack<T>();
    public void enqueue(T element)
    {
        Newest.push(element);
    }
    public void swap()
    {
       if(old.isEmpty())
       {
           while(!Newest.isEmpty())
           {
               old.push(Newest.pop());
           }
       }
    }
    public void dequeue()
    {
        swap();
        old.pop();
      //  Lifo.pop();
    }
    public T peek()
    {
        swap();
        T top = old.peek();
        return top;
    }
}
