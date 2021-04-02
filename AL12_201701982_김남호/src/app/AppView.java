package app;

public class AppView {
	private static final boolean DEBUG_MODE = true;


	private AppView() { 
	} 

	public static void outputLine(String aString) {
		System.out.println(aString);
	} 

	public static void output(String aString) {
		System.out.print(aString);
	} 

	public static void outputDebugMessage(String aString) {
		if (DEBUG_MODE) {
			System.out.print(aString);
		}
	}
}
