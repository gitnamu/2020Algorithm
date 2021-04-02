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
		AppView.output("? 해야할 작업을 선택하시오 (압축 = 1, 해제 = 2, 검증 = 3, 종료 = 4) : ");
		try {
			int selectedMenuNumber = AppView.inputInteger();
			// "NumberFormatException" can occur. It will be caught.
			MainMenu selectedMenuValue = MainMenu.value(selectedMenuNumber);
			if (selectedMenuValue == MainMenu.ERROR) {
				AppView.outputLine("!오류 : 작업 선택이 잘 못 되었습니다. (잘 못된 번호 : " + selectedMenuNumber + ")");
			}
			return selectedMenuValue;
		} catch (NumberFormatException e) {
			AppView.outputLine("!오류 : 입력된 메뉴 번호가 정수형 숫자가 아닙니다.");
			return MainMenu.ERROR;
		}
	}

	// Constructor 
	public AppController() {

	}
	// Public method
	public void run() {
		AppView.outputLine("<<< Huffman Code 를 이용한 파일 압출/해제 프로그램을 시작합니다. >>>");

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
		AppView.outputLine("<<< <<< Huffman Code 를 이용한 파일 압축/해제 프로그램을 종료합니다 >>>");
	}
}
