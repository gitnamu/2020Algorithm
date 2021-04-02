package app;
import java.util.Scanner;

public final class AppView {
	private static Scanner scanner = new Scanner(System.in);
	
	// Only for controlling debugging message
	private static final boolean DEBUG_MODE = true;
	public static void outputDebugMessage(String aString) {
		if(DEBUG_MODE) {
			System.out.print(aString);
		}
	}
	
	private AppView() {
	}
	//문장 및 enter 출력
	public static void outputLine(String aString) {
		System.out.println(aString);
	}
	//문장  출력
	public static void output(String aString) {
		System.out.print(aString);
	}
}
