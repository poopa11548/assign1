package assign1;

import java.io.EOFException;
import java.util.BitSet;
import java.util.Iterator;

public class BitSetIterator extends BitSet implements Iterable<Boolean>, Iterator<Boolean> {
	private int current_index = 0;
	
	public BitSetIterator() {
	}
	
	public BitSetIterator(int nbits) {
		super(nbits);
	}
	
	public BitSetIterator(byte[] bytesFromFile) {
		super(bytesFromFile.length * 8);
		for (int i = 0; i < size(); i++) {
			if ((bytesFromFile[bytesFromFile.length - i / 8 - 1] & (1 << (i % 8))) > 0)
		}
	}
	
	@Override
	public Iterator<Boolean> iterator() {
		return this;
	}
	
	@Override
	public boolean hasNext() {
		return current_index == this.size() - 1;
	}
	
	@Override
	public Boolean next() {
		return get(++current_index);
	}
	
	public Iterator<Boolean> reverse() {
		return new RevereseIterator();
	}
	
	public void add(boolean b) {
		set(size(), b);
	}
	
	public void add(BitSetIterator bitset) {
		for (boolean b : bitset) {
			add(b);
		}
	}
	
	public void addReverse(BitSetIterator bitset) {
		Iterator<Boolean> iterator = bitset.reverse();
		while (iterator.hasNext()) add(iterator.next());
	}
	
	public byte nextByte() throws EOFException {
		if (current_index > size() - 8) throw new EOFException("Less then byte are left");
		byte result = 0;
		for (int i = 0; i < 8; i++) {
			result <<= 1;
			if (next()) result |= 1;
		}
		return result;
	}
	
	public class RevereseIterator implements Iterator<Boolean> {
		private int current_index;
		
		@Override
		public boolean hasNext() {
			return current_index >= 0;
		}
		
		@Override
		public Boolean next() {
			return BitSetIterator.this.get(--current_index);
		}
	}
}
