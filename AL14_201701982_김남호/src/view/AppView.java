package view;

import java.util.Scanner;

public final class AppView {
	private static Scanner scanner = new Scanner(System.in);

	//constructor
	private AppView() { 
	}

	// String �� enter ���
	public static void outputLine(String aString) {
		System.out.println(aString);
	} 

	// String ���
	public static void output(String aString) {
		System.out.print(aString);
	} 

	private static final boolean DEBUG_MODE = true;

	public static void outputDebugMessage(String aString) {
		if (DEBUG_MODE) {
			AppView.output(aString);
		}
	}

	public static char inputChar() {
		String s = scanner.next();
		return s.charAt(0);
	}

	public static int inputInt() {
		return Integer.parseInt(AppView.scanner.next());
	}
}
