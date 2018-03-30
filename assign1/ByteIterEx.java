package assign1;

import java.util.LinkedList;

public class ByteIterEx {
	private LinkedList<Byte> bytes = new LinkedList<>();
	private byte temp = 0;
	private int tempIndex = 7;
	
	public void add(boolean b) {
		if (b) temp |= 1 << tempIndex;
		downIndex();
	}
	
	private void downIndex() {
		if (--tempIndex < 0) {
			bytes.add(temp);
			temp = 0;
			tempIndex = 7;
		}
	}
	
	public void add(LinkedList<Boolean> code) {
		for (boolean b : code)
			add(b);
	}
}
