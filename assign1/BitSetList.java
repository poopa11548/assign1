package assign1;

import java.util.BitSet;

public class BitSetList implements BitList {
	private int size;
	private BitSet bitSet;
	private final boolean isPaddingInFirstByte;
	
	private BitSetList(boolean saveByteToPaddingCount) {
		this.isPaddingInFirstByte = saveByteToPaddingCount;
		if (saveByteToPaddingCount) {
			this.bitSet = new BitSet(8);
			this.size = 8;
		} else {
			this.bitSet = new BitSet(0);
			this.size = 0;
		}
	}
	
	private BitSetList(byte[] bytes, boolean isSizeInFirstByte) {
		this.isPaddingInFirstByte = isSizeInFirstByte;
		if (isSizeInFirstByte) {
			this.bitSet = BitSet.valueOf(bytes);
			this.size = bytes.length * 8 - getPaddingNumber();
		} else {
			this.bitSet = BitSet.valueOf(bytes);
			this.size = bytes.length * 8;
		}
	}
	
	public static BitSetList newInstance(boolean isSizeIn) {
		return new BitSetList(isSizeIn);
	}
	
	public static BitSetList newInstance(byte[] bytes, boolean isSizeInFirstByte) {
		return new BitSetList(bytes, isSizeInFirstByte);
	}
	
	private int getPaddingNumber() {
		int num = 0;
		for (int i = 0; i < 8; i++) {
			if (this.bitSet.get(i)) {
				num |= (1 << 7 - i);
			}
		}
		return num;
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
	
	private void setPaddingNumber(int number) {
		for (int i = 0; i < 8; i++) {
			this.bitSet.set(i, (number & (1 << (7 - i))) > 0);
		}
	}
	
	@Override
	public byte[] toByteArray() {
		if (isPaddingInFirstByte) {
			int i;
			for (i = 0; size % 8 != 0; i++) add(true);
			setPaddingNumber(i);
		}
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
		return new BitListIterator(this, isPaddingInFirstByte ? 8 : 0);
	}
	
	@Override
	public BitListIterator reverseIterator() {
		return new BitListIterator(this, true);
	}
}
