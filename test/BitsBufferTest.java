package test;

import assign1.BitSetBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BitsBufferTest {
	@Test
	void sizeTest() {
		BitSetBuilder bitSet = new BitSetBuilder();
		for (int i = 0; i < 20; i++) {
			bitSet.set(i);
			assertEquals(i + 1, bitSet.length());
		}
		bitSet = new BitSetBuilder();
		for (int i = 0; i < 20; i++) {
			bitSet.set(i, false);
			assertEquals(i + 1, bitSet.length());
		}
		
	}
}