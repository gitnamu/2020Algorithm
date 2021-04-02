package app;

public class AppController {
	
	// Private instance variables
	private CompressionController _compressionController;
	private DecompressionController _decompressionController;
	private ValidationController _validationController;
	
	// Getters & Setters
	private CompressionController compressionController() {
		if (this._compressionController == null) {
			// Lazy Instantiation
			this._compressionController = new CompressionController();
		}
		return this._compressionController;
	}

	private DecompressionController decompressionController() {
		if (this._decompressionController == null) {
			this._decompressionController = new DecompressionController();
		}
		return this._decompressionController;
	}

	private ValidationController validationController() {
		if (this._validationController == null) {
			this._validationController = new ValidationController();
		}
		return this._validationController;
	}
	
	// Private methods
	private MainMenu selectionMenu() {
		AppView.outputLine("");
		AppView.output("? �ؾ��� �۾��� �����Ͻÿ� (���� = 1, ���� = 2, ���� = 3, ���� = 4) : ");
		try {
			int selectedMenuNumber = AppView.inputInteger();
			// "NumberFormatException" can occur. It will be caught.
			MainMenu selectedMenuValue = MainMenu.value(selectedMenuNumber);
			if (selectedMenuValue == MainMenu.ERROR) {
				AppView.outputLine("!���� : �۾� ������ �� �� �Ǿ����ϴ�. (�� ���� ��ȣ : " + selectedMenuNumber + ")");
			}
			return selectedMenuValue;
		} catch (NumberFormatException e) {
			AppView.outputLine("!���� : �Էµ� �޴� ��ȣ�� ������ ���ڰ� �ƴմϴ�.");
			return MainMenu.ERROR;
		}
	}

	// Constructor 
	public AppController() {

	}
	// Public method
	public void run() {
		AppView.outputLine("<<< Huffman Code �� �̿��� ���� ����/���� ���α׷��� �����մϴ�. >>>");

		MainMenu selectedMenuValue = this.selectionMenu();
		while (selectedMenuValue != MainMenu.END) {
			switch (selectedMenuValue) {
			case COMPRESS:
				this.compressionController().run();
				break;
			case DECOMPRESS:
				this.decompressionController().run();
				break;
			case VALIDATE:
				this.validationController().run();
				break;
			default:
				break;
			}
			selectedMenuValue = this.selectionMenu();
		}
		AppView.outputLine("");
		AppView.outputLine("<<< <<< Huffman Code �� �̿��� ���� ����/���� ���α׷��� �����մϴ� >>>");
	}
}
