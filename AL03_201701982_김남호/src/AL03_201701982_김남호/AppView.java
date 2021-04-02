package AL03_201701982_김남호;
import java.util.Scanner;

public final class AppView {
	private static Scanner scanner = new Scanner(System.in);
	
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
	//정수 하나를 입력받는다 
	public static int inputInt() {
		int numberOfVertices;
		String scannedToken;
		//한 줄을 입력받아 scannedToken에 저장한다.
		scannedToken = AppView.scanner.next();
		//scannedToken값을 int형으로 형변환하여 numberOfVertices에 저장한다.
		numberOfVertices = Integer.parseInt(scannedToken);	
		
		//numberOfVertices을 반환한다.
		return numberOfVertices;	
	}
	//vertex의 수를 입력받는다
	public static int inputNumberOfVertices() {
		int numberOfVertices;
		String scannedToken;
		//올바른 수가 입력될 때 까지 반복한다 
		while(true) {
			AppView.output("? vertex 수를 입력하시오: ");
			scannedToken = AppView.scanner.next();
			//입력받은 수가 int형이 아니면 에러 메세지를 출력한다
			try {
				numberOfVertices = Integer.parseInt(scannedToken);
				return numberOfVertices;
			}
			catch(NumberFormatException e) {
				AppView.outputLine("(오류) Vertex 수 입력에 오류가 있습니다: "+scannedToken);
			}
		}
	}
	//edge의 수를 입력받는다
	public static int inputNumberOfEdges() {
		int numberOfEdges;
		String scannedToken;
		//올바른 수가 입력될 때 까지 반복한다 
		while(true) {
			AppView.output("? edge 수를 입력하시오: ");
			scannedToken = AppView.scanner.next();
			//입력받은 수 가 int형이 아니면 에러 메세지를 출력한다
			try {
				numberOfEdges = Integer.parseInt(scannedToken);
				return numberOfEdges;
			}
			catch(NumberFormatException e) {
				AppView.outputLine("(오류) edge 수 입력에 오류가 있습니다: "+scannedToken);
			}
		}
	}
	//tail vertex를 입력받는다
	public static int inputTailVertex() {
		int tailVertex;
		String scannedToken;
		//올바른 수가 입력될 때 까지 반복한다
		while(true) {
			AppView.output("? tail vertex 를 입력하시오: ");
			scannedToken = AppView.scanner.next();
			//입력받은 수가 int형이 아니면 에러 메세지를 출력한다
			try {
				tailVertex = Integer.parseInt(scannedToken);
				return tailVertex;
			}
			catch(NumberFormatException e) {
				AppView.outputLine("(오류) tail vertex 입력에 오류가 있습니다: "+scannedToken);
			}
		}
	}
	//head vertex를 입력받는다
	public static int inputHeadVertex() {
		int headVertex;
		String scannedToken;
		//올바른 수가 입력될 때 까지 반복한다
		while(true) {
			AppView.output("? head vertex 를 입력하시오: ");
			scannedToken = AppView.scanner.next();
			//입력 받은 수가 int형이 아니면 에러 메세지를 출력한다
			try {
				headVertex = Integer.parseInt(scannedToken);
				return headVertex;
			}
			catch(NumberFormatException e) {
				AppView.outputLine("(오류) head vertex 입력에 오류가 있습니다: "+scannedToken);
			}
		}
	}
}
