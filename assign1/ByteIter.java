package assign1;

public class ByteIter/* implements Iterable<Boolean>, Iterator<Boolean>*/ {
	private byte[] bytes;
	private int bitCount = 0;
	private int byteCount = 0;
	
	public ByteIter(byte[] bytes) {
		this.bytes = bytes;
	}
	// TODO think about using BitSet
	/*@Override
	public Iterator<Boolean> iterator() {
		return this;
	}*/
	
	//@Override
	public boolean hasNext() {
		return bitCount != 7 || byteCount != bytes.length - 1;
	}
	
	//@Override
	public Boolean next() {
		// TODO return correct values for 0 and 1
		if (hasNext()) {
			// shift left until to bit that we want
			// then flip to 0 all bits except the MSB
			boolean result = ((bytes[byteCount] << bitCount) & 0b10000000) == 0;
			NextBitCounter();
			return result;
		}
		throw new IndexOutOfBoundsException("You move next over the bytes");
	}
	
	private void NextBitCounter() {
		bitCount++;
		if (bitCount == 8) {
			bitCount = 0;
			byteCount++;
		}
	}
	
	public byte nextByte() {
		byte result = 0;
		for (int i = 0; i < 8; i++) {
			result <<= 1;
			
			if (!next()) {
				//System.out.println("");
				result |= 1;
			}
			//System.err.println(Utils.ByteToString(result));
		}
		return result;
	}
	
	public int nextLengthInFullBytes() {
		if (bitCount == 0)    // more full bytes
			return bytes.length - byteCount;
		return bytes.length - byteCount - 1;    // first of the full bytes are half
	}
}
