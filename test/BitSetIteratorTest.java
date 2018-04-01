package test;

import assign1.BitSetIterator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BitSetIteratorTest {
	@Test
	void iterator() {
		int size = 10;
		BitSetIterator iterator = new BitSetIterator(size);
		assertEquals(size, iterator.size());
		
		int i;
		for (i = 0; i < iterator.size(); i++) ;
		assertEquals(size, i);
		
		i = 0;
		for (boolean b : iterator) {
			i++;
		}
		assertEquals(size, i);
	}
}