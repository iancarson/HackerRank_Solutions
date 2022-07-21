/* Hidden stub code will pass a root argument to the function below. Complete the function to solve the challenge. Hint: you may want to write one or more helper functions.  
The Node class is defined as follows:
     class Node {
        int data;
        Node left;
        Node right;
     }
*/

    boolean checkBST(Node root) {
        
        if (root.left == null && root.right == null) return true;
        
        return isBinarySearchTree(root.left, null, root.data)
                && isBinarySearchTree(root.right, root.data, null);
    }

    boolean isBinarySearchTree(Node innerNode, Integer lowerBound, Integer upperBound) {
        if (innerNode == null) return true;
        
        if (!respectsLowerBound(innerNode, lowerBound) 
            || !respectsUpperBound(innerNode, upperBound)) return false;

        Integer updatedLowerBound = getUpdatedLowerBound(innerNode.data, lowerBound);
        Integer updatedUpperBound = getUpdatedUpperBound(innerNode.data, upperBound);
        
        return isBinarySearchTree(innerNode.left, lowerBound, updatedUpperBound) 
            && isBinarySearchTree(innerNode.right, updatedLowerBound, upperBound);
    }

    boolean respectsLowerBound(Node node, Integer bound) {
        return bound == null || node.data > bound;
    }


    boolean respectsUpperBound(Node node, Integer bound) {
        return bound == null || node.data < bound;
    }

    Integer getUpdatedLowerBound(int data, Integer lowerBound) {
        if (lowerBound == null) 
            return data;
        if (lowerBound >= data) 
            return lowerBound;
        return data;
    }

    Integer getUpdatedUpperBound(int data, Integer upperBound) {
        if (upperBound == null) 
            return data;
        if (upperBound <= data) 
            return upperBound;
        return data;
    }
