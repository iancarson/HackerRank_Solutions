import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        int b = in.nextInt();
        int f = in.nextInt();
        int c = in.nextInt();
        // your code goes here
        int minNodes = getMinNodes(t,b,f,c);
        if (minNodes < 0)
            System.out.println(minNodes);
        else {
            int maxNodes = (int)Math.max(minNodes, t+1);
			TreeMap<Integer, ArrayList<Integer>> map = initializeMap(maxNodes);
			// create edges for excess tree edges
			createEdge(minNodes+1, maxNodes, 1, map);
            while (minNodes > 1) {
				//identify max possible cross / forward edges for minNodes-1 vertices
				//thereby identifying number of cross / forward edges that needs to be
				//created in this iteration
				int currMaxFwdOrCrossEdges = ((minNodes-1)*(minNodes-2))/2;
				int prevMaxFwdOrCrossEdges = ((minNodes-2)*(minNodes-3))/2;
				int currMaxBackEdges = ((minNodes-1)*minNodes)/2;
				int prevMaxBackEdges = currMaxFwdOrCrossEdges;
				
				int maxFwdOrCrossEdges = minNodes-2;
				int maxBackEdges = minNodes-1;

				int minCreateForwardEdge = (int) Math.max(f-prevMaxFwdOrCrossEdges, 0);
				int minCreateCrossEdge = (int)Math.min(c, maxFwdOrCrossEdges - minCreateForwardEdge);				
				int minCreateBackEdge = (int) Math.max(c + b - minCreateCrossEdge - prevMaxBackEdges, 0);
				
				int treeEdgeNode = 
				        (int) Math.min(minNodes - minCreateCrossEdge, 
				                       (int) Math.max(minCreateBackEdge, minCreateForwardEdge+1));
								
				//create the tree edge / forward edge depending 
				//on whether a fwd edge was already created
				createCrossEdges(minCreateCrossEdge, minNodes, treeEdgeNode, map);
				createForwardEdges(minCreateForwardEdge, treeEdgeNode, minNodes, map);
				createBackEdges(minCreateBackEdge, minNodes, treeEdgeNode, map);
    			createEdge(minNodes, minNodes, treeEdgeNode, map);

                //update counts remaining and current node for processing
				c -= minCreateCrossEdge;
				b -= minCreateBackEdge;
				f -= minCreateForwardEdge;
				--minNodes;
			}
			printMap(map);
			
        }
    }

    private static void createCrossEdges(int numEdges, 
	                                     int startNode, 
										 TreeMap<Integer, ArrayList<Integer>> tMap){
		createEdge(startNode-numEdges, startNode-1, startNode, tMap);		
	}	
	
    private static void createCrossEdges(int numEdges, 
	                                     int startNode, 
										 int treeNode,
										 TreeMap<Integer, ArrayList<Integer>> tMap){
		createEdge(treeNode+1, treeNode+numEdges, startNode, tMap);		
	}	
	
    private static void createBackEdges(int numEdges, 
	                                    int startNode, 
										int treeNode,
										TreeMap<Integer, ArrayList<Integer>> tMap){
		createEdge(treeNode-numEdges+1, treeNode, startNode, tMap);		
	}	
	
    private static void createForwardEdges(int numEdges, 
										   int treeNode,
										   int startNode, 
										   TreeMap<Integer, ArrayList<Integer>> tMap){
		for (int i=treeNode-numEdges; i<=treeNode-1; i++)
			createEdge(startNode, startNode, i, tMap);		
	}	
	
    //creates an edge from startNode to all the nodes (both inclusive) between from and to
	private static void createEdge(int from, 
	                               int to, 
								   int startNode, 
								   TreeMap<Integer, ArrayList<Integer>> tMap) {
		ArrayList<Integer> edges = tMap.get(startNode);
		for (int i=from; i<=to; i++) {
			edges.add(i);
		}
		edges = null;
	}
	
	private static TreeMap<Integer, ArrayList<Integer>> initializeMap(int size){
    	TreeMap<Integer, ArrayList<Integer>> newMap = new TreeMap<Integer, ArrayList<Integer>>();
		for (int i=1; i<=size; i++) {
			newMap.put(i, new ArrayList<Integer>());
		}
		return newMap;
	}
	
	private static void printMap(TreeMap<Integer, ArrayList<Integer>> treeMap) {
        System.out.println(treeMap.size());
		ArrayList<Integer> edges = null;
		for (Integer key: treeMap.keySet()) {
			edges = treeMap.get(key);
			Collections.sort(edges);
			System.out.print(edges.size());
			for (Integer edge: edges) {
				System.out.print(" " + edge);
			}
			System.out.println("");
		}
	}
    
    private static int getMinNodes(int te, int be, int fe, int ce) {
        // there should be at least 1 te (2 nodes) to have 1 be and
        // at least 2 te (3 nodes) for 1 fe and ce.
        // Also, the maximum number of be or  fe and ce grows with every new node
        // at this rate: n*(n-1)/2 and (n-1)*(n-2)/2  respectively
        
        if (te == 0 && be == 0 && fe == 0 && ce == 0)
            return 1;
        
        //There can be utmost n*(n-1) edged in a graph of n nodes.
        // every ce means conversion of 1 fe + 1 be to 1 ce.
        // since fe and ce grow at same rate, if all are ce, there won't be any fe but there still can be some be
        // be = (n-1)*n/2, ce and fe are (n-1)*(n-2)/2 
        int min_te4cefeRbe = (int)Math.max(1 + (int)Math.round(Math.sqrt(2*(ce+fe))), (int)Math.round(Math.sqrt(2*(be+ce))));        
        if (min_te4cefeRbe > te) return -1;
        return 1 + min_te4cefeRbe;
    }
}