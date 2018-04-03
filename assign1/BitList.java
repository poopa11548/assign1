package assign1;

import java.util.Iterator;

public interface BitList extends Iterator<Boolean> {
	
	void add(boolean value);
	
	void add(BitList reversedCode);
	
	//For change in all program
	static BitList newInstance(byte[] bytes) {
		return BitSetList.newInstance(bytes);
	}
	
	static BitList newInstance() {
		return BitSetList.newInstance();
	}
	
	byte[] toByteArray();
	
	byte nextByte();
}
