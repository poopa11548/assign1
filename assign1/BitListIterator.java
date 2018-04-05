package assign1;

import java.io.EOFException;
import java.util.Iterator;

final class BitListIterator implements Iterator<Boolean> {
	private final boolean reverse;
	private BitList bitList;
	private int index;
	
	public BitListIterator(BitList bitList) {
		this.bitList = bitList;
		this.reverse = false;
		index = 0;
	}
	
	public BitListIterator(BitList bitList, boolean reverse) {
		this.bitList = bitList;
		this.reverse = reverse;
		if (reverse) index = bitList.length() - 1;
		else index = 0;
	}
	
	public BitListIterator(BitList bitList, int startIndex) {
		this.bitList = bitList;
		this.reverse = false;
		index = startIndex;
	}
	
	@Override
	public boolean hasNext() {
		return hasNextLength() > 0;
	}
	
	@Override
	public Boolean next() {
		return bitList.get(step());
	}
	
	private int step() {
		return step(1);
	}
	
	private int step(int count) {
		int result = index;
		index = index + (reverse ? -count : count);
		return result;
	}
	
	public byte nextByte() throws EOFException {
		if (hasNextLength() < 8) throw new EOFException("Less then byte are left");
		return bitList.getByte(step(8));
	}
	
	public int hasNextLength() {
		if (reverse) return index + 1;
		return bitList.length() - index + 1;
	}
}
