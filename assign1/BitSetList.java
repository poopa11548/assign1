package assign1;

import java.io.EOFException;
import java.util.BitSet;

public class BitSetList implements BitList {
	private int size;
	private BitSet bitSet;
	private int index;
	
	private BitSetList() {
		this.bitSet = new BitSet(0);
		this.size = 0;
		index = 0;
	}
	
	private BitSetList(byte[] bytes) {
		this.bitSet = BitSet.valueOf(bytes);
		this.size = bytes.length * 8;
		index = 0;
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
	public void add(BitList bitList) {
		while (bitList.hasNext())
			this.add(bitList.next());
	}
	
	@Override
	public byte[] toByteArray() {
		return bitSet.toByteArray();
	}
	
	@Override
	public byte nextByte() throws EOFException {
		if (hasNextLength() < 8) throw new EOFException("Less then byte are left");
		byte result = 0;
		for (int i = 0; i < 8; i++) {
			result <<= 1;
			if (next()) {
				result |= 1;
				System.out.print("1");
			} else System.out.print("0");
		}
		System.out.println("");
		return result;
	}
	
	@Override
	public int length() {
		return size;
	}
	
	@Override
	public int hasNextLength() {
		return size - index;
	}
	
	@Override
	public void add(byte value) {
		for (int i = 7; i >= 0; i--) {
			add((value & (1 << i)) > 0);
		}
	}
	
	@Override
	public boolean hasNext() {
		return index < size;
	}
	
	@Override
	public Boolean next() {
		return bitSet.get(index++);
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
}
