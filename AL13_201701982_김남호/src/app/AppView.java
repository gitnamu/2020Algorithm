package app;

import java.util.Scanner;

public final class AppView {
	private static Scanner scanner = new Scanner(System.in); 

	private AppView() {
	}

	public static void outputLine(String aString) {
		System.out.println(aString);
	} 

	public static void output(String aString) {
		System.out.print(aString);
	}

	public static int inputInteger() throws NumberFormatException {
		// 정수가 아닌 경우의 에러 처리를 보완할 것: exception throws
		return Integer.parseInt(AppView.scanner.next());
	}

	public static String inputString() {
		return AppView.scanner.next();
	}

	public static String inputFileName() {
		AppView.output("? 파일 이름을 입력하시오 : ");
		return AppView.scanner.next();
	}

	public static String inputFilePath() {
		AppView.output("? 파일 경로를 입력하시오 (현재 폴더를 사용하려면 '.'을 입력하시오) : ");
		return AppView.scanner.next();
	}

	public static boolean inputAnswerForUsingSamePath() {
		AppView.outputLine("? 두번째 파일의 경로를 첫번째 파일의 경로와 동일한 것을 사용하겠습니까?");
		AppView.output(" 동일한 경로를 사용하려면 'Y'나 'y'를 치고, 다른 경로를 사용하려면 공백이 아닌 다른 아무 키나 치시오 : ");
		char answer = AppView.scanner.next().charAt(0);
		return (answer == 'Y' || answer == 'y');
	}
}
