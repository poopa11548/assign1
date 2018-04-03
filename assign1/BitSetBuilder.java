package assign1;

import java.util.BitSet;

public class BitSetBuilder {
	private BitSet bitSet = new BitSet();
	private int size = 0;
	
	public BitSetBuilder(int nbits) {
		this.bitSet = new BitSet(nbits);
	}
	
	public BitSetBuilder(BitSet bitSet, int size) {
		this.bitSet = (BitSet) bitSet.clone();
		this.size = size;
	}
	
	public BitSetBuilder() {
	
	}
	
	public void set(int i) {
		bitSet.set(i);
		size++;
	}
	
	public void set(int index, boolean value) {
		bitSet.set(index, value);
		if (index + 1 > size) size = index + 1;
	}
	
	public Boolean get(int i) {
		return bitSet.get(i);
	}
	
	public byte[] toByteArray() {
		return bitSet.toByteArray();
	}
	
	public int length() {
		return size;
	}
	
	public void set(boolean value) {
		bitSet.set(size++, value);
	}
}
