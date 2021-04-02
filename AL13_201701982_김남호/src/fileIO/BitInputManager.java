package fileIO;

import java.io.IOException;

public class BitInputManager {
	
	// Private Instance Variables
	private ExtendedBufferedInputStream _bitInputStream;

	private int _bitBuffer;				// buffer for one byte
	private int _numberOfBitsInBuffer;

	// Getters & Setters
	private ExtendedBufferedInputStream bitInputStream() {
		return _bitInputStream;
	}

	private void setBitInputStream(ExtendedBufferedInputStream _bitInputStream) {
		this._bitInputStream = _bitInputStream;
	}

	private int bitBuffer() {
		return _bitBuffer;
	}

	private void setBitBuffer(int _bitBuffer) {
		this._bitBuffer = _bitBuffer;
	}

	private int numberOfBitsInBuffer() {
		return _numberOfBitsInBuffer;
	}

	private void setNumberOfBitsInBuffer(int _numberOfBitsInBuffer) {
		this._numberOfBitsInBuffer = _numberOfBitsInBuffer;
	}

	// Constructor
	public BitInputManager(ExtendedBufferedInputStream givenBitInputStream) {
		this.setBitInputStream(givenBitInputStream);
		this.setNumberOfBitsInBuffer(0);
		this.setBitBuffer(0);
	}

	// Public method
	public int readBit() throws IOException {
		if (this.numberOfBitsInBuffer() == 0) {
			this.setBitBuffer(this.bitInputStream().read());
			this.setNumberOfBitsInBuffer(8);
		}
		int reuturnBitOfTheHuffmanCode = ((this.bitBuffer() >> (this.numberOfBitsInBuffer() - 1)) & 0x1);
		this.setNumberOfBitsInBuffer(this.numberOfBitsInBuffer() - 1);

		return reuturnBitOfTheHuffmanCode;
	}
}
