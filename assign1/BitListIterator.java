package assign1;

import java.io.EOFException;
import java.util.Iterator;

class BitListIterator implements Iterator<Boolean> {
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
	
	@Override
	public boolean hasNext() {
		return hasNextLength() > 0;
	}
	
	@Override
	public Boolean next() {
		return bitList.get(step());
	}
	
	private int step() {
		int result = index;
		index = index + (reverse ? -1 : 1);
		return result;
	}
	
	public byte nextByte() throws EOFException {
		if (hasNextLength() < 8) throw new EOFException("Less then byte are left");
		byte result = 0;
		for (int i = 0; i < 8; i++) {
			result <<= 1;
			if (next())// {
				result |= 1;
			//	System.out.print("1");
			//} else System.out.print("0");
		}
		//System.out.println("");
		return result;
	}
	
	public int hasNextLength() {
		if (reverse) return index + 1;
		return bitList.length() - index;
	}
}
