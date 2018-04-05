package test;

import assign1.HufmannEncoderDecoder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;


class HufmannEncoderDecoderTest {
	
	@Test
	void Decompress() throws IOException {
		HufmannEncoderDecoder compressorObject = new HufmannEncoderDecoder();
		try {
			compressorObject.Compress(Consts.files_example, Consts.files_output);
			for (int i = 0; i < Consts.files_example.length; i++) {
				assertTrue(new File(Consts.files_example[i]).length() > new File(Consts.files_output[i]).length(),
						"" + new File(Consts.files_example[i]).length() + " > " + new File(Consts.files_output[i]).length());
			}
			
			compressorObject.Decompress(Consts.files_output, Consts.files_decompressed);
			for (int i = 0; i < Consts.files_example.length; i++) {
				assertEquals(new File(Consts.files_example[i]).length(), new File(Consts.files_decompressed[i]).length());
				assertArrayEquals(Files.readAllBytes(Paths.get(Consts.files_example[i])), Files.readAllBytes(Paths.get(Consts.files_decompressed[i])));
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
}