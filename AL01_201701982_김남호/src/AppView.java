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
}
