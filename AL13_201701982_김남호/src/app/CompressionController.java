package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import fileIO.BitOutputManager;
import fileIO.ExtendedBufferedInputStream;
import fileIO.ExtendedBufferedOutputStream;
import fileIO.FileIO_CONST;
import fileIO.FilePathManager;

import huffman.HuffmanCode;
import huffman.HuffmanEncoder;
import huffman.Huffman_CONST;

public class CompressionController {
	
	// Private instance Variables
	private File _originalFile;
	private File _compressedFile;

	private ExtendedBufferedInputStream _originalInputStream;
	private ExtendedBufferedOutputStream _compressedOutputStream;

	private BitOutputManager _bitOutputManger;

	private HuffmanEncoder _huffmanEncoder;

	// Getters & Setters
	private File originalFile() {
		return this._originalFile;
	}

	private void setOriginalFile(File newOriginalFile) {
		this._originalFile = newOriginalFile;
	}

	private File compressedFile() {
		return this._compressedFile;
	}

	private void setCompressedFile(File newCompressedFile) {
		this._compressedFile = newCompressedFile;
	}

	private ExtendedBufferedInputStream originalInputStream() {
		return this._originalInputStream;
	}

	private void setOriginalInputStream(ExtendedBufferedInputStream newOriginalInputStream) {
		this._originalInputStream = newOriginalInputStream;
	}

	private ExtendedBufferedOutputStream compressedOutputStream() {
		return this._compressedOutputStream;
	}

	private void setCompressedOutputStream(ExtendedBufferedOutputStream newCompressedOutputStream) {
		this._compressedOutputStream = newCompressedOutputStream;
	}

	private BitOutputManager bitOutputManager() {
		return this._bitOutputManger;
	}

	private void setBitOutputManager(BitOutputManager newBitOutputManager) {
		this._bitOutputManger = newBitOutputManager;
	}

	private HuffmanEncoder huffmanEncoder() {
		return this._huffmanEncoder;
	}

	private void setHuffmanEncoder(HuffmanEncoder newHuffmanEncoder) {
		this._huffmanEncoder = newHuffmanEncoder;
	}
	
	// Private methods
	private boolean initOriginalFile() {
		AppView.outputLine("");
		AppView.outputLine("? ������ ���� ������ ��ο� �̸��� �Է��Ͻÿ� : ");
		String filePath = AppView.inputFilePath();
		String fileName = AppView.inputFileName();
		String filePathAndName = filePath + "/" + fileName;
		this.setOriginalFile(new File(filePathAndName));
		if (this.originalFile().exists()) {
			return true;
		} else {
			AppView.outputLine("!����: ���� (" + filePathAndName + ") �� �������� �ʽ��ϴ�.");
			return false;
		}
	}

	private void initCompressedFile() {
		AppView.outputLine("");
		String filePathAndName = this.originalFile().getAbsolutePath() + App_CONST.COMPRESSED_FILE_EXTENSION;
		this.setCompressedFile(new File(filePathAndName));
		if (this.compressedFile().exists()) {
			AppView.outputLine("!��� : ���� (" + filePathAndName + ") �� �̹� �����մϴ�.");
			AppView.outputLine("- ���� ������ �̸��� �ٸ� ������ �ٲپ� ó���մϴ� : ");
			String compressedFilePathAndNameWithoutExtension = FilePathManager
					.getFilePathAndNameWithoutExtension(this.originalFile());
			String originalFileExtension = FilePathManager.getFileExtension(this.originalFile());
			String compressedFilePathAndNameWithInfix = compressedFilePathAndNameWithoutExtension
					+ App_CONST.COMPRESSED_FILE_INFIX;
			String compressedFilePathAndName = null;
			int compressedFileSerialNumber = 0;
			do {
				compressedFileSerialNumber++;
				compressedFilePathAndName = compressedFilePathAndNameWithInfix + compressedFileSerialNumber
						+ originalFileExtension + App_CONST.COMPRESSED_FILE_EXTENSION;
				this.setCompressedFile(new File(compressedFilePathAndName));
			} while (this.compressedFile().exists());
			AppView.outputLine("- ���ο� ���� ������ �̸��� " + compressedFilePathAndName + " �Դϴ�.");
		}
	}

	// Constructor
	protected CompressionController() {
	}

	private void compress() throws IOException {
		this.setHuffmanEncoder(new HuffmanEncoder());

		this.sendAllByteCodesToHuffmanEncoderForCountingFrequencies();
		this.huffmanEncoder().buildHuffmanCode();

		this.openCompressedOutputStream();
		this.openOriginalInputStream();
		this.setBitOutputManager(new BitOutputManager(this.compressedOutputStream()));
		this.writeSerializedHuffmanTree();
		this.writeNumberOfBitsOfCompressedData();

		this.writeAllCompressedBits();

		this.closeOriginalInputStream();
		this.closeCompressedOutputStream();

		return;
	}

	private int readByteCode() throws IOException {
		try {
			return this.originalInputStream().read();
		} catch (IOException e) {
			AppView.outputLine("!���� : ���� ���� �ϱ⸦ �����Ͽ����ϴ�.");
			throw e;
		}
	}

	private void sendAllByteCodesToHuffmanEncoderForCountingFrequencies() throws IOException {
		// ���� ������ ��� byte code�� HuffmanEncoder�� ������, byte code�� �󵵸� ���� �Ѵ�.
		try {
			this.openOriginalInputStream();
			int byteCode = this.readByteCode();
			while (byteCode != FileIO_CONST.END_OF_INPUT_STREAM) {
				this.huffmanEncoder().incrementFrequencyOf(byteCode);
				byteCode = this.readByteCode();
			}
			this.closeOriginalInputStream();
		} catch (IOException e) {
			AppView.outputLine("!���� : ���� ������ �о� �ڵ� �󵵸� ���� �۾��� �����Ͽ����ϴ�.");
			throw e;
		}
	}

	private void writeSerializedHuffmanTree() throws IOException {
		// ������� HuffmanTree�� ����ȭ �Ͽ� ���Ͽ� ����.
		// Writing Format:
		// 	numberOfNodesInSerializeHuffmanTree: short (2 byte)
		//	serializeHuffmanTree: Nodes(4 byte)�� ���� ��ŭ ���� �ȴ�.
		//		Node: 2���� short integer (left, right ���� 2 byte)
		int numberOfNodesInSerializedHuffmanTree = this.huffmanEncoder().numberOfNodesOfSerialzedHuffmanTree();
		this.compressedOutputStream().writeShort((short) numberOfNodesInSerializedHuffmanTree);
		for (int i = 0; i < numberOfNodesInSerializedHuffmanTree; i++) {
			try {
				this.compressedOutputStream()
						.writeShort(this.huffmanEncoder().serializedHuffmanTree()[i][Huffman_CONST.LEFT_OF_NODE]);
				this.compressedOutputStream()
						.writeShort(this.huffmanEncoder().serializedHuffmanTree()[i][Huffman_CONST.RIGHT_OF_NODE]);
			} catch (IOException e) {
				AppView.outputLine("!���� : [����ȭ�� ������ Ʈ��]�� ���Ͽ� ���� �۾��� �����Ͽ����ϴ�.");
				throw e;
			}
		}
	}

	private void writeNumberOfBitsOfCompressedData() throws IOException {
		try {
			this.compressedOutputStream().writeLong(this.huffmanEncoder().numberOfBitsOfCompressedData());
		} catch (IOException e) {
			AppView.outputLine("!���� : [����� �������� ��Ʈ ��]�� ���Ͽ� ���� �۾��� �����Ͽ����ϴ�.");
			throw e;
		}
	}

	private void openOriginalInputStream() throws FileNotFoundException {
		try {
			FileInputStream originalFileInputStream = new FileInputStream(this.originalFile());
			this.setOriginalInputStream(new ExtendedBufferedInputStream(originalFileInputStream));
		} catch (FileNotFoundException e) {
			AppView.outputLine("!���� : ���� ������ �� �� �����ϴ�.");
			throw e;
		}
	}

	private void openCompressedOutputStream() throws FileNotFoundException {
		try {
			FileOutputStream compressedFileOutputStream = new FileOutputStream(this.compressedFile());
			this.setCompressedOutputStream(new ExtendedBufferedOutputStream(compressedFileOutputStream));
		} catch (FileNotFoundException e) {
			AppView.outputLine("!���� : ���� ������ �� �� �����ϴ�.");
			throw e;
		}
	}

	private void flushBits() throws IOException {
		try {
			this.bitOutputManager().flush();
		} catch (IOException e) {
			AppView.outputLine("!���� : ������ ���� �ִ� ��Ʈ���� ���� �۾��� �����Ͽ����ϴ�.");
			throw e;
		}
	}

	private void writeHuffmanCode(HuffmanCode huffmanCode) throws IOException {
		for (int i = 0; i < huffmanCode.length(); i++) {
			try {
				this.bitOutputManager().writeBit(huffmanCode.bitAtIndex(i));
			} catch (IOException e) {
				AppView.outputLine("!���� : [������ �ڵ�]�� ���Ͽ� ���� �۾��� �����Ͽ����ϴ�.");
				throw e;
			}
		}
	}

	private void writeAllCompressedBits() throws IOException {
		try {
			int byteCode = this.readByteCode();
			while (byteCode != FileIO_CONST.END_OF_INPUT_STREAM) {
				HuffmanCode huffmanCode = this.huffmanEncoder().huffmanCodeOfByteCode(byteCode);
				this.writeHuffmanCode(huffmanCode);
				byteCode = this.readByteCode();
			}
			this.flushBits();
		} catch (IOException e) {
			AppView.outputLine("!���� : ���� ���Ͽ� ��Ʈ ������ ���⸦ �����Ͽ����ϴ�.");
			throw e;
		}
	}

	private void closeOriginalInputStream() throws IOException {
		try {
			this.originalInputStream().close();
		} catch (IOException e) {
			AppView.outputLine("!���� : ���� ���� �ݱ⸦ �����Ͽ����ϴ�.");
			throw e;
		}
	}

	private void closeCompressedOutputStream() throws IOException {
		try {
			this.compressedOutputStream().close();
		} catch (IOException e) {
			AppView.outputLine("!���� : ���� ���� �ݱ⸦ �����Ͽ����ϴ�.");
			throw e;
		}
	}

	private void showStatistics() {
		AppView.outputLine("> ���� ���� : ");
		long originalFileSize = this.originalFile().length();
		long compressedFileSize = this.compressedFile().length();
		AppView.outputLine("- ���� ���� : " + this.originalFile().getAbsolutePath() + " (" + originalFileSize + " Byte)");
		AppView.outputLine(
				"- ���� ���� : " + this.compressedFile().getAbsolutePath() + " (" + compressedFileSize + " Byte)");
		double compressionRatio = (double) compressedFileSize / (double) originalFileSize;
		AppView.outputLine("- ����� : " + String.format("%2.1f %%", compressionRatio * 100));
	}

	// Public method
	protected void run() {
		if (this.initOriginalFile()) {
			this.initCompressedFile();
			try {
				this.compress();
				AppView.outputLine("");
				AppView.outputLine("! ������ ���������� ���ƽ��ϴ�.");
				this.showStatistics();
			} catch (IOException e) {
				AppView.outputLine("!���� : ������ �����ϴ� ���ȿ� ���� ó�� ������ �߻��Ͽ����ϴ�.");
			}
		}
	}
}
