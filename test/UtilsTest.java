package test;

import assign1.HufmannNode;
import assign1.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.BitSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilsTest {
	
	private static byte[] bytes;
	private static Hashtable<Byte, Integer> constFrequencies;
	private static final int count = 1000;
	private static Random rnd = new Random();
	
	public static Hashtable<Byte, Integer> getFrequencies(int count) {
		Hashtable<Byte, Integer> frequencies = new Hashtable<>();
		for (int i = rnd.nextInt(count - 1) + 1; count > 4; count -= i, i = rnd.nextInt(count > 1 ? count - 1 : 1) + 1)
			frequencies.put((byte) rnd.nextInt(256), i);
		frequencies.put((byte) rnd.nextInt(256), count);
		
		//assertEquals(frequencies.values().stream().mapToInt(Integer::intValue).sum(), count);
		return frequencies;
	}
	
	public static byte[] getByteRandom(int count, Hashtable<Byte, Integer> frequencies) {
		byte[] mybytes = new byte[count];
		
		for (Map.Entry<Byte, Integer> x : frequencies.entrySet()) {
			int value = x.getValue();
			while (value > 0) {
				int rndNum = rnd.nextInt(count);
				while (mybytes[rndNum] != (byte) 0b00000000) {
					if (++rndNum >= count) rndNum = 0;
				}
				mybytes[rndNum] = x.getKey();
				value--;
			}
		}
		return mybytes;
	}
	
	public static byte[] getByteRandom(int count) {
		return getByteRandom(count, getFrequencies(count));
		/*System.out.println("Start");
		byte[] bytes = new byte[count];
		int counter = count;
		
		for (int i = rnd.nextInt(counter - 1) + 1; counter > 4; counter -= i, i = rnd.nextInt(counter > 1 ? counter - 1 : 1) + 1) {
			for (int j = 0; j < i; j++) {
				byte numToInsert = (byte) rnd.nextInt(256);
				int rndNum = rnd.nextInt(count);
				while (bytes[rndNum] != (byte) 0b00000000) {
					if (++rndNum >= count)
						rndNum = 0;
				}
				bytes[rndNum] = numToInsert;
			}
		}
		System.out.println("end");
		return bytes;*/
		/*for (int i = rnd.nextInt(count - 1) + 1; count > 4; count -= i, i = rnd.nextInt(count > 1 ? count - 1 : 1) + 1)
			frequencies.put((byte) rnd.nextInt(256), i);
		frequencies.put((byte) rnd.nextInt(256), count);*/
	}
	
	
	@BeforeAll
	static void classInit() {
		constFrequencies = getFrequencies(count);
		bytes = getByteRandom(count, constFrequencies);
	}
	
	@Test
	void bits() {
		BitSet bitSet = new BitSet();
		bitSet.set(0,false);
		System.out.println(bitSet.toString());
		
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
	
	@Test
	void getFrequencies() {
		Hashtable<Byte, HufmannNode> result = Utils.GetFrequencies(bytes);
		assertEquals(result.size(), constFrequencies.size());
		for (Map.Entry<Byte, Integer> entry :
				constFrequencies.entrySet()) {
			System.out.println(entry.getKey() + " const: " + entry.getValue() + " result: " + result.get(entry.getKey()).getFrequency());
			assertEquals((int) entry.getValue(), result.get(entry.getKey()).getFrequency());
		}
	}
	
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