package assign1;

import java.util.Iterator;

public class BitsBuffer implements Buffer {
	private int paddingBits;
	private BitList bitSet;
	private int currentIndex = -1;
	
	/*public BitsBuffer(HufmannNode head) {
		head.addToBuffer(this);
		//this.paddingBits = 8 - ((currentIndex + 1) % 8);
	}*/
	
	@Override
	public void add(boolean b) {
		bitSet.add(b);
		currentIndex++;
	}
	
	@Override
	public void add(Iterator<Boolean> iterator) {
		while (iterator.hasNext())
			add(iterator.next());
	}
	
	@Override
	public Iterator<Boolean> iterator() {
		return null;// BitSetIterator(bitSet);
	}
	
	@Override
	public byte[] toByteArray() {
		return bitSet.toByteArray();
	}
}
