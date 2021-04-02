package huffman;

public class HuffmanTreeLeafNode extends HuffmanTreeNode {
	// Private Instance Variables 
	private short _byteCode;

	// Getter & Setter
	protected short byteCode() {	// Used only inside the some package "huffman"
		return this._byteCode;
	}

	private void setByteCode(short newByteCode) {
		this._byteCode = newByteCode;
	}

	// Constructor
	protected HuffmanTreeLeafNode(short givenByteCode) {	// Used only inside the same package "huffman".
		this.setLeft(null);
		this.setRight(null);
		this.setByteCode(givenByteCode);
	}
}
