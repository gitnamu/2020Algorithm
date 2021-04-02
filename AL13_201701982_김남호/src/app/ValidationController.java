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
        AppView.outputLine("? 첫번째 파일의 경로와 이름을 입력하시오:");
        String filePath = AppView.inputFilePath();
        String fileName = AppView.inputFileName();
        String filePathAndName = filePath + "/" + fileName;
        this.setFirstFilePath(filePath);
        this.setFirstFile(new File(filePathAndName));
        if (this.firstFile().exists()) {
            return true;
        } else {
            AppView.outputLine("!오류: 파일 (" + filePathAndName + ") 이 존재하지 않습니다.");
            return false;
        }
    }

    private boolean initSecondFile() {
        AppView.outputLine("");
        AppView.outputLine("? 두번째 파일의 경로와 이름을 입력하시오:");
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
            AppView.outputLine("!오류: 파일 (" + filePathAndName + ") 이 존재하지 않습니다.");
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
            AppView.outputLine("!오류: 압축닫기를 실패했습니다.");
            throw var4;
        }
    }

    private int readByteFromFirstInputStream() throws IOException {
        try {
            return this.firstInputStream().read();
        } catch (IOException var2) {
            AppView.outputLine("!오류: 첫번째 파일 읽기를 실패하였습니다.");
            throw var2;
        }
    }

    private int readByteFromSecondInputStream() throws IOException {
        try {
            return this.secondInputStream().read();
        } catch (IOException var2) {
            AppView.outputLine("!오류: 두번째 파일 읽기를 실패하였습니다.");
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
                    System.out.println("> 두 파일의 내용은 동일합니다.");
                } else {
                    System.out.println("> 두 파일의 내용은 동일하지 않습니다.");
                }
            } catch (IOException var2) {
                AppView.outputLine("!오류: 검증을 실행하는 동안에 파일 처리 오류가 발생하였습니다.");
            }
        }

    }
}
