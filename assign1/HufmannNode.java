package assign1;

import java.io.EOFException;
import java.util.Iterator;
import java.util.Objects;
import java.util.PriorityQueue;

public class HufmannNode implements Comparable {
	private int frequency;
	private BitSetBuilder reversedCode = new BitSetBuilder();
	//private int codeIndex = 0;
	private byte value;
	private HufmannNode left;
	private HufmannNode right;
	private boolean isLeaf;
	
	private HufmannNode() {
		left = null;
		right = null;
	}
	
	public HufmannNode(byte value) {
		this.value = value;
		this.frequency = 0;
		this.left = null;
		this.right = null;
		isLeaf = true;
	}
	
	public HufmannNode(HufmannNode left, HufmannNode right) {
		this.left = left;
		this.right = right;
		this.frequency = left.frequency + right.frequency;
		isLeaf = false;
		left.setReversedCode(true);
		right.setReversedCode(false);
	}
	
	public HufmannNode(Iterator<Boolean> iterator) throws EOFException {
		if (!iterator.hasNext())
			throw new EOFException("No more bits to build tree");
		boolean bit = iterator.next();
		// DEL System.out.println(bit ? 0 : 1);
		if (bit) {
			if (left != null || right != null) {
				throw new IllegalArgumentException("Error here. this is wired");
			}
			isLeaf = false;
			left = new HufmannNode(iterator);
			right = new HufmannNode(iterator);
			
			// DEL System.out.println(Utils.ByteToString(value));
		} else {
			isLeaf = true;
			value = Utils.NextBitFromIterator(iterator);
		}
	}
	
	public static HufmannNode BuildTree(PriorityQueue<HufmannNode> minHeap) {
		while (minHeap.size() > 1) {
			HufmannNode newNode = new HufmannNode(minHeap.poll(), minHeap.poll());
			minHeap.add(newNode);
		}
		return minHeap.poll();
	}
	
	/*public void BuildTree(ByteIter iterator) throws EOFException {

		for(int i = 0;i<bytesFromFile.length;i++)
			System.out.print(Utils.ByteToString(bytesFromFile[i]));
		HufmannNode head = new HufmannNode();
		Iterator<Boolean> iterator = ByteIter.getIterator(bytesFromFile);
		while (iterator.hasNext()) {
			boolean next = iterator.next();
			System.out.println(next ? 0 : 1);
			if (!next) {    // block start with 1- move on tree
				System.out.println("Start build tree");
				head.BuildFromBits(iterator, "");
			} else {    // block start with 0- should be more 0 to close tree
				System.out.println("Check if done");
				if (iterator.next()) {
					System.out.println("Yes is done");
					for(int i = 0;i<bytesFromFile.length;i++)
						System.out.print(Utils.ByteToString(bytesFromFile[i]));
					return head;
				}
				throw new ParseException("Error while parse tree from bytes: ", 0);
			}
		}
		throw new EOFException("EOF without '00' sign of end of tree");
	}*/
	
	public int getFrequency() {
		return frequency;
	}
	
	public HufmannNode getLeft() {
		return left;
	}
	
	public HufmannNode getRight() {
		return right;
	}
	
	public void UpdateFrequency(int i) {
		frequency += i;
	}
	
	@Override
	public int compareTo(Object o) {
		if (o instanceof HufmannNode) {
			
			return frequency - ((HufmannNode) o).frequency;
		}
		throw new IllegalArgumentException("Argument must be instance of HufmannNode");
	}
	
	public Iterator<Boolean> getReversedCode() {
		return new ReverseIterator(reversedCode);
	}
	
	private void setReversedCode(boolean s) {
		if (isLeaf) reversedCode.set(s);
		else {
			right.setReversedCode(s);
			left.setReversedCode(s);
		}
	}
	
	public void addToBuffer(BitsBuffer buffer) {
		if (isLeaf) {
			buffer.add(false);
			buffer.add(new ReverseIterator(reversedCode));
		} else {
			buffer.add(true);
			left.addToBuffer(buffer);
			right.addToBuffer(buffer);
		}
	}
	
	/*public String toString1() {
		if (isLeaf) return "00" + Utils.ByteToString(value);
		return "10" + left.addToBuffer() + "01" + right.addToBuffer();
	}
	
	public String toString2() {
		if (isLeaf) return Utils.ByteToString((byte) reversedCode.length()) + reversedCode + Utils.ByteToString(value);
		else return left.toString2() + right.toString2();
	}*/
	
	/*private void BuildFromBits(Iterator<Boolean> iterator, String codeStep) throws NumberFormatException {
		boolean first = iterator.next(), second = iterator.next();
		System.out.print(first ? 0 : 1);
		System.out.println(second ? 0 : 1);
		if (!first && second) {    // 10
			isLeaf = false;
			if (left == null) left = new HufmannNode();
			left.BuildFromBits(iterator, codeStep + "0");
		} else if (first && !second) {    // 01
			isLeaf = false;
			if (right == null) right = new HufmannNode();
			right.BuildFromBits(iterator, codeStep + "1");
		} else if (first && second) {    // 00
			System.out.println("Is Leaf!");
			isLeaf = true;
			StringBuilder ascii = new StringBuilder();
			for (int i = 0; i < 8; i++)
				ascii.append(iterator.next() ? 0 : 1);
			System.out.println(ascii.addToBuffer());
			value = Utils.StringToByte(ascii.addToBuffer());
			System.out.println(Utils.ByteToString(value));
			reversedCode = codeStep;
			System.out.println(reversedCode);
		} else throw new NumberFormatException("block '11' is illegal format");
	}*/
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		HufmannNode that = (HufmannNode) o;
		if (isLeaf)
			return that.isLeaf &&
					value == that.value;
		//Objects.equals(reversedCode, that.reversedCode) &&
		return left.equals(that.left) && right.equals(that.right);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(reversedCode, value, isLeaf);
	}
	
	public byte getValue(Iterator<Boolean> iterator) throws EOFException {
		if (!iterator.hasNext()) throw new EOFException("End of Iterator");
		if (isLeaf) return value;
		if (iterator.next()) return left.getValue(iterator);
		return right.getValue(iterator);
	}
}
