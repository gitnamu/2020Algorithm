package AL03_201701982_�賲ȣ;
import java.util.Scanner;

public final class AppView {
	private static Scanner scanner = new Scanner(System.in);
	
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
			AppView.output("? vertex ���� �Է��Ͻÿ�: ");
			scannedToken = AppView.scanner.next();
			//�Է¹��� ���� int���� �ƴϸ� ���� �޼����� ����Ѵ�
			try {
				numberOfVertices = Integer.parseInt(scannedToken);
				return numberOfVertices;
			}
			catch(NumberFormatException e) {
				AppView.outputLine("(����) Vertex �� �Է¿� ������ �ֽ��ϴ�: "+scannedToken);
			}
		}
	}
	//edge�� ���� �Է¹޴´�
	public static int inputNumberOfEdges() {
		int numberOfEdges;
		String scannedToken;
		//�ùٸ� ���� �Էµ� �� ���� �ݺ��Ѵ� 
		while(true) {
			AppView.output("? edge ���� �Է��Ͻÿ�: ");
			scannedToken = AppView.scanner.next();
			//�Է¹��� �� �� int���� �ƴϸ� ���� �޼����� ����Ѵ�
			try {
				numberOfEdges = Integer.parseInt(scannedToken);
				return numberOfEdges;
			}
			catch(NumberFormatException e) {
				AppView.outputLine("(����) edge �� �Է¿� ������ �ֽ��ϴ�: "+scannedToken);
			}
		}
	}
	//tail vertex�� �Է¹޴´�
	public static int inputTailVertex() {
		int tailVertex;
		String scannedToken;
		//�ùٸ� ���� �Էµ� �� ���� �ݺ��Ѵ�
		while(true) {
			AppView.output("? tail vertex �� �Է��Ͻÿ�: ");
			scannedToken = AppView.scanner.next();
			//�Է¹��� ���� int���� �ƴϸ� ���� �޼����� ����Ѵ�
			try {
				tailVertex = Integer.parseInt(scannedToken);
				return tailVertex;
			}
			catch(NumberFormatException e) {
				AppView.outputLine("(����) tail vertex �Է¿� ������ �ֽ��ϴ�: "+scannedToken);
			}
		}
	}
	//head vertex�� �Է¹޴´�
	public static int inputHeadVertex() {
		int headVertex;
		String scannedToken;
		//�ùٸ� ���� �Էµ� �� ���� �ݺ��Ѵ�
		while(true) {
			AppView.output("? head vertex �� �Է��Ͻÿ�: ");
			scannedToken = AppView.scanner.next();
			//�Է� ���� ���� int���� �ƴϸ� ���� �޼����� ����Ѵ�
			try {
				headVertex = Integer.parseInt(scannedToken);
				return headVertex;
			}
			catch(NumberFormatException e) {
				AppView.outputLine("(����) head vertex �Է¿� ������ �ֽ��ϴ�: "+scannedToken);
			}
		}
	}
}
