package assign1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Iterator;

public class Utils {
	public static byte[] GetFileAsBytes(String path) throws IOException {
		Path realPath = Paths.get(path);
		return Files.readAllBytes(realPath);
	}
	
	public static Hashtable<Byte, HufmannNode> GetFrequencies(byte[] bytes) {
		
		Hashtable<Byte, HufmannNode> hashtable = new Hashtable<>();
		
		for (byte b : bytes) {
			if (!hashtable.containsKey(b))
				hashtable.put(b, new HufmannNode(b));
			hashtable.get(b).UpdateFrequency(1);
		}
		return hashtable;
	}
	
	public static byte[] GetByteArrayFromString(String s) {
		int length = s.length() % 8 == 0 ? s.length() / 8 : (s.length() / 8) + 1;
		byte[] bytes = new byte[length];
		int i;
		for (i = 0; i < length - 1; i++) {
			String tmp = s.substring(i * 8, (i * 8) + 8);
			//s = s.substring(8);
			//System.out.println(tmp);
			bytes[i] = StringToByte(tmp);
		}
		bytes[length - 1] = StringToByte(s.substring(i * 8));
		return bytes;
	}
	
	public static byte StringToByte(String s) {
		// TODO remove 'AND'
		return (byte) (Integer.parseInt(s, 2) << 8 - s.length());
	}
	
	public static String ByteToString(byte b) {
		return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
	}
	
	public static void WriteByteArrayToFile(byte[] bytesToFile, String path) throws IOException {
		File file = new File(path);
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(new File(path));
		fos.write(bytesToFile);
		fos.close();
	}
	
	public static byte[] ConvertObjectToByte(Object[] objects) {
		byte[] result = new byte[objects.length];
		for (int i = 0; i < result.length; i++)
			result[i] = (byte) objects[i];
		return result;
	}
	
	public static byte NextBitFromIterator(Iterator<Boolean> iterator) {
		byte result = 0;
		int i;
		for (i = 0; i < 8 && iterator.hasNext(); i++) {
			result <<= 1;
			if (iterator.next()) result |= 1;
		}
		if (i < 8)
			throw new IndexOutOfBoundsException(i);
		return result;
	}
}
