package exampleCode;

/**
 * Example of the minmax algorithm written with the use of 
 * the recursive method
 * @author lukas kurasinski
 */

public class MinMax {

	public static void main(String[] args) {
		Node root = createNodeTree();
		int depth = 0;
		int result = minMax(root, depth, true);
		System.out.println("Optimal maximizing value: " + result);
	}

	private static Node createNodeTree() {
		//create nodes
		Node root = new Node(3, 2);
		Node node1 = new Node(5, 2);
		Node node2 = new Node(2, 0);
		Node node3 = new Node(9, 2);
		Node node4 = new Node(12, 2);
		Node node5 = new Node(4, 0);
		Node node6 = new Node(6, 0);
		Node node7 = new Node(7, 0);
		Node node8 = new Node(8, 0);
		//add nodes as children
		root.addChildren(node1);
		root.addChildren(node2);
		node1.addChildren(node3);
		node1.addChildren(node4);
		node3.addChildren(node5);
		node3.addChildren(node6);
		node4.addChildren(node7);
		node4.addChildren(node8);

		return root;
	}

	public static int minMax(Node node, int depth, boolean isMaximizing) {
		//base case 
		if (depth == 10 || node.getChildren().length == 0) {
			return node.value;
		}

		//some helpful printout to the console
		System.out.println("depth: " + depth);
		System.out.println("node value: " + node.value);
		System.out.println("Number of children: " 
							+ node.getChildren().length);
		depth++;
		
		//Recurrent case
		if (isMaximizing) {
			//initial lowest possible value
			int maxVal = Integer.MIN_VALUE;
			//best next move
			Node bestChild = null; 
			//all of the children on a current node
			for (Node child : node.children) { 
				
				int eval = minMax(child, depth, false);//recursion until base case
				// maxEval = Math.max(maxEval, eval);
				//after base case return value is checked if its larger than before
				//thats the choice maximizer wants to choose
				if (eval >= maxVal) {
					maxVal = eval;
					bestChild = child;
					System.out.println("best child " + bestChild.value);
				}
			}
			return maxVal;
			//else similar to maximizer but for the lowest values
			//it doesnt have the best next move logic 
			//since it is the computers choice
		} else {
			int minEval = Integer.MAX_VALUE;
			for (Node child : node.children) {
				int eval = minMax(child, depth, true);
				minEval = Math.min(minEval, eval);
			}

			return minEval;
		}
	}

}

/**
 * A help class representing a node, 
 * AKA next step in a tree of possible choices
 * @author lukas kurasinski
 *
 */
class Node {
	int value;
	Node[] children;
	int index = 0;

	Node(int value, int childrenSize) {
		this.value = value;
		this.children = new Node[childrenSize];
	}

	public Node[] getChildren() {
		return children;
	}

	public void addChildren(Node child) {
		children[index] = child;
		index++;
	}

}
