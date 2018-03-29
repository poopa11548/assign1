package assign1;

import java.awt.List;
import java.io.IOException;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Vector;
import java.lang.Iterable;

import com.sun.org.apache.xalan.internal.xsltc.dom.BitArray;

import oracle.jrockit.jfr.events.Bits;


public class main {
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String[] s = new String[2];
		s[0]="c:\\data\\input-text.txt"; 	
		String[] x=new String[2];
		x[0]="c:\\data\\decoded.txt";
		HufmannEncoderDecoder x1=new HufmannEncoderDecoder();
		x1.Compress(s, x);
		s[0]="c:\\data\\output-text.txt";
	    x1.Decompress(x, s);
	}

}
