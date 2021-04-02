package app;

import fileIO.BitInputManager;
import fileIO.ExtendedBufferedInputStream;
import fileIO.ExtendedBufferedOutputStream;
import fileIO.FilePathManager;
import huffman.HuffmanDecoder;


import java.io.*;

public class DecompressionController {
	
	// Private Instance Variables
    private File _compressedFile;
    private File _decompressedFile;
    private ExtendedBufferedInputStream _compressedInputStream;
    private ExtendedBufferedOutputStream _decompressedOutputStream;
    private BitInputManager _bitInputManager;
    private HuffmanDecoder _huffmanDecoder;

    // Getters & Setters
    private File compressedFile() {
        return this._compressedFile;
    }

    private void setCompressedFile(File aCompressedFile) {
        this._compressedFile = aCompressedFile;
    }

    private File decompressedFile() {
        return this._decompressedFile;
    }

    private void setDecompressedFile(File aDecompressedFile) {
        this._decompressedFile = aDecompressedFile;
    }

    private ExtendedBufferedInputStream compressedInputStream() {
        return this._compressedInputStream;
    }

    private void setCompressedInputStream(ExtendedBufferedInputStream aCompressedInputStream) {
        this._compressedInputStream = aCompressedInputStream;
    }

    private ExtendedBufferedOutputStream decompressedOutputStream() {
        return this._decompressedOutputStream;
    }

    private void setDecompressedOutputStream(ExtendedBufferedOutputStream aDecompressedOutputStream) {
        this._decompressedOutputStream = aDecompressedOutputStream;
    }

    private BitInputManager bitInputManager() {
        return this._bitInputManager;
    }

    private void setBitInputManager(BitInputManager aBitInputManager) {
        this._bitInputManager = aBitInputManager;
    }

    private HuffmanDecoder huffmanDecoder() {
        return this._huffmanDecoder;
    }

    private void setHuffmanDecoder(HuffmanDecoder aHuffmanDecoder) {
        this._huffmanDecoder = aHuffmanDecoder;
    }

    // Constructor
    public DecompressionController() {
    }

    // Private Instance Methods
    private boolean initCompressedFile() {
        AppView.outputLine("");
        AppView.outputLine("? ������ Ǯ ������ ��ο� �̸��� �Է��Ͻÿ�: ");
        String filePath = AppView.inputFilePath();
        String fileName = AppView.inputFileName();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        String filePathAndName = filePath + "/" + fileName;
        this.setCompressedFile(new File(filePathAndName));
        if(!fileExtension.equals("MYZIP")) {
        	AppView.outputLine("!����: ���� ���� (" + filePathAndName + ") �� Ȯ���ڰ� (.MYZIP) �� �ƴմϴ�.");
        	return false;
        }
        if (this.compressedFile().exists()) {
            return true;
        } else {
            AppView.outputLine("!����: ���� ���� (" + filePathAndName + ") �� �������� �ʽ��ϴ�.");
            return false;
        }
    }

    private void initDecompressedFile() {
        AppView.outputLine("");
        String filePathAndName = FilePathManager.getFilePathAndNameWithoutExtension(this.compressedFile());
        this.setDecompressedFile(new File(filePathAndName));
        if (this.decompressedFile().exists()) {
            AppView.outputLine("!���: ���� ���� ���� (" + filePathAndName + ") �� �̹� �����մϴ�.");
            AppView.outputLine("- ���� ���� ������ �̸��� �ٸ� ������ �ٲپ� ó���մϴ�:");
            String filePathAndnameWithoutExtension = FilePathManager.getFilePathAndNameWithoutExtension(this.decompressedFile());
            String OrigianlFileExtension = "." + filePathAndName.substring(filePathAndName.lastIndexOf(".") + 1);
            String filePathAndNameWithInfix = filePathAndnameWithoutExtension + "_UNZIP_";
            String decompressedFilePathAndName = null;
            int numberCount = 0;

            do {
                ++numberCount;
                decompressedFilePathAndName = filePathAndNameWithInfix + numberCount + OrigianlFileExtension;
                this.setDecompressedFile(new File(decompressedFilePathAndName));
            } while (this.decompressedFile().exists());

            AppView.outputLine("- ���ο� ���� ���� ������ �̸���" + decompressedFilePathAndName + " �Դϴ�.");
        }

    }

    private short[][] readSerializedHuffmanTree() throws IOException {
        int sizeOfSerialArray = this.compressedInputStream().readShort();
        short[][] serializedHuffmanTree = new short[sizeOfSerialArray][2];

        for (int i = 0; i < sizeOfSerialArray; ++i) {
            try {
                serializedHuffmanTree[i][0] = this.compressedInputStream().readShort();
                serializedHuffmanTree[i][1] = this.compressedInputStream().readShort();
            } catch (IOException var5) {
                AppView.outputLine("!����: [����ȭ�� ������ Ʈ��]�� ���Ͽ��� �д� �۾��� �����߽��ϴ�.");
                throw var5;
            }
        }

        return serializedHuffmanTree;
    }

    private long readNumberOfBitsOfCompressedData() throws IOException {
        try {
            return this.compressedInputStream().readLong();
        } catch (IOException var2) {
            AppView.outputLine("!����: [����� �������� ��Ʈ ��]�� ���Ͽ��� �д� �۾��� �����߽��ϴ�.");
            throw var2;
        }
    }

    private void openCompressedInputStream() throws IOException {
        try {
            FileInputStream compressedFileInputStream = new FileInputStream(this.compressedFile());
            this.setCompressedInputStream(new ExtendedBufferedInputStream(compressedFileInputStream));
        } catch (FileNotFoundException var2) {
            AppView.outputLine("!����: ����� ������ �� �� �����ϴ�.");
            throw var2;
        }
    }

    private void openDecompressedOutputStream() throws IOException {
        try {
            FileOutputStream decompressedFileOutputStream = new FileOutputStream(this.decompressedFile());
            this.setDecompressedOutputStream(new ExtendedBufferedOutputStream(decompressedFileOutputStream));
        } catch (FileNotFoundException var2) {
            AppView.outputLine("!����: ���� ������ ������ �� �� �����ϴ�.");
            throw var2;
        }
    }

    private void writeDecompressedBits() throws IOException {
        try {
            long numberOfData = this.readNumberOfBitsOfCompressedData();

            for (long i = 0L; i < numberOfData; ++i) {
                int bits = this.bitInputManager().readBit();
                int decoded = this.huffmanDecoder().decodeBit(bits);
                if (decoded != -1) {
                    this.decompressedOutputStream().write(decoded);
                }
            }

        } catch (IOException var7) {
            AppView.outputLine("!����: ���� ������ �����͸� ���Ͽ� ���� �۾��� �����Ͽ����ϴ�.");
            throw var7;
        }
    }

    private void closeCompressedInputStream() throws IOException {
        try {
            this.compressedInputStream().close();
        } catch (IOException var2) {
            AppView.outputLine("!����: ���� ���� �ݱ⸦ �����߽��ϴ�.");
            throw var2;
        }
    }

    private void closeDecompressedOutputStream() throws IOException {
        try {
            this.decompressedOutputStream().close();
        } catch (IOException var2) {
            AppView.outputLine("!����: ���� ������ ���� �ݱ⸦ �����߽��ϴ�.");
            throw var2;
        }
    }
    
    private void decompress() throws IOException {
        this.openCompressedInputStream();
        this.openDecompressedOutputStream();
        this.setBitInputManager(new BitInputManager(this.compressedInputStream()));
        short[][] serializedHuffmanTree = this.readSerializedHuffmanTree();
        this.setHuffmanDecoder(new HuffmanDecoder(serializedHuffmanTree));
        this.writeDecompressedBits();
        this.closeCompressedInputStream();
        this.closeDecompressedOutputStream();
    }

    private void showStatistics() {
        AppView.outputLine("> ���� ���� ����:");
        long compressedFileSize = this.compressedFile().length();
        long decompressedFileSize = this.decompressedFile().length();
        AppView.outputLine("- ���� ����: " + this.compressedFile().getAbsolutePath() + " (" + compressedFileSize + "Byte)");
        AppView.outputLine("- ���� ���� ����: " + this.decompressedFile().getAbsolutePath() + " (" + decompressedFileSize + "Byte)");
    }

    // Public Method
    public void run() {
        if (this.initCompressedFile()) {
            this.initDecompressedFile();

            try {
                this.decompress();
                AppView.outputLine("");
                AppView.outputLine("! ���� ������ ���������� ���ƽ��ϴ�.");
                this.showStatistics();
            } catch (IOException var2) {
                AppView.outputLine("!����: ���� ������ �����ϴ� ���ȿ� ���� ó�� ������ �߻��Ͽ����ϴ�.");
            }
        }

    }
}