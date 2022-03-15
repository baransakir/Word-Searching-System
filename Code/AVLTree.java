public class AVLTree {
	private Node root;

	public AVLTree() {
		root = null;
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public int height(Node node) {
		if (node == null)
			return 0;
		return node.getHeight();
	}

	public int getBalance(Node node) {
		if (root == null)
			return 0;
		return height(node.getLeft()) - height(node.getRight()); // -1,0,1
	}

	public int maxHeight(int leftHeight, int rightHeight) {
		return (leftHeight > rightHeight) ? leftHeight : rightHeight;
	}

	public Node minValueNode(Node node) {
		Node current = node;
		while (current.getLeft() != null)
			current = current.getLeft();
		return current;
	}

	public Node rightRotate(Node grandParent) {
		Node parent = grandParent.getLeft();
		Node child = parent.getRight();

		// Rotation
		parent.setRight(grandParent);
		grandParent.setLeft(child);
		// Update height
		grandParent.setHeight(maxHeight(height(grandParent.getLeft()), height(grandParent.getRight())) + 1);
		parent.setHeight(maxHeight(height(parent.getLeft()), height(parent.getRight())) + 1);

		return parent; // Return new root
	}

	public Node leftRotate(Node grandParent) {
		Node parent = grandParent.getRight();
		Node child = parent.getLeft();

		// Rotation
		parent.setLeft(grandParent);
		grandParent.setRight(child);
		// Update height
		grandParent.setHeight(maxHeight(height(grandParent.getLeft()), height(grandParent.getRight())) + 1);
		parent.setHeight(maxHeight(height(parent.getLeft()), height(parent.getRight())) + 1);

		return parent; // Return new root
	}

	public Node insert(Node node, String word) {
		if (word.equals("\n")) // Remove "\n" node from String
			return node;
		// BST insertion
		if (node == null)
			return (new Node(word));

		if (word.compareTo(node.getWord()) == 0) { // count++
			node.setCount(node.getCount() + 1);
		}

		if (word.compareTo(node.getWord()) < 0) // <=, >= duplicated values*
			node.setLeft(insert(node.getLeft(), word));
		else if (word.compareTo(node.getWord()) > 0)
			node.setRight(insert(node.getRight(), word));
		else
			return node;

		// Update height of this ancestor node
		node.setHeight(1 + maxHeight(height(node.getLeft()), height(node.getRight())));

		// Get the balance factor of this ancestor node to check whether this node
		int balance = getBalance(node);

		// UNBALANCED NODE
		// Left-Left Case
		if ((balance > 1) && (word.compareTo(node.getLeft().getWord())) < 0)
			return rightRotate(node);

		// Right-Right Case
		if ((balance < -1) && (word.compareTo(node.getRight().getWord())) > 0)
			return leftRotate(node);

		// Left-Right Case
		if ((balance > 1) && (word.compareTo(node.getLeft().getWord())) > 0) {
			node.setLeft(leftRotate(node.getLeft()));
			return rightRotate(node);
		}

		// Right-Left Case
		if ((balance < -1) && (word.compareTo(node.getRight().getWord())) < 0) {
			node.setRight(rightRotate(node.getRight()));
			return leftRotate(node);
		}

		return node;
	}

	public void deleteTree() {
		root = null;
	}

	public void inOrder(Node node) {
		if (node != null) {
			inOrder(node.getLeft());
			System.out.println(node.getWord() + "(" + node.getCount() + ") ");
			inOrder(node.getRight());
		}
	}

	public boolean search(String word) {
		Node position = root;
		while (position != null) {
			if (word.compareTo(position.getWord()) < 0)
				position = position.getLeft();
			else if (word.compareTo(position.getWord()) > 0)
				position = position.getRight();
			else // Data matched with position.getData()
				return true; // Data founded
		}
		return false; // Data did not find
	}

	public Node returnNode(String word) { // Return related node
		Node position = root;
		while (position != null) {
			if (word.compareTo(position.getWord()) < 0)
				position = position.getLeft();
			else if (word.compareTo(position.getWord()) > 0)
				position = position.getRight();
			else
				return position;
		}
		return null;
	}
}