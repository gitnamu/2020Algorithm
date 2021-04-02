package huffman;

import priorityQ.MinPriorityQ;
import priorityQ.MinPriorityQByHeap;

public class HuffmanEncoder {
	// Private Instance Variables
	private long[] _frequencyTable = new long[Huffman_CONST.MAX_NUMBER_OF_CODES];
	private HuffmanCode[] _huffmanCodeTable = new HuffmanCode[Huffman_CONST.MAX_NUMBER_OF_CODES];
	private MinPriorityQ<HuffmanTree> _huffmanForest;

	private HuffmanTree _huffmanTree;
	private short[][] _serializedHuffmanTree;	// = new short[Huffman.MAX_NUMBER_OF_TREENODES][2];
	private int _numberOfByteCodesActuallyUsed;
	private long _numberOfBitsOfCompressedData;

	// Getters & Setters
	private long[] frequencyTable() {
		return this._frequencyTable;
	}

	private long frequencyOf(int byteCode) {
		return this.frequencyTable()[byteCode];
	}

	private HuffmanCode[] huffmanCodeTable() {
		return this._huffmanCodeTable;
	}

	private MinPriorityQ<HuffmanTree> huffmanForest() {
		return this._huffmanForest;
	}

	private void setHuffmanForest(MinPriorityQ<HuffmanTree> newHuffmanForest) {
		this._huffmanForest = newHuffmanForest;
	}

	private HuffmanTree huffmanTree() {
		return this._huffmanTree;
	}

	private void setHuffmanTree(HuffmanTree newHuffmanTree) {
		this._huffmanTree = newHuffmanTree;
	}

	public short[][] serializedHuffmanTree() {
		return this._serializedHuffmanTree;
	}

	private void setSerializedHuffmanTree(short[][] newSerializedHuffmanTree) {
		this._serializedHuffmanTree = newSerializedHuffmanTree;
	}

	private int numberOfByteCodesActuallyUsed() {
		return this._numberOfByteCodesActuallyUsed;
	}

	private void setNumberOfByteCodesActuallyUsed(int newNumberOfByteCodesActuallyUsed) {
		this._numberOfByteCodesActuallyUsed = newNumberOfByteCodesActuallyUsed;
	}

	public long numberOfBitsOfCompressedData() {
		return this._numberOfBitsOfCompressedData;
	}

	private void setNumberOfBitsOfCompressedData(long newNumberOfBitsOfCompressedData) {
		this._numberOfBitsOfCompressedData = newNumberOfBitsOfCompressedData;
	}

	// Public Methods
	public void incrementFrequencyOf(int byteCode) {
		this.frequencyTable()[byteCode]++;
	}

	public HuffmanCode huffmanCodeOfByteCode(int byteCode) {
		return this.huffmanCodeTable()[byteCode];
	}

	public int numberOfNodesOfSerialzedHuffmanTree() {
		// SerializedHuffmanTree의 노드의 개수 : 실제로 사용되고 있는 byteCode의 개수의 2배보다 하나 작다.
		return (this.numberOfByteCodesActuallyUsed() * 2 - 1);
	}

	public void buildHuffmanCode() {
		this.setHuffmanTree(this.buildHuffmanTree());
		this.extractHuffmanCodeFromHuffmanTree();
		this.setNumberOfBitsOfCompressedData(this.calcNumberOfBitsOfCompressedData());
		
		this.serializeHuffmanTree(); 
	}

	// Private methods 
	private void initHuffmanForest() {
		for (short byteCode = 0; byteCode < Huffman_CONST.MAX_NUMBER_OF_CODES; byteCode++) {
			long frequencyOfByteCode = this.frequencyOf(byteCode);
			if (frequencyOfByteCode > 0) {
				// We do not need to use the byte codes with frequency 0.
				HuffmanTree currentTree = new HuffmanTree(byteCode, frequencyOfByteCode);
				this.huffmanForest().add(currentTree);
			}
		}
		this.setNumberOfByteCodesActuallyUsed(this.huffmanForest().size());
		return;
	}

	private HuffmanTree buildHuffmanTree() {
		this.setHuffmanForest(new MinPriorityQByHeap<HuffmanTree>(Huffman_CONST.MAX_NUMBER_OF_CODES));
		this.initHuffmanForest();
		while (this.huffmanForest().size() > 1) {
			HuffmanTree firstMinTree = this.huffmanForest().removeMin();
			HuffmanTree secondMinTree = this.huffmanForest().removeMin();
			this.huffmanForest().add(new HuffmanTree(firstMinTree, secondMinTree));
		}
		return this.huffmanForest().removeMin();
	}

	private void extractRecursively(HuffmanTreeNode node, HuffmanCode huffmanCode) {
		if (node.isLeaf()) {
			HuffmanTreeLeafNode leafNode = (HuffmanTreeLeafNode) node;
			this.huffmanCodeTable()[leafNode.byteCode()] = huffmanCode.clone();
		} else {
			huffmanCode.appendBit(false);
			this.extractRecursively(node.left(), huffmanCode);
			huffmanCode.removeLastBit();

			huffmanCode.appendBit(true);
			this.extractRecursively(node.right(), huffmanCode);
			huffmanCode.removeLastBit();
		}
	}

	private void extractHuffmanCodeFromHuffmanTree() {
		HuffmanCode huffmanCode = new HuffmanCode();
		this.extractRecursively(this.huffmanTree().root(), huffmanCode);
	}

	private long calcNumberOfBitsOfCompressedData() {
		long numberOfBits = 0;
		for (int i = 0; i < Huffman_CONST.MAX_NUMBER_OF_CODES; i++) {
			if (this.huffmanCodeTable()[i] != null) {
				HuffmanCode code = this.huffmanCodeTable()[i];
				numberOfBits += code.length() * this.frequencyTable()[i];
			}
		}
		return numberOfBits;
	}


	private int serializeHuffmanTreeRecursively(HuffmanTreeNode currentNode, int currentNodeIndex) {
		if (currentNode.isLeaf()) {
			short byteCode = ((HuffmanTreeLeafNode) currentNode).byteCode();
			this.serializedHuffmanTree()[currentNodeIndex][Huffman_CONST.LEFT_OF_NODE] = -1;
			this.serializedHuffmanTree()[currentNodeIndex][Huffman_CONST.RIGHT_OF_NODE] = byteCode;
			return 1;	// size of the single node tree
		} 
		
		else {	// Curent node is NOT a leaf.
			int leftNodeIndex = currentNodeIndex + 1;
			int sizeOfLeftSubtree = this.serializeHuffmanTreeRecursively(currentNode.left(), leftNodeIndex);
			this.serializedHuffmanTree()[currentNodeIndex][Huffman_CONST.LEFT_OF_NODE] = (short) leftNodeIndex;

			int rightNodeIndex = leftNodeIndex + sizeOfLeftSubtree;
			int sizeOfRightSubtree = this.serializeHuffmanTreeRecursively(currentNode.right(), rightNodeIndex);
			this.serializedHuffmanTree()[currentNodeIndex][Huffman_CONST.RIGHT_OF_NODE] = (short) rightNodeIndex;
			return (sizeOfLeftSubtree + sizeOfRightSubtree + 1);
		}
	}

	private void serializeHuffmanTree() {
		this.setSerializedHuffmanTree(new short[this.numberOfNodesOfSerialzedHuffmanTree()][2]);
		HuffmanTreeNode root = this.huffmanTree().root();
		this.serializeHuffmanTreeRecursively(root, Huffman_CONST.ROOT_NODE_INDEX);
	}
}