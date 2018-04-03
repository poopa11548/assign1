package test;

import assign1.BitList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BitsBufferTest {
	@Test
	void sizeTest() {
		BitList bitSet = BitList.newInstance();
		for (int i = 0; i < 20; i++) {
			bitSet.set(i);
			assertEquals(i + 1, bitSet.length());
		}
		bitSet = BitList.newInstance();
		for (int i = 0; i < 20; i++) {
			bitSet.set(i, false);
			assertEquals(i + 1, bitSet.length());
		}
		
	}
}