package test;

import assign1.HufmannEncoderDecoder;
import assign1.Utils;
import base.compressor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class HufmannEncoderDecoderTest {
	
	private static compressor compressorObject;
	private static byte[] bytes;
	//private static Random rnd = new Random();
	
	@BeforeAll
	static void classSetUp() {
		compressorObject = new HufmannEncoderDecoder();
		bytes = UtilsTest.getByteRandom(); //new byte[10000];
	}
	
	@Test
	void Compress() {
		try {
			File from = new File("tocompress.test"), to = new File("compressed.test");
			from.createNewFile();
			to.createNewFile();
			
			Utils.WriteByteArrayToFile(bytes, from.getPath());
			String[] input_names = {from.getPath()}, output_names = {to.getPath()};
			compressorObject.Compress(input_names, output_names);
			System.out.println(from.length());
			System.out.println(to.length());
			assertTrue(from.length() > to.length());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	void DeCompress() throws IOException {
		try {
			File from = new File("tocompress.de"), temp = new File("temp.de"), to = new File("decompress.de");
			String[] input_names = {from.getPath()}, temp_names = {temp.getPath()}, output_names = {to.getPath()};
			
			from.createNewFile();
			temp.createNewFile();
			to.createNewFile();
			
			Utils.WriteByteArrayToFile(bytes, from.getPath());
			compressorObject.Compress(input_names, temp_names);
			compressorObject.Decompress(temp_names, output_names);
			assertEquals(from.length(), to.length());
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
}