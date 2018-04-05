package test;

import assign1.BitList;
import assign1.HufmannNode;
import assign1.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.EOFException;
import java.io.IOException;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HufmannNodeTest {
	
	private static byte[] bytes;
	
	@BeforeAll
	static void classSetUp() throws IOException {
		bytes = Utils.GetFileAsBytes(Consts.files_example[1]);
	}
	
	@Test
	void ParseToStringAndBuild() throws EOFException {
		try {
			HufmannNode head = HufmannNode.BuildTreeFromHeap(new PriorityQueue<>(Utils.GetFrequencies(bytes).values()));
			
			BitList buffer = head.BuildBitListFromTree();
			//System.out.println(buffer.toString());
			HufmannNode after = new HufmannNode(buffer.iterator());
			
			assertEquals(head, after);
			
		} catch (EOFException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
}