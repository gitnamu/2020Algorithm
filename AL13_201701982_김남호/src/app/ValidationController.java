package app;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ValidationController {
	
	// Private Instance Variables
    private File _firstFile;
    private File _secondFile;
    
    private String _firstFilePath;
    
    private BufferedInputStream _firstInputStream;
    private BufferedInputStream _secondInputStream;
    
    // Getters & Setters
    private File firstFile() {
        return this._firstFile;
    }

    private void setFirstFile(File aFirstFile) {
        this._firstFile = aFirstFile;
    }

    private File secondFile() {
        return this._secondFile;
    }

    private void setSecondFile(File aSecondFile) {
        this._secondFile = aSecondFile;
    }

    private String firstFilePath() {
        return this._firstFilePath;
    }

    private void setFirstFilePath(String aFirstFilePath) {
        this._firstFilePath = aFirstFilePath;
    }

    private BufferedInputStream firstInputStream() {
        return this._firstInputStream;
    }

    private void setFirstInputStream(BufferedInputStream aFirstInputStream) {
        this._firstInputStream = aFirstInputStream;
    }

    private BufferedInputStream secondInputStream() {
        return this._secondInputStream;
    }

    private void setSecondInputStream(BufferedInputStream aSecondInputStream) {
        this._secondInputStream = aSecondInputStream;
    }

    // Private Instance Methods
    private boolean initFirstFile() {
        AppView.outputLine("");
        AppView.outputLine("? ù��° ������ ��ο� �̸��� �Է��Ͻÿ�:");
        String filePath = AppView.inputFilePath();
        String fileName = AppView.inputFileName();
        String filePathAndName = filePath + "/" + fileName;
        this.setFirstFilePath(filePath);
        this.setFirstFile(new File(filePathAndName));
        if (this.firstFile().exists()) {
            return true;
        } else {
            AppView.outputLine("!����: ���� (" + filePathAndName + ") �� �������� �ʽ��ϴ�.");
            return false;
        }
    }

    private boolean initSecondFile() {
        AppView.outputLine("");
        AppView.outputLine("? �ι�° ������ ��ο� �̸��� �Է��Ͻÿ�:");
        String filePath;
        if (AppView.inputAnswerForUsingSamePath()) {
            filePath = this.firstFilePath();
        } else {
            filePath = AppView.inputFilePath();
        }

        String fileName = AppView.inputFileName();
        String filePathAndName = filePath + "/" + fileName;
        this.setSecondFile(new File(filePathAndName));
        if (this.secondFile().exists()) {
            return true;
        } else {
            AppView.outputLine("!����: ���� (" + filePathAndName + ") �� �������� �ʽ��ϴ�.");
            return false;
        }
    }

    private BufferedInputStream openInputStream(File file) throws IOException {
        return new BufferedInputStream(new FileInputStream(file));
    }

    private void closeInputStream(BufferedInputStream bufferedInputStream, File inputFile) throws IOException {
        try {
            bufferedInputStream.close();
        } catch (IOException var4) {
            AppView.outputLine("!����: ����ݱ⸦ �����߽��ϴ�.");
            throw var4;
        }
    }

    private int readByteFromFirstInputStream() throws IOException {
        try {
            return this.firstInputStream().read();
        } catch (IOException var2) {
            AppView.outputLine("!����: ù��° ���� �б⸦ �����Ͽ����ϴ�.");
            throw var2;
        }
    }

    private int readByteFromSecondInputStream() throws IOException {
        try {
            return this.secondInputStream().read();
        } catch (IOException var2) {
            AppView.outputLine("!����: �ι�° ���� �б⸦ �����Ͽ����ϴ�.");
            throw var2;
        }
    }

    private boolean validate() throws IOException {
        BufferedInputStream firstStream = this.openInputStream(this.firstFile());
        BufferedInputStream secondStream = this.openInputStream(this.secondFile());
        this.setFirstInputStream(firstStream);
        this.setSecondInputStream(secondStream);

        int byte1;
        int byte2;
        do {
            byte1 = this.readByteFromFirstInputStream();
            byte2 = this.readByteFromSecondInputStream();
            if (byte1 != byte2) {
                this.closeInputStream(firstStream, this.firstFile());
                this.closeInputStream(secondStream, this.secondFile());
                return false;
            }
        } while(byte1 != -1 && byte2 != -1);

        this.closeInputStream(firstStream, this.firstFile());
        this.closeInputStream(secondStream, this.secondFile());
        return byte1 == -1 && byte2 == -1;
    }

    // Constructor
    protected ValidationController() {
    }

    // Public Instance Method
    protected void run() {
        if (this.initFirstFile() && this.initSecondFile()) {
            try {
                if (this.validate()) {
                    System.out.println("> �� ������ ������ �����մϴ�.");
                } else {
                    System.out.println("> �� ������ ������ �������� �ʽ��ϴ�.");
                }
            } catch (IOException var2) {
                AppView.outputLine("!����: ������ �����ϴ� ���ȿ� ���� ó�� ������ �߻��Ͽ����ϴ�.");
            }
        }

    }
}
