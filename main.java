
package assign1;

import java.io.IOException;

public class main {
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String[] s = new String[2];
		s[0]="c:\\data\\YouKnowThisSound";
		String[] x=new String[2];
		x[0]="c:\\data\\decoded.txt";
		HufmannEncoderDecoder x1=new HufmannEncoderDecoder();
		HufmannEnglishEnDe x2=new HufmannEnglishEnDe();
		x2.Compress(s, x);
		//x1.Compress(s, x);
		//s[0]="c:\\data\\output-text.txt";
		//x1.Decompress(x, s);
		//byte[] b=x1.CompressWithArray(s,x);
		s[0]="c:\\data\\output-text.txt";
		//x2.Decompress(x,s);
		//b=x1.DecompressWithArray(x,s);
		
	}

}