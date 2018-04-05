package test;

import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

public class Consts {
	public static final String[] files_example = {
			"Ass1ExampeInputs/london_in_polish_source.txt",
			"Ass1ExampeInputs/OnTheOrigin.txt",
			"Ass1ExampeInputs/OnTheOrigin_C2.txt",
			"Ass1ExampeInputs/YouKnowThisSound"
	};
	public static final String[] files_output = {
			"Ass1Outputs/london_in_polish_source.txt",
			"Ass1Outputs/OnTheOrigin.txt",
			"Ass1Outputs/OnTheOrigin_C2.txt",
			"Ass1Outputs/YouKnowThisSound"
	};
	public static final String[] files_decompressed = {
			"Ass1Decompressed/london_in_polish_source.txt",
			"Ass1Decompressed/OnTheOrigin.txt",
			"Ass1Decompressed/OnTheOrigin_C2.txt",
			"Ass1Decompressed/YouKnowThisSound"
	};
	private static final int count = 100;
	private static Random rnd = new Random();
	
	public static Hashtable<Byte, Integer> staticGetFrequencies() {
		int count = Consts.count;
		Hashtable<Byte, Integer> frequencies = new Hashtable<>();
		for (int i = rnd.nextInt(count - 1) + 1; count > 4; count -= i, i = rnd.nextInt(count > 1 ? count - 1 : 1) + 1)
			frequencies.put((byte) rnd.nextInt(256), i);
		frequencies.put((byte) rnd.nextInt(256), count);
		
		//assertEquals(frequencies.values().stream().mapToInt(Integer::intValue).sum(), count);
		return frequencies;
	}
	
	public static byte[] getByteRandom(Hashtable<Byte, Integer> frequencies) {
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
	
	public static byte[] getByteRandom() {
		return getByteRandom(staticGetFrequencies());
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
}
