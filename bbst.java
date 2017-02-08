import java.util.Optional;
import java.util.Scanner;
import java.util.*;
import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class bbst {	
      
    static Node root;      // root of the red black tree

    // Colors of the nodes defined as enum
    
    public enum Color {
        RED,
        BLACK
    }
    
    public static class TotalCount {
        public static int sum;
    }

    public static class Node {
        int ID;
        int count;
        Color color;
        Node left;
        Node right;
        Node parent;
        boolean nullLeaf;

    }
    
  
  // While inserting a new node if it has a parent then this method is called  
    
    private static Node createRed(Node parent, int ID, int count) {
        Node node = new Node();
        node.ID = ID;
        node.count = count;
        node.color = Color.RED;
        node.parent = parent;
        node.left = createNullLeaf(node);
        node.right = createNullLeaf(node);
        return node;
    }

    
    //While inserting a new node if it has no parent then this method is called
    
    private static Node createBlack(int ID, int count) {
        Node node = new Node();
        node.ID = ID;
        node.count = count;
        node.color = Color.BLACK;
        node.left = createNullLeaf(node);
        node.right = createNullLeaf(node);
        return node;
    }
    
// Creates null leaf nodes
    private static Node createNullLeaf(Node parent) {
        Node leaf = new Node();
        leaf.color = Color.BLACK;
        leaf.nullLeaf = true;
        leaf.parent = parent;
        return leaf;
    }


    // The method being called recursively during initializing red black tree from the input array
    
    
    private static Node createNode(int ID, int count , Node left , Node right) {
        Node node = new Node();
        node.ID = ID;
        node.count = count;
        if(left != null && right != null){
        	left.parent = node;
        	node.left = left;
        	right.parent = node;
        	node.right = right;
        	node.color = Color.BLACK;}
        else if (left == null && right != null){
        	node.left = createNullLeaf(node);
        	right.parent = node;
        	node.right = right;
        	node.color = Color.BLACK;
        	}
        else if (left != null && right == null){
        	node.right = createNullLeaf(node);
        	left.parent = node;
        	node.left = left;
        	node.color = Color.BLACK;
        	}
        else {
        	node.left = createNullLeaf(node);
        	node.right = createNullLeaf(node);
        	node.color = Color.RED;
        }

        return node;	
    }

    	// Initialize method of red black tree.
     
     
        // A method that constructs Balanced Red Black Tree from a sorted array 

        Node sortedArrayToRBT(int IDarr[], int countarr[], int start, int end) {
     
        	Node left = null;
        	Node right = null;
        	    	
            
            if (start > end) {
                return null;
            }
            
     
            /* Get the middle element and make it root */
            int mid = (start + end) / 2;  

            /* Recursively construct the left subtree and make it
             left child of root */
            if (start < mid){
            left = sortedArrayToRBT(IDarr, countarr, start, mid - 1 );
            }
            
            /* Recursively construct the right subtree and make it
             right child of root */
            if (mid < end){
            right = sortedArrayToRBT(IDarr, countarr, mid + 1, end );
            }
            
            Node middle = createNode(IDarr[mid], countarr[mid], left , right);
             
            return middle;
        }      
     
    
    /** 
     * Increase count method
     */
        
    public void increase (Node root, int ID, int m) { 	
    	if (search(root, ID) == true) {
    		increasecount(root, ID, m);		//calls the increasecount method if the given ID is present in the tree
    }
    	else {
    		insert(root, ID, m);     		// calls the insert method if the given ID is not present in the tree
    		System.out.println(m);
    	}      		   	   	
    }
    
    public void increasecount (Node root, int ID, int n) {
	if (root.ID == ID){    	    	
	root.count += n; 
	System.out.println(root.count);
	}
	
	//searches for the ID in the tree and recursively calls the increasecount method 
	
	else if (root.ID < ID){
	increasecount (root.right, ID, n);
	}
	else if (root.ID > ID){
	increasecount (root.left, ID, n);
	}
    }
    
    /** 
     * Reduce count method
     */
    
    public void reduce (Node root, int ID, int m) {
    	if (search(root, ID) == true) {
    		reducecount(root, ID, m);    		//calls the reducecount method if the given ID is present in the tree
    	}
    	else {
    		System.out.println(0);				//prints 0 if the given ID is not present 
    	}      		   	   	
    }
    
    public void reducecount (Node root, int ID, int n) {    	
    if (root.ID == ID){
    		if ((root.count)-n > 0){
    			root.count -= n;
    			System.out.println(root.count);		//prints the count of the ID after reduction if it is >0
    		}
    		else {
    			delete(root, ID);
    			System.out.println(0);  			//prints 0 if the count of the ID after reduction becomes <= 0
    			}
	}
    
  //searches for the ID in the tree and recursively calls the reducecount method 
    
	else if (root.ID < ID){
		reducecount (root.right, ID, n);
	}
	else if (root.ID > ID){
		reducecount (root.left, ID, n);
	}
    }
    
    /**
     * Print count
     */
    
    public void count (Node root, int ID){
    	if (search(root, ID) == true) {
    		printcount(root, ID);    		// calls the printcount method if the given ID is present in the tree
    	}
    	else {
    		System.out.println(0);			//prints 0 if the given ID is not there in the tree
    	}      		   	  
    }
    
    public void printcount(Node root, int ID){
    	if (root.ID == ID){
    		System.out.println(root.count);			//prints the count of the given ID 
	}
    	
    	//searches for the ID in the tree and recursively calls the printcount method 	
    	
	else if (root.ID < ID){
		printcount (root.right, ID);
	}
	else if (root.ID > ID){
		printcount (root.left, ID);
	}
    }
    
    
   /**
    * Search Method
    */

    public boolean search(Node root, int ID) {
        //search for the node.
    	Boolean match = false;
    	while (root.ID != ID)
    	{ 
    		if( root.left == null && root.right == null){
    			break;
    		}
    		else if ( root.left.ID == ID || root.right.ID == ID){
    			match = true;									//sets the isMatch value to true when the ID is found in the tree
    			break;
    		}
    		else if(root.ID < ID) {
	        	root = root.right;
	        } 
    		else {
	        	root = root.left;
	        }
	    }    	
    	return match;
    }
    
    /**
     * InRange search
     */  


   public void inrange(Node root, int ID1, int ID2) {
	 
        if (root.ID == 0) {
        	return;
        }
        /* Since the desired o/p is sorted, recurse for left subtree first
        If root.ID is greater than ID1, then only we can get o/p keys
        in left subtree */
       if (ID1 < root.ID) {
           inrange(root.left, ID1, ID2);
       }

       /* if root's ID lies in range, then sum root's ID */
       if (ID1 <= root.ID && ID2 >= root.ID) {           
           TotalCount.sum += root.count; 
       }

       /* If root.ID is smaller than ID2, then only we can get o/p keys
        in right subtree */
       if (ID2 > root.ID) {
           inrange(root.right, ID1, ID2);
       }
    }

   
    
    /**
     * Next Method
     */
    

    public Node next(Node root, int ID) {        
    	int nCount = 0;
    	int nID = 0;
    	
    	while(true){
    		if( ID >= root.ID && root.ID != 0){			//search in the right subtree rooted at root for the next node
    			root = root.right;
    		}
    		else{
    			if( root.ID == 0){
    				break;
    			}
    			nCount = root.count;
    			nID = root.ID;
    			root = root.left;
    		}
    	}
    	System.out.println( nID + " " + nCount); 		//prints the ID and count of the next node
    	return root.parent;
    }
    
    /**
     * Previous method
     */
    


    public int previous(Node root, int ID) {
        //search for the node.
    	Boolean isFound = false;
    	int pCount = 0;
    	int pID = 0;
    	
    	while(!isFound){
    		if( ID <= root.ID && root.ID != 0){			//search in the left subtree rooted at root for the previous node
    			root = root.left;
    		}
    		else{
    			if( root.ID == 0){
    				break;
    			}
    			pCount = root.count;
    			pID = root.ID;
    			root = root.right;
    		}
    	}
    	System.out.println(pID + " " + pCount);			//prints the ID and count of the previous node
    	return pCount;
    }
    
  
    //method to right rotate the subtree rooted at root 
    
    private void rightRotate(Node root, boolean switchColor) {
        Node parent = root.parent;
        root.parent = parent.parent;
        if(parent.parent != null) {
            if(parent.parent.right == parent) {
                parent.parent.right = root;
            } else {
                parent.parent.left = root;
            }
        }
        Node right = root.right;
        root.right = parent;
        parent.parent = root;
        parent.left = right;
        if(right != null) {
            right.parent = parent;
        }
        if(switchColor) {
            root.color = Color.BLACK;
            parent.color = Color.RED;
        }
    }
    

  //method to left rotate the subtree rooted at root 
    
    
    private void leftRotate(Node root, boolean switchColor) {
        Node parent = root.parent;
        root.parent = parent.parent;
        if(parent.parent != null) {
            if(parent.parent.right == parent) {
                parent.parent.right = root;
            } else {
                parent.parent.left = root;
            }
        }
        Node left = root.left;
        root.left = parent;
        parent.parent = root;
        parent.right = left;
        if(left != null) {
            left.parent = parent;
        }
        if(switchColor) {
            root.color = Color.BLACK;
            parent.color = Color.RED;
        }
    }

    //Finds the sibling of a given node
    //This method is used when deleting a node
    
    private Optional<Node> findSibling(Node root) {
        Node parent = root.parent;
        if(isLeftChild(root)) {
            return Optional.ofNullable(parent.right.nullLeaf ? null : parent.right);
        } else {
            return Optional.ofNullable(parent.left.nullLeaf ? null : parent.left);
        }
    }

    
    //Finds whether the given node is a left child of its parent
    
    private boolean isLeftChild(Node root) {
        Node parent = root.parent;
        if(parent.left == root) {
            return true;
        } else {
            return false;
        }
    }
    
    
    /**
     * Main insert method of red black tree.
    */
       
   public Node insert(Node root, int ID, int count) {
       return insert(null, root, ID, count);
   }


    private Node insert(Node parent, Node root, int ID, int count) {
        if(root  == null || root.nullLeaf) {
            //if parent is not null means tree is not empty. Therefore create a red node
            
            if(parent != null) {
                return createRed(parent, ID, count);
            } 
          //Create a black root node if tree is empty
            else { 
                return createBlack(ID, count);			
            }
        }


        //if we go on left side then wentLeft will be true and false if we go on the right side
        
        boolean wentLeft;
        if(root.ID > ID) {
            Node left = insert(root, root.left, ID, count);
            //if left becomes root parent means rotation
            //happened at lower level. So just return left
            //so that nodes at upper level can set their
            //child correctly
            if(left == root.parent) {
                return left;
            }
            
            root.left = left;		//set the left returned to be left child of root node            
            wentLeft = true;		//set wentLeft to be true
        } 
        else {
            Node right = insert(root, root.right, ID, count);
            //if right becomes root parent means rotation
            //happened at lower level. So just return right
            //so that nodes at upper level can set their
            //child correctly
            if(right == root.parent) {
                return right;
            }
            
            root.right = right;			//set the right returned to be right child of root node            
            wentLeft = false;			//set wentLeft to be false
        }

        //if we went on the left side
        
        if(wentLeft) {
        	// check for red parent with red child
            if(root.color == Color.RED && root.left.color == Color.RED) {
                //Returns the other child node of root's parent(sibling of root). It is using optional because
                //node could be empty
                Optional<Node> sibling = findSibling(root);
                //if sibling is empty or black in color
                if(!sibling.isPresent() || sibling.get().color == Color.BLACK) {
                    //check if root is left child of its parent
                    if(isLeftChild(root)) {
                        //in this case we need to right rotate
                        rightRotate(root, true);
                    } else {
                        //else we need to do one right rotate without change in color                        
                        //followed by left rotate with color change
                        //when right rotation is done root becomes right child of its left child.
                        //So root = root.parent is done because its left child before rotation
                        //is new root of this subtree.
                    	
                        rightRotate(root.left, false);                        
                        root = root.parent;                        
                        leftRotate(root, true);
                    }

                } else {
                    //we have sibling color as RED. So change color of root
                    //and its sibling to Black. And then change color of their
                    //parent to red if their parent is not a root.
                    root.color = Color.BLACK;
                    sibling.get().color = Color.BLACK;
                    //if parent's parent is not null means parent is not root.
                    //so change its color to RED.
                    if(root.parent.parent != null) {
                        root.parent.color = Color.RED;
                    }
                }
            }
        } 
        
      //if we went on the right side
        
        else {
        	// check for red parent with red child
            if(root.color == Color.RED && root.right.color == Color.RED) {
                //Returns the other child node of root's parent(sibling of root). It is using optional because
                //node could be empty
                Optional<Node> sibling = findSibling(root);
              //if sibling is empty or black in color
                if(!sibling.isPresent() || sibling.get().color == Color.BLACK) {
                	//check if root is right child of its parent
                    if(!isLeftChild(root)) {
                    	//in this case we need to left rotate
                        leftRotate(root, true);
                    } else {
                        //else we need to do one left rotate without change in color 
                    	//followed by right rotate with color change                   
                        //After left rotation root becomes left child of its right child
                        //So root = root.parent is done because its right child before rotation
                        //is new root of this subtree.
                    	
                        leftRotate(root.right, false);
                        root = root.parent;
                        rightRotate(root, true);
                    }
                } else {
                    //we have sibling color as RED. So change color of root
                    //and its sibling to Black. And then change color of their
                    //parent to red if their parent is not a root.
                    root.color = Color.BLACK;
                    sibling.get().color = Color.BLACK;
                    //if parent's parent is not null means parent is not root.
                    //so change its color to RED.
                    if(root.parent.parent != null) {                    	
                        root.parent.color = Color.RED;
                    }
                }
            }
        }
        return root;
    }
    
    /**
     * Main delete method of red black tree.
     */
    public Node delete(Node root, int ID) {
        AtomicReference<Node> rootRef = new AtomicReference<>();
        delete(root, ID, rootRef);
        if(rootRef.get() == null) {
            return root;
        } else {
            return rootRef.get();
        }
    }
    


    private void delete(Node root, int ID, AtomicReference<Node> rootRef) {
        if(root == null || root.nullLeaf) {
            return;			//tree is empty
        }
        if(root.ID == ID) {
            //if node to be deleted has 0 or 1 null children        
            if(root.right.nullLeaf || root.left.nullLeaf) {
                Node child = root.right.nullLeaf ? root.left : root.right;
                //replace node with either one not null child if it exists or null child.
                replaceNode(root, child, rootRef);
                //if the node to be deleted is BLACK. See if it has one red child.
                if(root.color == Color.BLACK) {
                    //if it has one red child then change color of that child to be Black.
                    if(child.color == Color.RED) {
                        child.color = Color.BLACK;
                    } else {
                        //otherwise we have double black situation i.e. the node to be deleted is black with black children
                        deleteNode(child, rootRef);
                    }
                }
            } else {
                //otherwise look for the inorder successor in right subtree.
                //replace inorder successor ID at root ID.
                //then delete inorder successor which should have 0 or 1 not null child.
                Node next = findSmallest(root.right);
                root.ID = next.ID;
                root.count = next.count;
                delete(root.right, next.ID, rootRef);
            }
        }
        //search for the node to be deleted.
        if(root.ID < ID) {
            delete(root.right, ID, rootRef);
        } else {
            delete(root.left, ID, rootRef);
        }
    }

    

    
    
    //method to find the smallest node of the subtree rooted at root
    //used in delete method
    
    private Node findSmallest(Node root) {
        Node prev = null;
        while(root != null && !root.nullLeaf) {
            prev = root;
            root = root.left;
        }
        return prev != null ? prev : root;
    }

//Delete method for the double black situation
    
   private void deleteNode(Node node, AtomicReference<Node> rootRef) {
    	Node siblingNode = findSibling(node).get();
        // If double black node becomes root then turn it into a single black node 
    	// This reduces black height by one        
        if(node.parent == null) {
            rootRef.set(node);
            return;
        }
        
        //If sibling is red and parent and sibling's children are black then rotate it
        //so that sibling becomes black.
        else if(siblingNode.color == Color.RED) {
            if(isLeftChild(siblingNode)) {
                rightRotate(siblingNode, true);
            } else {
                leftRotate(siblingNode, true);
            }
            if(siblingNode.parent == null) {
                rootRef.set(siblingNode);
            }
        }
        
        //If sibling, sibling's children and parent are all black then turn sibling into red.
        // This reduces black node for both the paths from parent.
        else if(node.parent.color == Color.BLACK && siblingNode.color == Color.BLACK && siblingNode.left.color == Color.BLACK
                && siblingNode.right.color == Color.BLACK) {
            siblingNode.color = Color.RED;
            deleteNode(node.parent, rootRef);
        }	
        
        //If sibling is black, parent is red and sibling's children are black then swap color b/w sibling
        //and parent. This increases one black node on double black node path but does not affect black node count on
        //sibling path.
        
		else if(node.parent.color == Color.RED && siblingNode.color == Color.BLACK && siblingNode.left.color == Color.BLACK
        && siblingNode.right.color == Color.BLACK) {
            siblingNode.color = Color.RED;
            node.parent.color = Color.BLACK;
            return;
        } 
        
        // If sibling is black, check if double black node is left or right child of its parent and sibling's right or left
        // child is black and other child is red
        // then do a right rotation or left rotation at siblings left or right child respectively and swap colors.
        
		else if(siblingNode.color == Color.BLACK){
		            if (isLeftChild(node) && siblingNode.right.color == Color.BLACK && siblingNode.left.color == Color.RED) {
                rightRotate(siblingNode.left, true);
            } else if (!isLeftChild(node) && siblingNode.left.color == Color.BLACK && siblingNode.right.color == Color.RED) {
                leftRotate(siblingNode.right, true);
            }
        }
        
        // If sibling is black, double black node is left or right child of its parent, sibling left or right child is black 
        // and other child is red, sibling takes its parent color, parent color becomes black, sibling's right or left child becomes black 
        // then perform left or right rotation at sibling without any further change in color. This removes double black
        
        
        else {
		        siblingNode.color = siblingNode.parent.color;
				siblingNode.parent.color = Color.BLACK;
				if(isLeftChild(node)) {
					siblingNode.right.color = Color.BLACK;
					leftRotate(siblingNode, false);
				} else {
					siblingNode.left.color = Color.BLACK;
					rightRotate(siblingNode, false);
				}
				if(siblingNode.parent == null) {
					rootRef.set(siblingNode);
				}
		}
    }

  //This method replaces root node with the node child 
   
    private void replaceNode(Node root, Node child, AtomicReference<Node> rootRef) {
        child.parent = root.parent;
        if(root.parent == null) {
            rootRef.set(child);
        }
        else {
            if(isLeftChild(root)) {
                root.parent.left = child;
            } else {
                root.parent.right = child;
            }
        }
    }

  
    

    public static void main(String args[]) throws IOException{
        Node root = null;
        bbst tree = new bbst();
        String path = null; 
        
        
        path = args[0];        
        Scanner scanner = new Scanner(new File(path));     //reads input from the text file    
        int num = scanner.nextInt();
        int size = 2*num;
        int temp[] = new int[size];        
        int countarr[] = new int[num];
        int IDarr[] = new int[num];
        
        for(int i=0; i<size; i++)
        {
          temp[i] = scanner.nextInt();		              //The input from text file is stored in the temporary array
        }
		  scanner.close();
		
		  int k = 0;
		  for (int j = 0; j < num; j++ ) {
		  	IDarr[j] = temp[k];							//Initializes ID array from the temp array
		  	countarr[j] = temp[k+1];					//Initializes Count array from the temp array
		  	k = k + 2;
		  }
		

			
        root = tree.sortedArrayToRBT(IDarr, countarr, 0, num - 1);	//calls the initialization method
		

		
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in)); //reads commands from command prompt
        String line; 

        while ((line = stdin.readLine()) != null && line.length()!= 0) {  
        	String[] input = line.split(" ");
        	
        	//goes to the appropriate method according to the command entered
        	
        	if (line.contains("increase")){	
        		int arg1 = Integer.parseInt(input[1]);
        		int arg2 = Integer.parseInt(input[2]);                
                tree.increase(root, arg1, arg2); 
        	}

        	if (line.contains("reduce")){   
        		int arg1 = Integer.parseInt(input[1]);
    			int arg2 = Integer.parseInt(input[2]);                
                tree.reduce(root, arg1, arg2);                
        	}
        	
        	if (line.contains("count")){
        		int arg1 = Integer.parseInt(input[1]);                        
                tree.count(root, arg1);                        		
        	}

        	if (line.contains("inrange")){ 
        		TotalCount.sum = 0;
        		int arg1 = Integer.parseInt(input[1]);
        		int arg2 = Integer.parseInt(input[2]);                      
                tree.inrange(root, arg1 , arg2);
                System.out.println(TotalCount.sum);        		
        	}

        	if (line.contains("next")){  
        		int arg1 = Integer.parseInt(input[1]);                      
                tree.next(root, arg1);        		
        	}

        	if (line.contains("previous")){  
        		int arg1 = Integer.parseInt(input[1]);                
                tree.previous(root, arg1);         		
        	}

        	if (line.contains("quit")){
        		System.exit(0);				//Ends the program 
        	}


		}	

    }
}
