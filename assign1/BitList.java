package assign1;

public interface BitList extends Iterable<Boolean> {
	//For change in all program
	static BitList newInstance(boolean saveByteToPaddingCount) {
		return BitSetList.newInstance(saveByteToPaddingCount);
	}
	
	static BitList newInstance(byte[] bytes, boolean isSizeIn) {
		return BitSetList.newInstance(bytes, isSizeIn);
	}
	void add(boolean value);
	
	void add(BitListIterator reversedCode);
	
	byte[] toByteArray();
	
	int length();
	
	void add(byte value);
	
	@Override
	BitListIterator iterator();
	
	BitListIterator reverseIterator();
	
	Boolean get(int index);
	
	byte getByte(int index);
}
