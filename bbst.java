import java.util.Optional;
import java.util.Scanner;
import java.util.*;
import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class bbst {

    static Node root;
    public static boolean colorFlag = false;

    public enum Color {
        RED,
        BLACK
    }
    
    public static class TotalcountItems {
        public static int sum;
    }

    public static class Node {
        Color nodeColor;
        Node leftNode;
        Node parentNode;
        Node rightNode;
        boolean isNullLeaf;
        int data;
        int countItems;
    }

    private static Node createNode(int data, int count, Node left , Node right) {
        Node node = new Node();
        node.data = data;
        node.countItems = count;
        if(left != null){
        	left.parentNode = node;
        	node.leftNode = left;
        	node.nodeColor = Color.BLACK;
        	}
	    else{
	    	node.leftNode = createNullLeafNode(node);
	    	node.nodeColor = Color.RED;
	    	}
	    if(right != null){
	    	right.parentNode = node;
	    	node.rightNode = right;
	    	node.nodeColor = Color.BLACK;
	    	}
	    else{
	    	node.rightNode = createNullLeafNode(node);
	        node.nodeColor = Color.RED;
	    	}
        return node;    
    }

    private static Node createBlackNode(int data, int countItems) {
        Node node = new Node();
        node.data = data;
        node.countItems = countItems;
        node.nodeColor = Color.BLACK;
        node.leftNode = createNullLeafNode(node);
        node.rightNode = createNullLeafNode(node);
        return node;
    }

    private static Node createRedNode(Node parentNode, int data, int countItems) {
        Node node = new Node();
        node.data = data;
        node.countItems = countItems;
        node.nodeColor = Color.RED;
        node.parentNode = parentNode;
        node.leftNode = createNullLeafNode(node);
        node.rightNode = createNullLeafNode(node);
        return node;
    }

    private static Node createNullLeafNode(Node parentNode) {
        Node leaf = new Node();
        leaf.nodeColor = Color.BLACK;
        leaf.isNullLeaf = true;
        leaf.parentNode = parentNode;
        return leaf;
    }


    // construct Red Black Tree from a sorted array 
    public  Node sortedArray(int IDarr[], int countarr[], int start, int end) {
 
    	Node left = null;
    	Node right = null;
    
        if (start > end) {
            return null;
        }
 
        //get middle element
        int mid = (start + end) / 2;      
    
        //call left root
        if (start < mid){
        left = sortedArray(IDarr, countarr, start, mid - 1);
        }
        
        //call right root
        if (mid < end){
        right = sortedArray(IDarr, countarr, mid + 1, end);
        }
        
        Node middle = createNode(IDarr[mid], countarr[mid], left, right);
         
        return middle;
    }
    

    //Inserting new element in RedBlack tree 
    public Node insertElement(Node root, int data, int countItems) {
        return insertNode(null, root, data, countItems);
    }

    //Deleting element from RedBlack tree
    public Node deleteElement(Node root, int data) {
        AtomicReference<Node> rootReference = new AtomicReference<>();
        deleteNode(root, data, rootReference);
        if(rootReference.get() == null) {
            return root;
        } else {
            return rootReference.get();
        }
    }
    
    //Increasing the count
    public void increaseCount (Node root, int data, int m) {     
        if (searchElement(root, data) == true) {
            increasecountId(root, data, m);
        }
        else {
            insertElement(root, data, m); 
            System.out.println(m);
        }                   
    }
    public void increasecountId (Node root, int data, int n) {
        if (root.data == data){             
            root.countItems += n; 
            System.out.println(root.countItems);
        }
        else if (root.data < data){
            increasecountId (root.rightNode, data, n);
        }
        else if (root.data > data){
            increasecountId (root.leftNode, data, n);
        }
    }
    
    
    //Reducing the count     		   	   	
    public void reduceCount (Node root, int data, int m) {
        if (searchElement(root, data) == true) {
            reducecountId(root, data, m);         
        }
        else {
            System.out.println(0);
        }                   
    }
    public void reducecountId (Node root, int data, int n) {      
        if (root.data == data){
            if ((root.countItems)-n > 0){
                root.countItems -= n;
                System.out.println( root.countItems);
            }
            else {
                deleteElement(root, data);
                System.out.println(0);
                }
        }
        else if (root.data < data){
            reducecountId (root.rightNode, data, n);
        }
        else if (root.data > data){
            reducecountId (root.leftNode, data, n);
        }
    }

    
    //Search for the node
    public boolean searchElement(Node root, int data) {
        AtomicReference<Node> rootReference = new AtomicReference<>();
        Boolean isMatch = false;
        while (root.data != data)
        { 
            if( root.leftNode == null && root.rightNode == null){
                break;
            }
            else if ( root.leftNode.data == data || root.rightNode.data == data){
                isMatch = true;
                break;
            }
            else if(root.data < data) {
                root = root.rightNode;
            } 
            else {
                root = root.leftNode;
            }
        }       
        return isMatch;
    }
    
    //Next method
    public Node nextMethod(Node root, int data) {
    	AtomicReference<Node> rootReference = new AtomicReference<>();
    	//search for the node.
        int found = 0;
        int foundID = 0;
        
        while(true){
            if( data >= root.data && root.data != 0){
                root = root.rightNode;
            }
            else{
                if( root.data == 0){
                    break;
                }
                found = root.countItems;
                foundID = root.data;
                root = root.leftNode;
            }
        }
        System.out.println(foundID + " " + found);
        return root.parentNode;
    }
    
    //Previous method
    public int previousMethod(Node root, int data) {
    	AtomicReference<Node> rootReference = new AtomicReference<>();
    	//search for the node.
        Boolean isFound = false;
        int found = 0;
        int foundID = 0;
        
        while(!isFound){
            if( data <= root.data && root.data != 0){
                root = root.leftNode;
            }
            else{
                if( root.data == 0){
                    break;
                }
                found = root.countItems;
                foundID = root.data;
                root = root.rightNode;
            }
        }
        System.out.println(foundID + " " + found);
        return found;
    }

    //Printing count amount
    public void countItems (Node root, int data){
        if (searchElement(root, data) == true) {
            printcount(root, data);  
        }
        else{
            System.out.println(0);
        }                 
    }
    public void printcount(Node root, int data){
        if (root.data == data){
            System.out.println( root.countItems);
        }
        else if (root.data < data){
            printcount (root.rightNode, data);
        }
        else if (root.data > data){
            printcount (root.leftNode, data);
        }
    }



    //Searching element in Range
    public void inRangeSearch(Node root, int data1 , int data2) {
    	AtomicReference<Node> rootReference = new AtomicReference<>();
    	if (root.data == 0) {
            return;
        }
        if (data1 < root.data) {
           inRangeSearch(root.leftNode, data1, data2);
        }
        // if root's data lies in range, then prints root's data 
        if (data1 <= root.data && data2 >= root.data) {           
           TotalcountItems.sum += root.countItems; 
        }
       //If root->data is smaller than k2, then only we can get o/p keys in right subtree
        if (data2 > root.data) {
           inRangeSearch(root.rightNode, data1, data2);
       }
    }
    
    //Print method for red black tree
    public void printTree(Node root) {
        redBlackTreePrint(root, 0);
    }
    
    //function for right rotation
    private void rotateRight(Node root, boolean colorChange) {
        Node parentNode = root.parentNode;
        root.parentNode = parentNode.parentNode;
        if(parentNode.parentNode != null) {
            if(parentNode.parentNode.rightNode == parentNode) {
                parentNode.parentNode.rightNode = root;
            } else {
                parentNode.parentNode.leftNode = root;
            }
        }
        Node rightNode = root.rightNode;
        root.rightNode = parentNode;
        parentNode.parentNode = root;
        parentNode.leftNode = rightNode;
        if(rightNode != null) {
            rightNode.parentNode = parentNode;
        }
        if(colorChange) {
            root.nodeColor = Color.BLACK;
            parentNode.nodeColor = Color.RED;
        }
    }
    //function for left rotation
    private void rotateLeft(Node root, boolean colorChange) {
        Node parentNode = root.parentNode;
        root.parentNode = parentNode.parentNode;
        if(parentNode.parentNode != null) {
            if(parentNode.parentNode.rightNode == parentNode) {
                parentNode.parentNode.rightNode = root;
            } else {
                parentNode.parentNode.leftNode = root;
            }
        }
        Node leftNode = root.leftNode;
        root.leftNode = parentNode;
        parentNode.parentNode = root;
        parentNode.rightNode = leftNode;
        if(leftNode != null) {
            leftNode.parentNode = parentNode;
        }
        if(colorChange) {
            root.nodeColor = Color.BLACK;
            parentNode.nodeColor = Color.RED;
        }
    }
    //function for Sibling search
    private Optional<Node> findingSiblingNode(Node root) {
        Node parentNode = root.parentNode;
        if(leftChild(root)) {
            return Optional.ofNullable(parentNode.rightNode.isNullLeaf ? null : parentNode.rightNode);
        } else {
            return Optional.ofNullable(parentNode.leftNode.isNullLeaf ? null : parentNode.leftNode);
        }
    }

    private boolean leftChild(Node root) {
        Node parentNode = root.parentNode;
        if(parentNode.leftNode == root) {
            return true;
        } else {
            return false;
        }
    }


    //function to insert node
    private Node insertNode(Node parentNode, Node root, int data, int countItems){
        if(root  == null || root.isNullLeaf) {
            //create a leaf node if parent is not empty
            if(parentNode != null) {
                return createRedNode(parentNode, data, countItems);
            } else { //otherwise create a black root node if tree is empty
                return createBlackNode(data, countItems);
            }
        }
        //duplicate datas cannot be inserted
        if(root.data == data) {
            throw new IllegalArgumentException("Duplicate data " + data);
        }

        //On going left side then isLeft will be true, else false
        boolean isLeft = false;
        if(root.data > data) {
            Node leftNode = insertNode(root, root.leftNode, data, countItems);
            //left becomes root parent means rotation happened at lower level. So just return left.
            if(leftNode == root.parentNode) {
                return leftNode;
            }
            //set the left child returned to be left of root node & set isLeft to be true.
            root.leftNode = leftNode;
            isLeft = true;
        } 
        else {
            Node rightNode = insertNode(root, root.rightNode, data, countItems);
            //if right becomes root parent means rotation happened at lower level. So just return right.
            if(rightNode == root.parentNode) {
                return rightNode;
            }
            //set the right child returned to be right of root node & isLeft to be false.
            root.rightNode = rightNode;
            isLeft = false;
        }

        Node rootnext = insertPart(parentNode, root, data, countItems, isLeft);
        return rootnext;
    }
    private Node insertPart(Node parentNode, Node root, int data, int countItems, boolean isLeft){

        if(isLeft) {
            //if we went to left side check to see Red-Red conflict between root and its left child
            if(root.nodeColor == Color.RED && root.leftNode.nodeColor == Color.RED) {

                Optional<Node> sibling = findingSiblingNode(root);
                //if sibling is empty or of BLACK color
                if(!sibling.isPresent() || sibling.get().nodeColor == Color.BLACK) {
                    if(leftChild(root)) {
                        //this creates left left situation. So doing one right rotate
                        rotateRight(root, true);

                    } else {
                        //this creates left-right situation so do one right rotate followed by left rotate
                        rotateRight(root.leftNode, false);
                        //after rotation root will be root's parent
                        root = root.parentNode;
                        //doing left rotate with changing of color
                        rotateLeft(root, true);
                    }

                } else {
                    //sibling color as RED. So changing color of root and its sibling to Black. And then changing color of their
                    //parent to red if their parent is not a root.
                    root.nodeColor = Color.BLACK;
                    sibling.get().nodeColor = Color.BLACK;
                    //if parent's parent is not null means parent is not root. So its color is changed to RED
                    
                    if(root.parentNode.parentNode != null) {
                        root.parentNode.nodeColor = Color.RED;
                    }
                }
            }
        } 
        else {
            //If isleft is false, mirror case opposite of previous function
            if(root.nodeColor == Color.RED && root.rightNode.nodeColor == Color.RED) {
                Optional<Node> sibling = findingSiblingNode(root);
                if(!sibling.isPresent() || sibling.get().nodeColor == Color.BLACK) {
                    if(!leftChild(root)) {
                        rotateLeft(root, true);
                    } else {
                        rotateLeft(root.rightNode, false);
                        root = root.parentNode;
                        rotateRight(root, true);
                    }
                } 
                else {
                    root.nodeColor = Color.BLACK;
                    sibling.get().nodeColor = Color.BLACK;
                    if(root.parentNode.parentNode != null) {
                        root.parentNode.nodeColor = Color.RED;
                    }
                }
            }
        }
        return root;
    }




    //function to delete node
    private void deleteNode(Node root, int data, AtomicReference<Node> rootReference) {
        if(root == null || root.isNullLeaf) {
            return;
        }
        if(root.data == data) {
            if(root.rightNode.isNullLeaf || root.leftNode.isNullLeaf) {
                deleteOneChild(root, rootReference);
            } else {
                Node inorderSuccessor = null;
                while(root.rightNode != null && !root.rightNode.isNullLeaf) {
                	inorderSuccessor = root.rightNode;
                    root.rightNode = root.rightNode.leftNode;
                }
                if (inorderSuccessor != null)
                	inorderSuccessor = inorderSuccessor;
                else
                	inorderSuccessor = root.rightNode;
                
                root.data = inorderSuccessor.data;
                root.countItems = inorderSuccessor.countItems;
                deleteNode(root.rightNode, inorderSuccessor.data, rootReference);
            }
        }
        //searching the node to be deleted.
        if(root.data < data) {
            deleteNode(root.rightNode, data, rootReference);
        } else {
            deleteNode(root.leftNode, data, rootReference);
        }
    }

    //Assuming that node to be deleted has either 0 or 1 non leaf child
    private void deleteOneChild(Node nodeToBeDelete, AtomicReference<Node> rootReference) {
        Node child = nodeToBeDelete.rightNode.isNullLeaf ? nodeToBeDelete.leftNode : nodeToBeDelete.rightNode;
        //replace node with either one not null child if it exists or null child.
        child.parentNode = nodeToBeDelete.parentNode;
        if(nodeToBeDelete.parentNode == null) {
            rootReference.set(child);
        }
        else {
            if(leftChild(nodeToBeDelete)) {
            	nodeToBeDelete.parentNode.leftNode = child;
            } else {
            	nodeToBeDelete.parentNode.rightNode = child;
            }
        }
        //if the node to be deleted is BLACK. See if it has one red child.
        if(nodeToBeDelete.nodeColor == Color.BLACK) {
            //if it has one red child then change color of that child to be Black.
            if(child.nodeColor == Color.RED) {
                child.nodeColor = Color.BLACK;
            } else {
                //otherwise we have double black situation.
                deleteCase(child, rootReference);
            }
        }
    }

    /*********************************************************************************************************************/

    //DeleteCases all in one
    private void deleteCase(Node doubleBlackNode, AtomicReference<Node> rootReference) {
        Node siblingNode = findingSiblingNode(doubleBlackNode).get();

        //deleteCase 1
        if(doubleBlackNode.parentNode == null) {
            rootReference.set(doubleBlackNode);
            return;
        }

        //deleteCase 2
        if(siblingNode.nodeColor == Color.RED) {
            if(leftChild(siblingNode)) {
                rotateRight(siblingNode, true);
            } else {
                rotateLeft(siblingNode, true);
            }
            if(siblingNode.parentNode == null) {
                rootReference.set(siblingNode);
            }
        }

        //deleteCase 3
        if(doubleBlackNode.parentNode.nodeColor == Color.BLACK && siblingNode.nodeColor == Color.BLACK && siblingNode.leftNode.nodeColor == Color.BLACK
                && siblingNode.rightNode.nodeColor == Color.BLACK) {
            siblingNode.nodeColor = Color.RED;
            if(doubleBlackNode.parentNode == null) {
            rootReference.set(doubleBlackNode);
            return;
        }
        } else {

        // deleteCase 4
            if(doubleBlackNode.parentNode.nodeColor == Color.RED && siblingNode.nodeColor == Color.BLACK && siblingNode.leftNode.nodeColor == Color.BLACK
        && siblingNode.rightNode.nodeColor == Color.BLACK) {
            siblingNode.nodeColor = Color.RED;
            doubleBlackNode.parentNode.nodeColor = Color.BLACK;
            return;
        } else {

        //deleteCase 5
            if(siblingNode.nodeColor == Color.BLACK) {
            if (leftChild(doubleBlackNode) && siblingNode.rightNode.nodeColor == Color.BLACK && siblingNode.leftNode.nodeColor == Color.RED) {
                rotateRight(siblingNode.leftNode, true);
            } else if (!leftChild(doubleBlackNode) && siblingNode.leftNode.nodeColor == Color.BLACK && siblingNode.rightNode.nodeColor == Color.RED) {
                rotateLeft(siblingNode.rightNode, true);
            }
        }

        //deleteCase 6
        siblingNode.nodeColor = siblingNode.parentNode.nodeColor;
        siblingNode.parentNode.nodeColor = Color.BLACK;
        if(leftChild(doubleBlackNode)) {
            siblingNode.rightNode.nodeColor = Color.BLACK;
            rotateLeft(siblingNode, false);
        } else {
            siblingNode.leftNode.nodeColor = Color.BLACK;
            rotateRight(siblingNode, false);
        }
        if(siblingNode.parentNode == null) {
            rootReference.set(siblingNode);
        }
        }
        }
    }

    /*********************************************************************************************************************/
    // Displaying the tree
    private void redBlackTreePrint(Node root, int space) {
        if(root == null || root.isNullLeaf) {
            return;
        }
        redBlackTreePrint(root.rightNode, space + 5);
        for(int i=0; i < space; i++) {
            System.out.print(" ");
        }
        System.out.println("(" + root.data + "," + root.countItems + "," + (root.nodeColor == Color.BLACK ? "Black" : "Red") + ")");
        redBlackTreePrint(root.leftNode, space + 5);
    }

    /*********************************************************************************************************************/


    public static void main(String args[]) throws IOException{
        Node root = null;
        bbst redBlackTree = new bbst();
        String pathoffile = null;
        
        pathoffile = args[0];
        Scanner scannerfile = new Scanner(new File(pathoffile ));        
        int numberOfIDs = scannerfile.nextInt();   
        int countarray[] = new int[numberOfIDs];
        int IDArray[] = new int[numberOfIDs];
		
		for (int j = 0; j < numberOfIDs; j++ ) {
			IDArray[j] = scannerfile.nextInt();
			countarray[j] = scannerfile.nextInt();
		}
		scannerfile.close();
			
        root = redBlackTree.sortedArray(IDArray, countarray, 0, numberOfIDs - 1);	
		
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in)); 
        String line; 

        while ((line = stdin.readLine()) != null && line.length()!= 0) { 
        	String[] input = line.split(" ");         
            String[] first = line.split(" ", 2);
            String a = first[0]; 

        switch(a){

        case "increase": int arg1 = Integer.parseInt(input[1]);
                         int arg2 = Integer.parseInt(input[2]);
                         redBlackTree.increaseCount(root, arg1, arg2); 
                         break;

        case "reduce":   arg1 = Integer.parseInt(input[1]);
                         arg2 = Integer.parseInt(input[2]);
                         redBlackTree.reduceCount(root, arg1, arg2);
                         break;

        case "count":    arg1 = Integer.parseInt(input[1]);       
                         redBlackTree.countItems(root, arg1); 
                         break;

        case "inrange":  TotalcountItems.sum = 0;
        				 arg1 = Integer.parseInt(input[1]);
                         arg2 = Integer.parseInt(input[2]);       
                         redBlackTree.inRangeSearch(root, arg1 , arg2);
                         System.out.println( TotalcountItems.sum);
                         break;

        case "next":     arg1 = Integer.parseInt(input[1]);       
                         redBlackTree.nextMethod(root, arg1);
                         break;

        case "previous": arg1 = Integer.parseInt(input[1]);
                         redBlackTree.previousMethod(root, arg1); 
                         break;
                         
        case "display":  redBlackTree.redBlackTreePrint(root, 0);

        case "quit":     System.exit(0);
                         break;

        default:         System.out.println("Not supported");
                         break;

    }

/*************************************************************************************************************
*************************************************************************************************************************/



		}	

    }
}