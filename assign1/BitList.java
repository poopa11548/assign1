package assign1;

public interface BitList extends Iterable<Boolean> {
	//For change in all program
	static BitList newInstance(byte[] bytes) {
		return BitSetList.newInstance(bytes);
	}
	
	static BitList newInstance() {
		return BitSetList.newInstance();
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
	
}
