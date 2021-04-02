package huffman;

public class HuffmanTree implements Comparable<HuffmanTree> {
	// Private Instance Variables
	private HuffmanTreeNode _root;
	private long _frequency;

	// Getters & Setters
	private void setFrequency(long newFrequency) {
		this._frequency = newFrequency;
	}

	public long frequency() {
		return this._frequency;
	}

	protected HuffmanTreeNode root() {	// This method can be used only inside the same package "huffman" 
		return this._root;
	}

	private void setRoot(HuffmanTreeNode newRoot) {
		this._root = newRoot;
	}

	// Constructor for Leaf only Tree
	protected HuffmanTree(short givenByteCode, long givenFrequency) {
		this.setFrequency(givenFrequency);
		this.setRoot(new HuffmanTreeLeafNode(givenByteCode));
	}
	
	// Constructor for making a tree using two subtrees
	protected HuffmanTree(HuffmanTree givenLeftSubtree, HuffmanTree givenRightSubtree) {
		this.setFrequency(givenLeftSubtree.frequency() + givenRightSubtree.frequency());
		this.setRoot(new HuffmanTreeNode(givenLeftSubtree.root(), givenRightSubtree.root()));
	}

	@Override
	public int compareTo(HuffmanTree otherTree) {
		// We cannot use the return statement as follows:
		// 		return (this.frequency() - otherTree.frequency());
		// This is because the type of frequency() is "long".
		if (this.frequency() < otherTree.frequency()) {
			return -1;
		} else if (this.frequency() > otherTree.frequency()) {
			return +1;
		} else {
			return 0;
		}
	}
}
