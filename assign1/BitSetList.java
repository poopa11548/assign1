package assign1;

import java.util.BitSet;

public class BitSetList implements BitList {
	private int size;
	private BitSet bitSet;
	
	private BitSetList() {
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
		bitSet.set(size++, value);
	}
	
	@Override
	public void add(BitListIterator bitList) {
		while (bitList.hasNext())
			this.add(bitList.next());
	}
	
	@Override
	public byte[] toByteArray() {
		return bitSet.toByteArray();
	}
	
	@Override
	public int length() {
		return size;
	}
	
	@Override
	public void add(byte value) {
		for (int i = 7; i >= 0; i--) {
			add((value & (1 << i)) > 0);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < size; i++) {
			builder.append(bitSet.get(i) ? "1" : "0");
		}
		return "BitSetList{" +
				"bitSet=" + builder.toString() +
				'}';
	}
	
	
	@Override
	public Boolean get(int index) {
		return this.bitSet.get(index);
	}
	
	@Override
	public BitListIterator iterator() {
		return new BitListIterator(this);
	}
	
	@Override
	public BitListIterator reverseIterator() {
		return new BitListIterator(this, true);
	}
}
