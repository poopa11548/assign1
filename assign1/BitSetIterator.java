package assign1;

import java.io.EOFException;
import java.util.BitSet;
import java.util.Iterator;

public class BitSetIterator implements Iterator<Boolean> {
	int current_index = 0;
	BitSetBuilder bitSet;
	
	public BitSetIterator(byte[] bytes) {
		bitSet = new BitSetBuilder(BitSet.valueOf(bytes), bytes.length * 8);
	}
	
	public BitSetIterator(BitSetBuilder bitSet) {
		this.bitSet = bitSet;
	}
	
	@Override
	public boolean hasNext() {
		return current_index < bitSet.length() - 1;
	}
	
	@Override
	public Boolean next() {
		if (hasNext())
			return bitSet.get(++current_index);
		throw new IndexOutOfBoundsException("Index: " + (current_index + 1) + " Out of range (size: " + bitSet.length());
	}
	
	
	public byte nextByte() throws EOFException {
		if (current_index > bitSet.length() - 8) throw new EOFException("Less then byte are left");
		byte result = 0;
		for (int i = 0; i < 8; i++) {
			result <<= 1;
			if (next()) result |= 1;
		}
		return result;
	}
	
	public byte[] toByteArray() {
		return bitSet.toByteArray();
	}
	
	@Override
	public String toString() {
		return bitSet.toByteArray().toString();
	}
}
