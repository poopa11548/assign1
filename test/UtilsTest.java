package test;

import assign1.BitList;
import assign1.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilsTest {
	
	private static byte[] bytes;
	private static Hashtable<Byte, Integer> constFrequencies;
	
	@BeforeAll
	static void classInit() throws IOException {
		//constFrequencies = staticGetFrequencies();
		bytes = Utils.GetFileAsBytes(Consts.files_example[1]);
	}
	
	@Test
	void bits() {
		BitList bitList = BitList.newInstance(bytes, false);
		
		byte tr = (byte) 256;
		byte b = (byte) 0b10010100;
		for (int i = 0; i < 8; i++) {
			System.out.println(((b << i) & 0b10000000) == 0);
		}
		System.out.println(Utils.ByteToString(b));
		System.out.println(Utils.ByteToString((byte) (b << 3)));
		System.out.println(Utils.ByteToString((byte) (b << 4)));
		System.out.println(Utils.ByteToString((byte) ~b));
	}
	
	@Test
	void getFileAsBytes() {
		File tmp = new File("temp.test");
		try {
			Utils.WriteByteArrayToFile(bytes, tmp.getPath());
			byte[] result = Utils.GetFileAsBytes(tmp.getPath());
			assertEquals(bytes.length, result.length);
			assertArrayEquals(bytes, result);
			/*for (int i = 0; i < bytes.length; i++) {
				System.out.println(i + ": " + bytes[i] + "\t" + result[i]);
				assertEquals(bytes[i], result[i]);
			}*/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*@Test
	void getFrequencies() {
		Hashtable<Byte, HufmannNode> result = Utils.GetFrequencies(bytes);
		assertEquals(result.size(), constFrequencies.size());
		for (Map.Entry<Byte, Integer> entry :
				constFrequencies.entrySet()) {
			System.out.println(entry.getKey() + " const: " + entry.getValue() + " result: " + result.get(entry.getKey()).getFrequency());
			assertEquals((int) entry.getValue(), result.get(entry.getKey()).getFrequency());
		}
	}*/
	
	@Test
	void StringToByte() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			stringBuilder.append(Utils.ByteToString(bytes[i]));
		}
		//stringBuilder.append("010010");
		byte[] result = Utils.GetByteArrayFromString(stringBuilder.toString());
		assertEquals(bytes.length, result.length);
		assertArrayEquals(bytes, result);
	}
}