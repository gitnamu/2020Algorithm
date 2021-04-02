package huffman;

public class HuffmanDecoder {
	
	// Private Instance Variables
	private short[][] _serializedHuffmanTree;
	private int _currentNodeIndex;
	
	// Getters & Setters
	public short[][] serializedHuffmanTree() {
		return _serializedHuffmanTree;
	}
	public void setSerializedHuffmanTree(short[][] newSerializedHuffmanTree) {
		this._serializedHuffmanTree = newSerializedHuffmanTree;
	}
	public int currentNodeIndex() {
		return _currentNodeIndex;
	}
	public void setCurrentNodeIndex(int newCurrentNodeIndex) {
		this._currentNodeIndex = newCurrentNodeIndex;
	}
	
	// Constructor
	public HuffmanDecoder(short[][] givenSerializedHuffmanTree){
		this.setCurrentNodeIndex(Huffman_CONST.ROOT_NODE_INDEX);
		this.setSerializedHuffmanTree(givenSerializedHuffmanTree);
	}
	
	// Public method
	public int decodeBit(int bitValue) {
		int returnNumber = -1;
		if (bitValue == Huffman_CONST.LEFT_OF_NODE) {
			this.setCurrentNodeIndex(this.serializedHuffmanTree()[this.currentNodeIndex()][Huffman_CONST.LEFT_OF_NODE]);
		} else {
			this.setCurrentNodeIndex(
					this.serializedHuffmanTree()[this.currentNodeIndex()][Huffman_CONST.RIGHT_OF_NODE]);
		}

		if (this.serializedHuffmanTree()[this.currentNodeIndex()][Huffman_CONST.LEFT_OF_NODE] == -1) {
			returnNumber = this.serializedHuffmanTree()[this.currentNodeIndex()][Huffman_CONST.RIGHT_OF_NODE];
			this.setCurrentNodeIndex(Huffman_CONST.ROOT_NODE_INDEX);
		}
		
		return returnNumber;

	}
}
