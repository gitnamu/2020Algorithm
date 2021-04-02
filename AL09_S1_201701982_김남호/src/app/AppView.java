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
	//���� �� enter ���
	public static void outputLine(String aString) {
		System.out.println(aString);
	}
	//����  ���
	public static void output(String aString) {
		System.out.print(aString);
	}
	//���� �ϳ��� �Է¹޴´� 
	public static int inputInt() {
		int numberOfVertices;
		String scannedToken;
		//�� ���� �Է¹޾� scannedToken�� �����Ѵ�.
		scannedToken = AppView.scanner.next();
		//scannedToken���� int������ ����ȯ�Ͽ� numberOfVertices�� �����Ѵ�.
		numberOfVertices = Integer.parseInt(scannedToken);	
		
		//numberOfVertices�� ��ȯ�Ѵ�.
		return numberOfVertices;	
	}
	//vertex�� ���� �Է¹޴´�
	public static int inputNumberOfVertices() {
		int numberOfVertices;
		String scannedToken;
		//�ùٸ� ���� �Էµ� �� ���� �ݺ��Ѵ� 
		while(true) {
			AppView.output("? ������ ������ �Է��Ͻÿ�: ");
			scannedToken = AppView.scanner.next();
			//�Է¹��� ���� int���� �ƴϸ� ���� �޼����� ����Ѵ�
			try {
				numberOfVertices = Integer.parseInt(scannedToken);
				return numberOfVertices;
			}
			catch(NumberFormatException e) {
				AppView.outputLine("(����) ������ ���� �Է¿� ������ �ֽ��ϴ�: "+scannedToken);
			}
		}
	}
	//edge�� ���� �Է¹޴´�
	public static int inputNumberOfEdges() {
		int numberOfEdges;
		String scannedToken;
		//�ùٸ� ���� �Էµ� �� ���� �ݺ��Ѵ� 
		while(true) {
			AppView.output("? ���� ���� ������ �Է��Ͻÿ�: ");
			scannedToken = AppView.scanner.next();
			//�Է¹��� �� �� int���� �ƴϸ� ���� �޼����� ����Ѵ�
			try {
				numberOfEdges = Integer.parseInt(scannedToken);
				return numberOfEdges;
			}
			catch(NumberFormatException e) {
				AppView.outputLine("(����) ���� ���� ���� �Է¿� ������ �ֽ��ϴ�: "+scannedToken);
			}
		}
	}
	//tail vertex�� �Է¹޴´�
	public static int inputTailVertex() {
		int tailVertex;
		String scannedToken;
		//�ùٸ� ���� �Էµ� �� ���� �ݺ��Ѵ�
		while(true) {
			AppView.output("? ���� ���� ù ��° ���Ҹ� �Է��Ͻÿ�: ");
			scannedToken = AppView.scanner.next();
			//�Է¹��� ���� int���� �ƴϸ� ���� �޼����� ����Ѵ�
			try {
				tailVertex = Integer.parseInt(scannedToken);
				return tailVertex;
			}
			catch(NumberFormatException e) {
				AppView.outputLine("(����) ���� ���� ù ��° ���� �Է¿� ������ �ֽ��ϴ�: "+scannedToken);
			}
		}
	}
	//head vertex�� �Է¹޴´�
	public static int inputHeadVertex() {
		int headVertex;
		String scannedToken;
		//�ùٸ� ���� �Էµ� �� ���� �ݺ��Ѵ�
		while(true) {
			AppView.output("? ���� ���� �� ��° ���Ҹ� �Է��Ͻÿ�: ");
			scannedToken = AppView.scanner.next();
			//�Է� ���� ���� int���� �ƴϸ� ���� �޼����� ����Ѵ�
			try {
				headVertex = Integer.parseInt(scannedToken);
				return headVertex;
			}
			catch(NumberFormatException e) {
				AppView.outputLine("(����) ���� ���� �� ��° ���� �Է¿� ������ �ֽ��ϴ�: "+scannedToken);
			}
		}
	}

	
	public static int inputSourceVertex() {
		int sourceVertex;
		String scannedToken;
		while(true) {
			AppView.output("? ��� ���Ҹ� �Է��Ͻÿ�: ");
			scannedToken = AppView.scanner.next();
			try {
				sourceVertex = Integer.parseInt(scannedToken);
				return sourceVertex;
			}
			catch(NumberFormatException e) {
				AppView.outputLine("[����] ��� ���� �Է¿� ������ �ֽ��ϴ�: " + scannedToken);
			}
		}
	}
}
