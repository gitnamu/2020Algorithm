package fileIO;

import java.io.File;

public abstract class FilePathManager {
	public static String getFileExtension(File file) {
		// ". "�� ������ ���·� file extension�� ��´�.
		String fileName = file.getName();
		int lastIndexOf = fileName.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return "";	// empty extension
		} else {
			return fileName.substring(lastIndexOf);
		}
	}

	public static String getFilePathAndNameWithoutExtension(File file) {
		// File extension�� ������ ���� �̸��� ��´�.
		// File extension�� ������, ���� �̸� �״�� ��´�.
		String filePathAndName = file.getAbsolutePath();
		int lastIndexOf = filePathAndName.lastIndexOf(".");
		if (lastIndexOf == -1) {
			// There is no extension in the fileName.
			return filePathAndName;
		} else {
			return filePathAndName.substring(0, lastIndexOf);
		}
	}
}
