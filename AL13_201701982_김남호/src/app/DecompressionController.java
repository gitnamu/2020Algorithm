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
        AppView.outputLine("? 압축을 풀 파일의 경로와 이름을 입력하시오: ");
        String filePath = AppView.inputFilePath();
        String fileName = AppView.inputFileName();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        String filePathAndName = filePath + "/" + fileName;
        this.setCompressedFile(new File(filePathAndName));
        if(!fileExtension.equals("MYZIP")) {
        	AppView.outputLine("!오류: 압축 파일 (" + filePathAndName + ") 의 확장자가 (.MYZIP) 이 아닙니다.");
        	return false;
        }
        if (this.compressedFile().exists()) {
            return true;
        } else {
            AppView.outputLine("!오류: 압축 파일 (" + filePathAndName + ") 이 존재하지 않습니다.");
            return false;
        }
    }

    private void initDecompressedFile() {
        AppView.outputLine("");
        String filePathAndName = FilePathManager.getFilePathAndNameWithoutExtension(this.compressedFile());
        this.setDecompressedFile(new File(filePathAndName));
        if (this.decompressedFile().exists()) {
            AppView.outputLine("!경고: 압축 해제 파일 (" + filePathAndName + ") 이 이미 존재합니다.");
            AppView.outputLine("- 압축 해제 파일의 이름을 다른 것으로 바꾸어 처리합니다:");
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

            AppView.outputLine("- 새로운 압축 해제 파일의 이름은" + decompressedFilePathAndName + " 입니다.");
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
                AppView.outputLine("!오류: [직렬화된 허프만 트리]을 파일에서 읽는 작업을 실패했습니다.");
                throw var5;
            }
        }

        return serializedHuffmanTree;
    }

    private long readNumberOfBitsOfCompressedData() throws IOException {
        try {
            return this.compressedInputStream().readLong();
        } catch (IOException var2) {
            AppView.outputLine("!오류: [압축된 데이터의 비트 수]를 파일에서 읽는 작업을 실패했습니다.");
            throw var2;
        }
    }

    private void openCompressedInputStream() throws IOException {
        try {
            FileInputStream compressedFileInputStream = new FileInputStream(this.compressedFile());
            this.setCompressedInputStream(new ExtendedBufferedInputStream(compressedFileInputStream));
        } catch (FileNotFoundException var2) {
            AppView.outputLine("!오류: 압축된 파일을 열 수 없습니다.");
            throw var2;
        }
    }

    private void openDecompressedOutputStream() throws IOException {
        try {
            FileOutputStream decompressedFileOutputStream = new FileOutputStream(this.decompressedFile());
            this.setDecompressedOutputStream(new ExtendedBufferedOutputStream(decompressedFileOutputStream));
        } catch (FileNotFoundException var2) {
            AppView.outputLine("!오류: 압축 해제할 파일을 열 수 없습니다.");
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
            AppView.outputLine("!오류: 압축 해제한 데이터를 파일에 쓰는 작업을 실패하였습니다.");
            throw var7;
        }
    }

    private void closeCompressedInputStream() throws IOException {
        try {
            this.compressedInputStream().close();
        } catch (IOException var2) {
            AppView.outputLine("!오류: 압축 파일 닫기를 실패했습니다.");
            throw var2;
        }
    }

    private void closeDecompressedOutputStream() throws IOException {
        try {
            this.decompressedOutputStream().close();
        } catch (IOException var2) {
            AppView.outputLine("!오류: 압축 해제할 파일 닫기를 실패했습니다.");
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
        AppView.outputLine("> 압축 해제 정보:");
        long compressedFileSize = this.compressedFile().length();
        long decompressedFileSize = this.decompressedFile().length();
        AppView.outputLine("- 원본 파일: " + this.compressedFile().getAbsolutePath() + " (" + compressedFileSize + "Byte)");
        AppView.outputLine("- 압축 해제 파일: " + this.decompressedFile().getAbsolutePath() + " (" + decompressedFileSize + "Byte)");
    }

    // Public Method
    public void run() {
        if (this.initCompressedFile()) {
            this.initDecompressedFile();

            try {
                this.decompress();
                AppView.outputLine("");
                AppView.outputLine("! 압축 해제를 성공적으로 마쳤습니다.");
                this.showStatistics();
            } catch (IOException var2) {
                AppView.outputLine("!오류: 압축 해제를 실행하는 동안에 파일 처리 오류가 발생하였습니다.");
            }
        }

    }
}