package assign1;

/**
 * Assignment 1
 * Submitted by:
 * Student 1. 	ID# XXXXXXXXX
 * Student 1. 	ID# XXXXXXXXX
 */

// Uncomment if you wish to use FileOutputStream and FileInputStream for file access.
//import java.io.FileOutputStream;
//import java.io.FileInputStream;

import base.compressor;

import java.io.IOException;
import java.util.Hashtable;
import java.util.PriorityQueue;

public class HufmannEncoderDecoder implements compressor {
	
	public HufmannEncoderDecoder() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void Compress(String[] input_names, String[] output_names) {
		if (input_names.length != output_names.length)
			throw new IllegalArgumentException("The Input and Output arrays must be in same length");
		for (int i = 0; i < input_names.length; i++) {
			try {
				byte[] bytes = Utils.GetFileAsBytes(input_names[i]);
				
				// get Frequencies as hashtable
				Hashtable<Byte, HufmannNode> frequencies = Utils.GetFrequencies(bytes);
				
				// build priority from Frequencies
				PriorityQueue<HufmannNode> minHeap = new PriorityQueue<>(frequencies.values());
				
				// build tree from priority until priority size is one
				HufmannNode hufmannTree = HufmannNode.BuildTreeFromHeap(minHeap);
				
				BitList bitsBuffer = BitList.newInstance();
				hufmannTree.BuildBitListFromTree(bitsBuffer);
				
				for (byte b : bytes) {
					bitsBuffer.add(frequencies.get(b).getCodeIterator());
					System.out.println(bitsBuffer.length());
				}
				//System.out.println("Tree string length: " + treeString.length());
				byte[] bytesToFile = bitsBuffer.toByteArray();// Utils.GetByteArrayFromString(treeString + builder.toString());
				System.out.println("Bytes to file length: " + bytesToFile.length);
				Utils.WriteByteArrayToFile(bytesToFile, output_names[i]);
			} catch (IOException e) {
				System.err.println("ERROR: " + e.getLocalizedMessage());
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void Decompress(String[] input_names, String[] output_names) {
		if (input_names.length != output_names.length)
			throw new IllegalArgumentException("The Input and Output arrays must be in same length");
		for (int i = 0; i < input_names.length; i++) {
			try {
				byte[] bytesFromFile = Utils.GetFileAsBytes(input_names[i]);
				System.out.println("Bytes from file: " + bytesFromFile.length);
				
				BitListIterator compressedBitListIterator = BitList.newInstance(bytesFromFile).iterator();
				BitList decompressed = BitList.newInstance();
				HufmannNode hufmannTree = new HufmannNode(compressedBitListIterator);
				System.out.println("Compreswsed: " + compressedBitListIterator.toString());
				while (compressedBitListIterator.hasNext())
					decompressed.add(hufmannTree.getValue(compressedBitListIterator));
				
				Utils.WriteByteArrayToFile(decompressed.toByteArray(), output_names[i]);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public byte[] CompressWithArray(String[] input_names, String[] output_names) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public byte[] DecompressWithArray(String[] input_names, String[] output_names) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
