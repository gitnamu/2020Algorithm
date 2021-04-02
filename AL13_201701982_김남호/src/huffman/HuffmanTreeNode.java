package huffman;

public class HuffmanTreeNode {
	
	// Private Instance Variables
	private HuffmanTreeNode _left;
	private HuffmanTreeNode _right;

	// Getters & Setters
	protected HuffmanTreeNode left() {
		return this._left;
	}

	protected void setLeft(HuffmanTreeNode newLeft) {
		this._left = newLeft;
	}

	protected HuffmanTreeNode right() {
		return this._right;
	}

	protected void setRight(HuffmanTreeNode newRight) {
		this._right = newRight;
	}

	// Constructor
	protected HuffmanTreeNode() {
	}

	protected HuffmanTreeNode(HuffmanTreeNode givenLeft, HuffmanTreeNode givenRight) {
		this.setLeft(givenLeft);
		this.setRight(givenRight);
	}

	// Protected Method: Used only inside the same package "Huffman"
	protected boolean isLeaf() {
		return (this.left() == null && this.right() == null);
	}
}
