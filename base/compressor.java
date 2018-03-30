package base;

public interface compressor {
	void Compress(String[] input_names, String[] output_names);
	
	void Decompress(String[] input_names, String[] output_names);
	
	byte[] CompressWithArray(String[] input_names, String[] output_names);
	
	byte[] DecompressWithArray(String[] input_names, String[] output_names);
}
