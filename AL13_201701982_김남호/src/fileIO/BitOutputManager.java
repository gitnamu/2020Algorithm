package fileIO;

import java.io.BufferedOutputStream;
import java.io.IOException;

public class BitOutputManager {
	
	// Private Instance Variables
	private BufferedOutputStream _bitOutputStream;
	
	private int _bitBuffer;			// buffer for one byte	
	private int _numberOfBitsInBuffer;

	// Getters & Setters
	private BufferedOutputStream bitOutputStream() {
		return this._bitOutputStream;
	}

	private void setBitOutputStream(BufferedOutputStream newBitOutputStream) {
		this._bitOutputStream = newBitOutputStream;
	}

	private int bitBuffer() {
		return this._bitBuffer;
	}

	private void setBitBuffer(int newBitBuffer) {
		this._bitBuffer = newBitBuffer;
	}

	private int numberOfBitsInBuffer() {
		return this._numberOfBitsInBuffer;
	}

	private void setNumberOfBitsInBuffer(int newNumberOfBitsInBuffer) {
		this._numberOfBitsInBuffer = newNumberOfBitsInBuffer;
	}

	// Constructor
	public BitOutputManager(BufferedOutputStream givenBitOutputStream) {
		this.setBitOutputStream(givenBitOutputStream);
		this.setNumberOfBitsInBuffer(0);
		this.setBitBuffer(0);
	}

	// Public methods
	public void writeBit(boolean bitValue) throws IOException {
		this.setBitBuffer(this.bitBuffer() << 1);
		if (bitValue) {
			this.setBitBuffer(this.bitBuffer() + 1);
		}
		this.setNumberOfBitsInBuffer(this.numberOfBitsInBuffer() + 1);
		if (this.numberOfBitsInBuffer() == 8) {
			// BitBuffer become full, so we should write to the bitOutputStream.
			this.bitOutputStream().write(this.bitBuffer());
			this.setBitBuffer(0);
			this.setNumberOfBitsInBuffer(0);
		}
	}

	public void flush() throws IOException {
		// There can exist some bits in Byte Buffer at the end of writing bits.
		// So w should write these bits to bitOutputStream.
		if (this.numberOfBitsInBuffer() > 0 && this.numberOfBitsInBuffer() < FileIO_CONST.NUMBER_OF_BITS_OF_BYTE) {
			int numberOfShiftLeftsToMakeLeftJustifiedInByte = FileIO_CONST.NUMBER_OF_BITS_OF_BYTE
					- this.numberOfBitsInBuffer();
			this.setBitBuffer(this.bitBuffer() << numberOfShiftLeftsToMakeLeftJustifiedInByte);
			this.bitOutputStream().write(this.bitBuffer());
			this.setBitBuffer(0);
			this.setNumberOfBitsInBuffer(0);
		}
	}
}
