package assign1;

import java.util.BitSet;

public class BitSetList implements BitList {
	private int size;
	private BitSet bitSet;
	
	public BitSetList() {
		this.bitSet = new BitSet(0);
		this.size = 0;
	}
	
	private BitSetList(byte[] bytes) {
		this.bitSet = BitSet.valueOf(bytes);
		this.size = bytes.length * 8;
	}
	
	public static BitSetList newInstance() {
		return new BitSetList();
	}
	
	public static BitSetList newInstance(byte[] bytes) {
		return new BitSetList(bytes);
	}
	
	@Override
	public void add(boolean value) {
		throw new IllegalArgumentException("Not Implement");
	}
	
	@Override
	public void add(BitList reversedCode) {
		throw new IllegalArgumentException("Not Implement");
	}
	
	@Override
	public byte[] toByteArray() {
		throw new IllegalArgumentException("Not Implement");
	}
	
	@Override
	public byte nextByte() {
		throw new IllegalArgumentException("Not Implement");
	}
	
	@Override
	public void set(int index) {
		throw new IllegalArgumentException("Not Implement");
		
	}
	
	@Override
	public int length() {
		throw new IllegalArgumentException("Not Implement");
	}
	
	@Override
	public void set(int index, boolean value) {
		throw new IllegalArgumentException("Not Implement");
		
	}
	
	@Override
	public boolean hasNext() {
		throw new IllegalArgumentException("Not Implement");
	}
	
	@Override
	public Boolean next() {
		throw new IllegalArgumentException("Not Implement");
	}
}
