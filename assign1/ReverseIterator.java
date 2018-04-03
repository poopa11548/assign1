package assign1;

public class ReverseIterator extends BitSetIterator {
	
	public ReverseIterator(byte[] bytes) {
		super(bytes);
		current_index = bytes.length * 8;
	}
	
	public ReverseIterator(BitSetBuilder bitSet) {
		super(bitSet);
		current_index = bitSet.length();
	}
	
	@Override
	public boolean hasNext() {
		return current_index > 0;
	}
	
	@Override
	public Boolean next() {
		return bitSet.get(--current_index);
	}
}
