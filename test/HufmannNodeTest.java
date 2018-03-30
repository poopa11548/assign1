package test;

import assign1.ByteIter;
import assign1.HufmannNode;
import assign1.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.EOFException;
import java.util.PriorityQueue;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HufmannNodeTest {
	
	private static byte[] bytes;
	private static Random rnd = new Random();
	
	@BeforeAll
	static void classSetUp() {
		bytes = UtilsTest.getByteRandom(10000);// new byte[100];
		//rnd.nextBytes(bytes);
	}
	
	@Test
	void ParseToStringAndBuild() {
		try {
			HufmannNode head = HufmannNode.BuildTree(new PriorityQueue<>(Utils.GetFrequencies(bytes).values()));
			
			//byte[] b = Utils.GetByteArrayFromString(head.toString());
			HufmannNode after = new HufmannNode(new ByteIter(b));
			
			assertEquals(head, after);
			
		} catch (EOFException e) {
			e.printStackTrace();
		}
	}
	
}